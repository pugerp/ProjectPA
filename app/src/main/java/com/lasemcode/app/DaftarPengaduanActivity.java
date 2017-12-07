package com.lasemcode.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DaftarPengaduanActivity extends AppCompatActivity {
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pengaduan);

        btnBack = (Button) findViewById(R.id.button2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(DaftarPengaduanActivity.this, DasboardActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}
