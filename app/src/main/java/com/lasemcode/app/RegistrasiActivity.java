package com.lasemcode.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrasiActivity extends AppCompatActivity {
    EditText surel, nama, username, password;
    Button btnDaftar;
    DatabaseReference mDatabase;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        nama = (EditText) findViewById(R.id.editNama);
        surel = (EditText) findViewById(R.id.editEmail);
        username = (EditText) findViewById(R.id.editUser);
        password = (EditText) findViewById(R.id.editPass);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("pengguna");

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(username.getText().toString()).exists()){
                            Toast.makeText(RegistrasiActivity.this, "Username sudah ada", Toast.LENGTH_SHORT).show();
                        }else{
                            Pengguna pengguna = new Pengguna(surel.getText().toString(), nama.getText().toString(), password.getText().toString());
                            mDatabase.child(username.getText().toString()).setValue(pengguna);
                            Intent in = new Intent(RegistrasiActivity.this, LoginActivity.class);
                            startActivity(in);
                            Toast.makeText(getApplicationContext(),"Sign In Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}
