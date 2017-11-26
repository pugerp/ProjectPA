package com.lasemcode.app;

import android.content.Context;
import android.content.SharedPreferences;
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
    public static final String KEYPREF     = "Key Preferences";
    public static final String KEYUSERNAME = "Key Username";
    public static final String KEYPASSWORD = "Key Password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

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
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEYUSERNAME, user);
            editor.putString(KEYPASSWORD, pass);
            editor.apply();
            Toast.makeText(this, "Username dan Password Success", Toast.LENGTH_SHORT).show();

        }
}

