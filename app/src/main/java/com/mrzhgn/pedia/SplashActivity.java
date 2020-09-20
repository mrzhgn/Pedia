package com.mrzhgn.pedia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isAuth = sPref.getBoolean("isAuth", false);

        if (!isAuth) {
            Intent intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("login", sPref.getString("splash_login", null));
            startActivity(intent);
            finish();
        }
    }
}
