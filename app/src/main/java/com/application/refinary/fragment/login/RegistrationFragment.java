package com.application.refinary.fragment.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.CustomMessageHelper;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.authenticateMobile.AuthenticateMobile;
import com.application.refinary.pojo.authenticateMobile.Data;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class RegistrationFragment extends Fragment implements ApiListener {

    private TextView tv_fname,tv_lname,tv_contact_no,tv_email;
    private Button btn_register;
    private CountryCodePicker reg_country_code_picker;
    private Context mCtx;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Spinner salutationSpinner;
    private String[] salutation = {"Mr.","Mrs.","Dr.","Ms."};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registration2,container,false);
        mCtx=view.getContext();
        getActivity().findViewById(R.id.toolbar_cart).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.nav_menu).setVisibility(View.GONE);
        getActivity().findViewById(R.id.iv_sos).setVisibility(View.GONE);
        TextView toolbar_title= getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Registration");

        tv_fname=view.findViewById(R.id.tv_fname);
        tv_lname=view.findViewById(R.id.tv_lname);
        tv_contact_no=view.findViewById(R.id.tv_contact_no);
        btn_register=view.findViewById(R.id.btn_register);
        tv_email=view.findViewById(R.id.tv_email);
        reg_country_code_picker=view.findViewById(R.id.reg_country_code_picker);
    //    salutationSpinner = view.findViewById(R.id.salutationSpinner);

        tv_fname.requestFocus();

       /* ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.spinner_list,salutation);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        salutationSpinner.setAdapter(arrayAdapter);
*/
        btn_register.setOnClickListener(v->{
            if (tv_contact_no.getText().length() > 0 &&  !tv_fname.getText().toString().isEmpty()
                    && !tv_email.getText().toString().isEmpty() && ! tv_lname.getText().toString().isEmpty()){
                if( tv_email.getText().toString().trim().matches(emailPattern)){
                    registerUser(tv_contact_no.getText(),
                            tv_fname.getText().toString(),
                            tv_lname.getText().toString(),
                            tv_email.getText().toString().trim());
                }else{
                    CustomMessageHelper showDialog = new CustomMessageHelper(mCtx);
                    showDialog.showCustomMessage((Activity) mCtx, "Message", "Invalid email address", false, false);
                }
            }else {
                CustomMessageHelper showDialog = new CustomMessageHelper(mCtx);
                showDialog.showCustomMessage((Activity) mCtx, "Message", "Fields can't be left blank", false, false);
            }
        });
        return view;
    }

    private void registerUser(CharSequence no, String fname, String lname, String email) {
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String,String> map = new HashMap<>();
       // map.put("salutation",salutationSpinner.getSelectedItem().toString());
        map.put("firstName",fname);
        map.put("lastName",lname);
        map.put("email",email);
        map.put("dialCode", "+"+ reg_country_code_picker.getSelectedCountryCode());
        map.put("mobile", String.valueOf(no));
        map.put("deviceID",GlobalClass.android_id);
        Call<AuthenticateMobile> register = api.regUser(map);
        APIResponse.postCallRetrofit(register,"regUser", mCtx, this);
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {
        try {

            if (apiCallName.equalsIgnoreCase("regUser")) {
                AuthenticateMobile authenticateMobile = (AuthenticateMobile) response.body();

                Log.d("statusCode",authenticateMobile.getStatusCode().toString());

                if (authenticateMobile.getStatusCode().equals(201)){

                    Data data = authenticateMobile.getData();
                    Log.d("requestID",data.getRequestID());

                    Fragment fragment = new VerifyOtpFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("requestID", data.getRequestID());
                    bundle.putString("mobile_no", tv_contact_no.getText().toString());
                    fragment.setArguments(bundle);
                    //navigate(fragment);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                } else {
                    GlobalClass.showErrorMsg(mCtx, authenticateMobile.getMessage());
                }

                }


            }
           /* if (apiCallName.equalsIgnoreCase("regUser")) {
                if (authenticateMobile.getStatusCode().equals(201)) {
                    Fragment fragment = new VerifyOtpFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("requestID", data.getRequestID());
                    bundle.putString("mobile_no", tv_contact_no.getText().toString());
                    fragment.setArguments(bundle);
                    //navigate(fragment);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                } else {
                    GlobalClass.showErrorMsg(mCtx, authenticateMobile.getMessage());
                }
            }*/
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorListner() {

    }

    private void navigate(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

}