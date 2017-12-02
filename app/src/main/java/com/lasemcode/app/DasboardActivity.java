package com.lasemcode.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class DasboardActivity extends AppCompatActivity {
    public static final String KEYPREF     = "LocalData";
    int PLACE_PICKER_REQUEST = 1;
    Button btnLocation;
    EditText alamat, latitude;
    WebView attributionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        btnLocation = (Button) findViewById(R.id.location);
        alamat = (EditText) findViewById(R.id.editAlamat);
        latitude = (EditText) findViewById(R.id.editLatitude);
        attributionText = (WebView) findViewById(R.id.webView);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Log.i("test","cek picker");
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnLogout:
                Log.i("test","logout");
                Intent i = new Intent(this,LoginActivity.class);
                SharedPreferences pref = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.clear();
                edit.commit();
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;

    }

    public void getLocation(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK){
//            if(requestCode == RESULT_OK){
//                Place place = PlacePicker.getPlace(DasboardActivity.this, data);
//                String address = String.valueOf(place.getAddress());
//                String lat = String.valueOf(place.getLatLng());
//                alamat.setText(address);
//                latitude.setText(lat);
//                if(place.getAttributions() == null){
//                    attributionText.loadData("no attribution", "text/html; charset=utf-8", "UFT-8");
//                }else{
//                    attributionText.loadData(place.getAttributions().toString(), "text/html; charset=utf-8", "UFT-8");
//                }
//
//            }
            displayPlacePicker(data);
        }
    }

    private void displayPlacePicker(Intent data){
        Place place = PlacePicker.getPlace(data, this);

        String address = String.valueOf(place.getAddress());
        String lat = String.valueOf(place.getLatLng());
        alamat.setText(address);
        latitude.setText(lat);

    }
}
