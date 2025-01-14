package com.application.refinary.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.application.refinary.fragment.login.LoginMpinFragment;


@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;
    private GlobalClass.OnBiometricAuthSucess listener;


    public FingerprintHandler(Context mContext, LoginMpinFragment loginMpinFragment) {
        context = mContext;
        listener=loginMpinFragment;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        // Toast.makeText(context,"Authentication error\n" + errString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        listener.onSucessfullBiometricAuth();
    }
}
