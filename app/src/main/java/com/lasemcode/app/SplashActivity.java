package com.lasemcode.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    public static final String KEYPREF     = "LocalData";
    public static final String KEYUSERNAME = "Username";
    private static int splashInterval = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Thread t = new Thread(){
            @Override
            public void run() {

                try{
                    sleep(splashInterval);
                    if(getUser().length() != 0){
                        Log.i("test","tidak sama dengan 0");
                        Intent i = new Intent(SplashActivity.this,DasboardActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Log.i("test","sama dengan 0");
                        Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
    };
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(getUser().length() != 0){
//            Log.i("test","tidak sama dengan 0");
//            Intent i = new Intent(this,DasboardActivity.class);
//            startActivity(i);
//            finish();
//        }else {
//            Log.i("test","sama dengan 0");
//            Intent i = new Intent(this,LoginActivity.class);
//            startActivity(i);
//            finish();
//        }
//    }
    public String getUser(){
        SharedPreferences pref = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);
        return pref.getString(KEYUSERNAME,"");
    }
}