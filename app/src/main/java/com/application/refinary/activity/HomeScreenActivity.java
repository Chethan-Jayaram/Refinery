package com.application.refinary.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.fragment.general.EditProfile;
import com.application.refinary.fragment.general.HomeGridFragment;
import com.application.refinary.fragment.general.Notification;
import com.application.refinary.helper.GlobalClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private View headerLayout;
    private Vibrator vibrator;
    private ProgressDialog dialog;
    private ImageView nav_menu;
    private ImageView iv_sos;
    private ImageView img_et_btn;
    private AlertDialog mWelcomealertDialog;
    private int notification;
    private   ImageView btn_back;
    TextView user_name,tv_privacy,tv_conditions;
    TextView email;
    private ImageView img_profile_photo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try{

            tv_privacy = findViewById(R.id.txt1);
            tv_conditions = findViewById(R.id.txt3);
            nav_menu = findViewById(R.id.nav_menu);
            btn_back = findViewById(R.id.btn_back);
            NavigationView navigationView = findViewById(R.id.nav_view);
            iv_sos = findViewById(R.id.iv_sos);
            drawer = findViewById(R.id.drawer_layout);
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);


            headerLayout = navigationView.getHeaderView(0);
            user_name = headerLayout.findViewById(R.id.user_name);
            email = headerLayout.findViewById(R.id.user_email);
            img_profile_photo = headerLayout.findViewById(R.id.img_profile_photo);
            btn_back.setOnClickListener(v -> {
                onBackPressed();
            });


            tv_privacy.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.refineryhotelnewyork.com/privacy-policy/"));
                startActivity(browserIntent);
            });


            tv_conditions.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.refineryhotelnewyork.com/terms/"));
                startActivity(browserIntent);
            });

            headerLayout.setOnClickListener(v -> {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                if (fragments != null) {
                    if(!(fragments.get(fragments.size()-1) instanceof EditProfile)){
                        this.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new EditProfile()).addToBackStack(null).commit();
                        onBackPressed();
                    }
                }
            });

            nav_menu.setOnClickListener(v -> {
                hideKeybord();
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else
                    drawer.openDrawer(GravityCompat.START);
            });

            iv_sos.setOnClickListener(v -> {
                if (!GlobalClass.mPreviousRouteName.equalsIgnoreCase("notification")) {
                    GlobalClass.mPreviousRouteName = "notification";
                    this.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new Notification()).addToBackStack(null).commit();
                }
            });


            Fragment fragment = new HomeGridFragment();

                this.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).commit();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void hideKeybord() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(nav_menu.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        try {
            onResume();
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        try {
            performMadetoryOperations();
            super.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performMadetoryOperations() {
        /*GlobalClass.mISVisible = false;
        if (GlobalClass.hasActiveBooking) {
            iv_sos.setVisibility(View.VISIBLE);
        } else {
            iv_sos.setVisibility(View.GONE);
        }
        img_et_btn.setVisibility(View.VISIBLE);
        nav_menu.setVisibility(View.VISIBLE);
        TextView user_name = headerLayout.findViewById(R.id.user_name);
        TextView email = headerLayout.findViewById(R.id.user_email);
        ImageView img_profile_photo = headerLayout.findViewById(R.id.img_profile_photo);
        user_name.setText(GlobalClass.sharedPreferences.getString("fName", "") + " " + GlobalClass.sharedPreferences.getString("lName", ""));
        Glide.with(this)
                .load(GlobalClass.sharedPreferences.getString("img", ""))
                .apply(RequestOptions.placeholderOf(R.drawable.profile_image).error(R.drawable.profile_image))
                .into(img_profile_photo);
        email.setText(GlobalClass.sharedPreferences.getString("eMail", ""));*/
        GlobalClass.mPreviousRouteName = "";
        user_name.setText(GlobalClass.sharedPreferences.getString("fName", "") + " " + GlobalClass.sharedPreferences.getString("lName", ""));
        email.setText(GlobalClass.sharedPreferences.getString("eMail", ""));
        Glide.with(getApplicationContext())
                .load(GlobalClass.sharedPreferences.getString("img", ""))
                .apply(RequestOptions.placeholderOf(R.drawable.profile_image).error(R.drawable.profile_image))
                .into(img_profile_photo);
    }

    private void showWelcomeDailoug() {
        try {
            Button btn_getstarted;
            ImageView iv_done;
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);


            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.welcome_dailoug, viewGroup, false);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            mWelcomealertDialog = builder.create();
            btn_getstarted = dialogView.findViewById(R.id.btn_getstarted);
            iv_done = dialogView.findViewById(R.id.iv_done);
            btn_getstarted.setOnClickListener(v -> mWelcomealertDialog.dismiss());
            mWelcomealertDialog.show();
            Animatable animatable = (Animatable) iv_done.getDrawable();
            animatable.start();
            mWelcomealertDialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


}