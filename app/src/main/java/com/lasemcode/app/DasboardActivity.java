package com.lasemcode.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import com.google.android.gms.maps.model.LatLng;

import java.io.File;

public class DasboardActivity extends AppCompatActivity {
    public static final String KEYPREF     = "LocalData";
    int PLACE_PICKER_REQUEST = 1, REQUEST_CAMERA = 2, SELECT_FILE = 0;
    Button btnLocation, btnPhoto;
    EditText alamat, latitude, photoPath, longtitude;
    WebView attributionText;
    Uri UrlGambar;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        btnLocation = (Button) findViewById(R.id.location);
        btnPhoto = (Button) findViewById(R.id.btnSelectPhoto);
        alamat = (EditText) findViewById(R.id.editAlamat);
        photoPath = (EditText) findViewById(R.id.editPhoto);
        latitude = (EditText) findViewById(R.id.editLatitude);
        longtitude = (EditText) findViewById(R.id.editLongitude);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Log.i("test","cek picker");
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
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

    private void selectPhoto(){
        final CharSequence[] items = {"Camera","Gallery","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DasboardActivity.this);
        builder.setTitle("Tambahkan Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file = new File(Environment.getExternalStorageDirectory(), "Foto Jalan Rusak/img_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                    UrlGambar = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, UrlGambar);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }else if(items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path ="";
        if(requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK){
            displayPlacePicker(data);
        }

        if(resultCode == DasboardActivity.RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                path = file.getPath();
            }else if(requestCode == SELECT_FILE){
                UrlGambar = data.getData();
                path = getRealPath(UrlGambar);
                if(path == null){
                    path = UrlGambar.getPath();
                }
            }
        }
        photoPath.setText(path);
    }

    private void displayPlacePicker(Intent data){
        Place place = PlacePicker.getPlace(data, this);

        String address = String.valueOf(place.getAddress());
        LatLng lat = place.getLatLng();
        alamat.setText(address);
        latitude.setText(String.valueOf(lat.latitude));
        longtitude.setText(String.valueOf(lat.longitude));


    }

    private String getRealPath(Uri contentUri) {
        String path = null;
        String[] img_data = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, img_data, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
