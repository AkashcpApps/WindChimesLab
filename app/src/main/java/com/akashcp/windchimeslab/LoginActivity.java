package com.akashcp.windchimeslab;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.FingerprintGestureController;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements FingerPrintAuthCallback {
    List<AuthUI.IdpConfig> providers;
    FingerPrintAuthHelper fingerPrintAuthHelper;
    private LottieAnimationView animationView;
    TextView auth;
    PatternLockView mPatternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button=(Button)findViewById(R.id.button);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        auth=(TextView)findViewById(R.id.auth);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

    }
    @Override
    protected void onResume() {
        super.onResume();
        //start finger print authentication
        fingerPrintAuthHelper.startAuth();
    }
    @Override
    protected void onPause() {
        super.onPause();
        fingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        auth.setText("NoFingerPrintHardwareFound");
        auth.setTextColor(Color.parseColor("#FF0000"));

    }

    @Override
    public void onNoFingerPrintRegistered() {

    }

    @Override
    public void onBelowMarshmallow() {
        auth.setText("BelowMarshmallow");
        auth.setTextColor(Color.parseColor("#FF0000"));

    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        auth.setText("AuthSuccess");
        auth.setTextColor(Color.parseColor("#90EE90"));
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();

    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        auth.setText("AuthFailed\nplease try again");
        auth.setTextColor(Color.parseColor("#FF0000"));

    }
    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
            auth.setText("AuthSuccess");
            auth.setTextColor(Color.parseColor("#90EE90"));
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };
}
