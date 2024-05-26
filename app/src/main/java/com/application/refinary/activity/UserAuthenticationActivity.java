package com.application.refinary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import com.application.refinary.R;
import com.application.refinary.fragment.login.LoginMpinFragment;
import com.application.refinary.fragment.login.UserAuthenticationFragment;
import com.application.refinary.helper.GlobalClass;

public class UserAuthenticationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            Fragment fragment=new LoginMpinFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("notification",getIntent().getIntExtra("notification",0));
            bundle.putString("route_name",getIntent().getStringExtra("route_name"));
            bundle.putString("ticket_number",getIntent().getStringExtra("ticket_number"));
            fragment.setArguments(bundle);
            if(getIntent().getBooleanExtra("logout",false)){
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
            ImageView btn_back = findViewById(R.id.btn_back);
            findViewById(R.id.nav_menu).setVisibility(View.GONE);
            findViewById(R.id.iv_sos).setVisibility(View.GONE);
            if (GlobalClass.sharedPreferences.getBoolean("isMpinSetupComplete", false)) {
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }else{
                Fragment fragment1=new UserAuthenticationFragment();
                Bundle bundle1=new Bundle();
                bundle1.putInt("notification",getIntent().getIntExtra("notification",0));
                bundle1.putString("route_name",getIntent().getStringExtra("route_name"));
                // bundle1.putString("ticket_number",getIntent().getStringExtra("ticket_number"));
                fragment1.setArguments(bundle);
                this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).commit();
            }
            btn_back.setOnClickListener(v -> onBackPressed());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}