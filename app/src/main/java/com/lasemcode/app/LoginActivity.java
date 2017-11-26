package com.lasemcode.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {

    EditText userName,passWord;
    Button btnMasuk;
    SharedPreferences preferences;
    public static final String KEYPREF     = "LocalData";
    public static final String KEYUSERNAME = "Username";
    public static final String KEYPASSWORD = "Password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        btnMasuk = (Button) findViewById(R.id.masuk);

        preferences = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);

        if (preferences.contains(KEYUSERNAME) && (preferences.contains(KEYPASSWORD))) {
            userName.setText(preferences.getString(KEYUSERNAME, ""));
            passWord.setText(preferences.getString(KEYPASSWORD, ""));
        }

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAkun(v);
            }
        });

    }

        public void loginAkun(View view) {
            String user = userName.getText().toString();
            String pass = passWord.getText().toString();
            Log.i("test",pass);
            if(user.equals("pugerp") && pass.equals("asd123")){
                Log.i("test","test2");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEYUSERNAME, user);
                editor.putString(KEYPASSWORD, pass);
                editor.apply();

                Intent intent = new Intent(this, DasboardActivity.class);
                startActivity(intent);

                clearForm();

                Toast.makeText(this, "Username dan Password Success", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Username dan Password ada yang salah", Toast.LENGTH_SHORT).show();
                clearForm();
            }



        }

        public void clearForm(){
            userName.setText("");
            passWord.setText("");
        }

        public String getUser(){
            SharedPreferences pref = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);
            return pref.getString(KEYUSERNAME,"");
        }

    @Override
    protected void onStart() {
        super.onStart();
//        if(getUser().length() != 0){
//            Log.i("test","tidak sama dengan 0");
//            Intent i = new Intent(this,DasboardActivity.class);
//            startActivity(i);
//            finish();
//        }else {
//            Log.i("test","sama dengan 0");
//            clearForm();
//        }
    }
}

