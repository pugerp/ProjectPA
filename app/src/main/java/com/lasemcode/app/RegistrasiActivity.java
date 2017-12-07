package com.lasemcode.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegistrasiActivity extends AppCompatActivity {
    EditText email, nama, username, password;
    Button daftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        daftar = (Button) findViewById(R.id.btnDaftar);
        nama = (EditText) findViewById(R.id.editNama);
        email = (EditText) findViewById(R.id.editEmail);
        username = (EditText) findViewById(R.id.editUser);
        password = (EditText) findViewById(R.id.editPass);



    }
}
