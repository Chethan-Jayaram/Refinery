package com.application.refinary.fragment.login;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.activity.HomeScreenActivity;
import com.application.refinary.fragment.modules.news.News;
import com.application.refinary.fragment.modules.weather.Weather;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.FingerprintHandler;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.loginmpin.Data;
import com.application.refinary.pojo.loginmpin.LoginMpin;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Response;



public class LoginMpinFragment extends Fragment implements ApiListener, GlobalClass.OnBiometricAuthSucess {
    //Fingerprint auth variables
    private static final String KEY_NAME = "RefinaryBioMetric";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private TextView textView;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private CancellationSignal cancellationSignal;
    /////////////////////////////////////////////

    private EditText et_1, et_2, et_3, et_4;
    private TextView tv_forgot_mpin, tv_or;
    private TextView btn_login;
    private Context context;
    private int notification;
    private AlertDialog mBioAlertDialog;
    private  String android_id;
    private  String mpin,login_token;
    private   ViewGroup mViewGroup;
    private LinearLayout m_lyt;
    private TextView mBtn_biometric;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_mpin, container, false);
        try {
            context = view.getContext();
            et_1 = view.findViewById(R.id.et_1);
            et_2 = view.findViewById(R.id.et_2);
            et_3 = view.findViewById(R.id.et_3);
            et_4 = view.findViewById(R.id.et_4);
            m_lyt = view.findViewById(R.id.lyt);
            mBtn_biometric= view.findViewById(R.id.btn_biometric);

            tv_forgot_mpin = view.findViewById(R.id.tv_forgot_mpin);

            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            mViewGroup = view.findViewById(android.R.id.content);
            getActivity().findViewById(R.id.toolbar_cart).setVisibility(View.GONE);

            Bundle bundle = getArguments();
            //    login_token =bundle.getString("loginToken");

            login_token = GlobalClass.sharedPreferences1.getString("logic_token","");


            init();
            notification = getArguments().getInt("notification", 0);

            /*android_id = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);*/

            mBtn_biometric.setOnClickListener(v->{
                showBio();
                m_lyt.setAlpha(0.2f);
                initiateFingerprintlistner();
            });

            mBtn_biometric.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);

            if (fingerprintManager != null) {
                if (fingerprintManager.isHardwareDetected()) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        GlobalClass.ShowAlet(context, "Alert", "Please enable the fingerprint permission");
                        //enableBioWithoutBiometric();
                    } else if (!fingerprintManager.hasEnrolledFingerprints()) {

                        //  GlobalClass.ShowAlet(context, "Alert", "No fingerprint configured. Please register at least one fingerprint in your device's Settings to use BIOMETRIC to login");
                        //  enableBioWithoutBiometric();
                    } else {
                        //  enableBIometric();
                        mBtn_biometric.setVisibility(View.VISIBLE);
                        showBio();
                        m_lyt.setAlpha(0.2f);
                        initiateFingerprintlistner();
                    }

                } else {
                    // enableMPIN();
                }
                //password protection

            } else {
                //enableMPIN();
            }


        }



    }

    private void initiateFingerprintlistner() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                generateKey();
            } catch (FingerprintException e) {
                e.printStackTrace();
            }
            if (initCipher()) {
                try {
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(context, this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                    cancellationSignal = new CancellationSignal();
                    fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, helper, null);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

/*    private void enableBioWithoutBiometric() {
       // lyt_biometric.setVisibility(View.GONE);
        mpin_lyt.setVisibility(View.VISIBLE);
        btn_login.setText("USE BIOMETRIC");

    }

    private void enableMPIN() {
      //  lyt_biometric.setVisibility(View.GONE);
        mpin_lyt.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
      //  tv_or.setVisibility(View.GONE);
    }

    private void enableBIometric() {
       // lyt_biometric.setVisibility(View.VISIBLE);
        mpin_lyt.setVisibility(View.GONE);
        btn_login.setText("USE MPIN");
    }*/

    private void init() {

        et_1.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et_2.requestFocus();
                        }
                    }, GlobalClass.mDelay);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        et_2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et_3.requestFocus();
                        }
                        if (s.toString().isEmpty()) {
                            et_1.requestFocus();
                        }
                    }
                }, GlobalClass.mDelay);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        et_3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et_4.requestFocus();
                        }
                        if (s.toString().isEmpty()) {
                            et_2.requestFocus();
                        }
                    }
                }, GlobalClass.mDelay);


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        et_4.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et_4.onEditorAction(EditorInfo.IME_ACTION_DONE);
                            login();
                        }
                        if (s.toString().isEmpty()) {
                            et_3.requestFocus();
                        }
                    }
                }, GlobalClass.mDelay);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        tv_forgot_mpin.setOnClickListener(v -> {
            Fragment fragment = new UserAuthenticationFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("notification", getArguments().getInt("notification"));
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        });
    }

    private void login() {
        mpin = et_1.getText().toString() +
                et_2.getText().toString() +
                et_3.getText().toString() +
                et_4.getText().toString();
        if (mpin.length() == 4) {
            LoginMpin(mpin, android_id);
        } else {
            GlobalClass.ShowAlet(context, "Alert", "Please enter valid Mpin");
        }
    }

    private void LoginMpin(String mpin, String device_id) {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> map = new HashMap<>();
        map.put("mpin", mpin);
        map.put("deviceID", GlobalClass.android_id);
        if (!login_token.isEmpty()){
            map.put("loginToken",login_token);
        }
        Call<LoginMpin> loginMpincall = api.loginMpin(map);
        APIResponse.postCallRetrofit(loginMpincall, "loginMpin", context, this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        try {
            Log.d("agee","ag");
            LoginMpin loginMpin = (LoginMpin) response.body();
            Data data = loginMpin.getData();
            if (apiCallName.equalsIgnoreCase("loginMpin")) {
                if (loginMpin.getStatusCode().equals(200)) {

                    Log.d("lg","lg");
                    GlobalClass.token = data.getAccessToken();
                    //GlobalClass.Location_UUID = data.getReservationInfo().getCurrent().get(0).getPropertyInfo().getLocationUUID();

                    /*GlobalClass.mDeviceID = Settings.Secure.getString(getContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);*/
                    GlobalClass.putsharedpreference(context, data.getGuestInfo().getFirstName(),
                            data.getGuestInfo().getLastName(),
                            data.getGuestInfo().getMobile(),
                            data.getGuestInfo().getEmail(),
                            data.getAccessToken(),
                            data.getGuestInfo().getMaritalStatus(),
                            data.getGuestInfo().getGender(),
                            data.getGuestInfo().getProfileImage(),
                            data.getGuestInfo().getDateOfBirth(),
                            data.getGuestInfo().getAnniversaryDate(),
                            data.getGuestInfo().getSpouseDateOfBirth());
                    if(mBioAlertDialog!=null && mBioAlertDialog.isShowing()){
                        mBioAlertDialog.dismiss();
                        stopFingerAuth();
                    }
                    /*Intent intent = new Intent();
                    intent.setClassName(context, "com.application.refinery.activity.HomeScreenActivity");
                    *//*intent.putExtra("changes", "");
                    intent.putExtra("notification", notification);
                    intent.putExtra("route_name",getArguments().getString("route_name"));
                    intent.putExtra("ticket_number",getArguments().getString("ticket_number"));
                    GlobalClass.loacation = loginMpin.getResult().getLocation().getLocationName();*//*
                    startActivity(intent);
                    getActivity().finish();*/


                    /*Fragment fragment=new Weather();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();*/


                    Intent intent = new Intent(context, HomeScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    if (loginMpin.getData().getReservationInfo().getCurrent().size() > 0) {
                        GlobalClass.hasActiveBooking = true;
                        GlobalClass.mUserCheckInDate=GlobalClass.inputdateformat.parse(loginMpin.getData().getReservationInfo().getCurrent().get(0).getCheckInDate());
                        GlobalClass.mUserCheckOutDate=GlobalClass.inputdateformat.parse(loginMpin.getData().getReservationInfo().getCurrent().get(0).getCheckOutDate());
                        //  GlobalClass.Booking_id = loginMpin.getResult().getActiveBooking().get(0).getId() != null ? loginMpin.getResult().getActiveBooking().get(0).getId() : 0;
                        GlobalClass.Booking_Number=loginMpin.getData().getReservationInfo().getCurrent().get(0).getBookingConfNo() != null ? loginMpin.getData().getReservationInfo().getCurrent().get(0).getBookingConfNo() : "";
                        GlobalClass.Location_ID = loginMpin.getData().getReservationInfo().getCurrent().get(0).getPropertyInfo().getLocationUUID() != null ? loginMpin.getData().getReservationInfo().getCurrent().get(0).getPropertyInfo().getLocationUUID() : "";
                        GlobalClass.Room_no = loginMpin.getData().getReservationInfo().getCurrent().get(0).getRoomDetails().get(0).getRoomNumber() != null ? loginMpin.getData().getReservationInfo().getCurrent().get(0).getRoomDetails().get(0).getRoomNumber() : 0;
                    } else {
                        GlobalClass.hasActiveBooking = false;
                    }
                    GlobalClass.Guest_id = loginMpin.getData().getGuestInfo().getPmsID() != null ? Integer.valueOf(loginMpin.getData().getGuestInfo().getPmsID()) : 0;
                    GlobalClass.Guest_UUID = loginMpin.getData().getGuestInfo().getGuestUUID() != null ? loginMpin.getData().getGuestInfo().getGuestUUID() : "";

                    //  GlobalClass.MY_ROOMS = loginMpin.getResult().getActiveBooking().size()>0 ? loginMpin.getResult().getActiveBooking().get(0).getRoom():null;

                } else {
                    GlobalClass.ShowAlet(context, "Alert", loginMpin.getMessage());
                    try {
                        initiateFingerprintlistner();
                        et_1.setText("");
                        et_2.setText("");
                        et_3.setText("");
                        et_4.setText("");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                et_1.requestFocus();
                            }
                        }, 300);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorListner() {
    }

    private void generateKey() throws FingerprintException {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");


            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(new
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
            }

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean initCipher() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @Override
    public void onSucessfullBiometricAuth() {
        LoginMpin( GlobalClass.sharedPreferences.getString("userM-pin","" ), android_id);
    }

    private class FingerprintException extends Exception {

        public FingerprintException(Exception e) {
            super(e);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        try{
            stopFingerAuth();

            if(mBioAlertDialog!=null && mBioAlertDialog.isShowing()){
                mBioAlertDialog.dismiss();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopFingerAuth(){
        if(cancellationSignal != null && !cancellationSignal.isCanceled()){
            cancellationSignal.cancel();
        }
    }

    private void showBio() {
        try {
            TextView btn_cancel;

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(context).inflate(R.layout.mpin_biometric_overlay, mViewGroup, false);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.NewDialog);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);
            // mBioAlertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //finally creating the alert dialog and displaying it
            mBioAlertDialog = builder.create();
            btn_cancel = dialogView.findViewById(R.id.btn_cancel);

            btn_cancel.setOnClickListener(v -> {
                mBioAlertDialog.dismiss();
                m_lyt.setAlpha(1);
                stopFingerAuth();
            });
            mBioAlertDialog.show();

            mBioAlertDialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
}