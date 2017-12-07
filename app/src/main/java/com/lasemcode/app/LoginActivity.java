package com.lasemcode.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    EditText userName,passWord;
    Button btnMasuk;
    TextView btnSignUp;
    SharedPreferences preferences;
    DatabaseReference mDatabase;
    FirebaseDatabase database;
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
        btnSignUp = (TextView) findViewById(R.id.signUp);

        preferences = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("pengguna");

        if (preferences.contains(KEYUSERNAME) && (preferences.contains(KEYPASSWORD))) {
            userName.setText(preferences.getString(KEYUSERNAME, ""));
            passWord.setText(preferences.getString(KEYPASSWORD, ""));
        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LoginActivity.this, RegistrasiActivity.class);
                startActivity(in);
                finish();
            }
        });
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAkun(v);
            }
        });

    }

        public void loginAkun(View view) {
            final String user = userName.getText().toString();
            final String pass = passWord.getText().toString();
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(user).exists()){
                        Pengguna pengguna = dataSnapshot.child(user).getValue(Pengguna.class);

                        if(pengguna.getPassword().equals(pass)){
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(KEYUSERNAME, user);
                            editor.putString(KEYPASSWORD, pass);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, DasboardActivity.class);
                            startActivity(intent);

                            clearForm();
                            finish();
                            Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Password Salah",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(LoginActivity.this, "Usernama Salah",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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

