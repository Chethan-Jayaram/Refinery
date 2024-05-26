package com.application.refinary.fragment.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.CustomMessageHelper;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.authenticateMobile.AuthenticateMobile;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


public class UserAuthenticationFragment extends Fragment implements ApiListener {

    private Context context;
    private ProgressBar loading;
    private EditText et_phone_no;
    private CountryCodePicker country_code_picker;
    private TextView email_ph_text;
    private Boolean isEmailSelected=false;
    private CheckBox reg_check_box;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_authentication, container, false);
        try{
            context = view.getContext();
            loading=view.findViewById(R.id.loading);
            et_phone_no = view.findViewById(R.id.et_phone_no);
            Button btn_send_otp = view.findViewById(R.id.btn_send_otp);
            country_code_picker = view.findViewById(R.id.country_code_picker);
            email_ph_text= view.findViewById(R.id.email_ph_text);
            email_ph_text.setText("Login via E-mail");
            et_phone_no.setHint("Enter Your Phone Number");
            et_phone_no.requestFocus();
            et_phone_no.setInputType(InputType.TYPE_CLASS_NUMBER);
            TextView mpin_Login=view.findViewById(R.id.mpin_Login);
            TextView tv_register_now=view.findViewById(R.id.tv_register_now);
            reg_check_box = view.findViewById(R.id.reg_check_box);


           /* reg_check_box.setText(Html.fromHtml("<body>\n" +
                    "        <p" +
                    "          style=\"color:#857d6b\">  Do you Agree to our Terms &amp; Conditions?" +
                    "    </body>"));*/


            reg_check_box.setText(Html.fromHtml("<body>\n" +
                    "        <p" +
                    "          style=\"color:#857d6b\">  I agree with the  <a href=\"https://www.refineryhotelnewyork.com/terms/\" style=\"color:#857d6b\">Terms &amp; Conditions</a>\n" +
                    "    </body>"));
            reg_check_box.setClickable(false);
            reg_check_box.setMovementMethod(LinkMovementMethod.getInstance());


            mpin_Login.setOnClickListener(v->{
                Fragment fragment=new LoginMpinFragment();
                Bundle bundle=new Bundle();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            });
            tv_register_now.setOnClickListener(v->{
                Fragment fragment1=new RegistrationFragment();
                Bundle bundle1=new Bundle();
                fragment1.setArguments(bundle1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).addToBackStack(null).commit();

            });
            getActivity().findViewById(R.id.toolbar_cart).setVisibility(View.GONE);
            btn_send_otp.setOnClickListener(v -> {

                if (reg_check_box.isChecked()){
                    if (et_phone_no.getText().length() > 0) {
                        MobileLogin(et_phone_no.getText().toString().trim());
                    } else {
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Message", "Please enter your phone number", false, false);
                    }
                }else{
                    GlobalClass.showErrorMsg(context,"Check condition checkbox");
                }


            });
            email_ph_text.setOnClickListener(v->{
                et_phone_no.setText("");
                if(isEmailSelected){
                    isEmailSelected=false;
                    et_phone_no.setInputType(InputType.TYPE_CLASS_NUMBER);
                    country_code_picker.setVisibility(View.VISIBLE);
                    email_ph_text.setText("Login via E-mail");
                    et_phone_no.setHint("Enter Your Phone Number");
                }else{
                    isEmailSelected=true;
                    country_code_picker.setVisibility(View.INVISIBLE);
                    et_phone_no.setInputType(InputType.TYPE_CLASS_TEXT);
                    email_ph_text.setText("Login via Phone Number");
                    et_phone_no.setHint("Enter Your E-mail ID");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void MobileLogin(String mobile_no) {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map map = new HashMap();
        map.put("deviceID",GlobalClass.android_id);
        if(!isEmailSelected) {
            map.put("dialCode",country_code_picker.getSelectedCountryCode());
            map.put("mobile", mobile_no);
        }else{
            map.put("email", mobile_no);
        }
        Call<AuthenticateMobile> authenticateMobilecall = api.userAuthentication(map);
        APIResponse.postCallRetrofit(authenticateMobilecall, "userAuth", context, this);
    }

    private void navigate(Fragment fragment) {
        et_phone_no.setText("");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }


    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        try {
            AuthenticateMobile authenticateMobile = (AuthenticateMobile) response.body();
            if (apiCallName.equalsIgnoreCase("userAuth")) {
                if (authenticateMobile.getStatusCode().equals(200) || authenticateMobile.getStatusCode().equals(201)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("requestID", authenticateMobile.getData().getRequestID());
                    if(isEmailSelected) {
                        bundle.putString("mobile_no",et_phone_no.getText().toString());
                    }else{
                        bundle.putString("mobile_no","+"+country_code_picker.getSelectedCountryCode()+"-"+et_phone_no.getText().toString());
                    }
                    Fragment fragment = new VerifyOtpFragment();
                    fragment.setArguments(bundle);
                    navigate(fragment);
                } else {
                    GlobalClass.showErrorMsg(context, authenticateMobile.getMessage());
                }
            }
        } catch (Exception e) {
            Log.d("auth",e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorListner() {
    }
}