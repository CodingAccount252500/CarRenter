package com.example.user_.bookinguser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;

public class LogoAvtivity extends Activity {
    private static int S=4000;
    private static final int REQUEST_LOCATION = 1;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
                    ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);


                    setContentView(R.layout.activity_logo_avtivity);
                    new Handler().postDelayed(new Runnable() {
@Override
public void run() {
        Intent intent=new Intent(LogoAvtivity.this, WelcomToMyCardActivity.class);
        startActivity(intent);
        finish();
        }
        }, S);

    }


        }
