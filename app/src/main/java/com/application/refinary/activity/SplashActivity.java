package com.application.refinary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;


public class SplashActivity extends Activity implements Animation.AnimationListener {

    private static final int MY_REQUEST_CODE = 99 ;
    private Animation animTogether;
    private ImageView logo,iv_log_txt;
    private int notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            logo=findViewById(R.id.logo);
            iv_log_txt=findViewById(R.id.iv_log_txt);
            animTogether = AnimationUtils.loadAnimation(this,R.anim.logo );
            animTogether.setAnimationListener(this);
            iv_log_txt.startAnimation(animTogether);
            logo.startAnimation(animTogether);
            Intent intent = getIntent();
            notification = intent.getIntExtra("notification", 0);

            //splash screen will be shown for 2 seconds
            int SPLASH_DISPLAY_LENGTH = 1800;

            // Creates instance of the manager.
            AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
            // Returns an intent object that you use to check for an update.
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks whether the platform allows the specified type of update,
// and checks the update priority.
            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        /*      && appUpdateInfo.updatePriority() >= HIGH_PRIORITY_UPDATE*/
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                AppUpdateType.IMMEDIATE,
                                // The current activity making the update request.
                                this,
                                // Include a request code to later monitor this update request.
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }else if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        /*      && appUpdateInfo.updatePriority() >= HIGH_PRIORITY_UPDATE*/
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ){

                }




            });


            new Handler().postDelayed(() -> {
                getSharedPreferences();
                Intent mainIntent = new Intent(SplashActivity.this, UserAuthenticationActivity.class);
                mainIntent.putExtra("notification", notification);
                mainIntent.putExtra("route_name",intent.getStringExtra("route_name"));
                mainIntent.putExtra("ticket_number",intent.getStringExtra("ticket_number"));
                startActivity(mainIntent);
                finish();
            }, SPLASH_DISPLAY_LENGTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //mark mpinSetupComplete
    private void getSharedPreferences() {
        GlobalClass.sharedPreferences = this.getSharedPreferences(GlobalClass.shredPrefName, 0);
        GlobalClass.isMpinSetupComplete = GlobalClass.sharedPreferences.getBoolean("isMpinSetupComplete",false );
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_REQUEST_CODE) {
            Intent intent=new Intent(this,SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }
}