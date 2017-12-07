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
import android.support.v4.view.animation.FastOutLinearInInterpolator;
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
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DasboardActivity extends AppCompatActivity {
    public static final String KEYPREF     = "LocalData";
    public static final String KEYUSERNAME = "Username";
    int PLACE_PICKER_REQUEST = 1, REQUEST_CAMERA = 2, SELECT_FILE = 0;
    Button btnLocation, btnPhoto, Lapor;
    EditText alamat, latitude, photoPath, longtitude,keterangan;
    WebView attributionText;
    Uri UrlGambar;
    File file;
    SharedPreferences preferences;
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
        Lapor = (Button) findViewById(R.id.btnLapor);
        keterangan = (EditText) findViewById(R.id.editKeterangan);

//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databasePengaduan = FirebaseDatabase.getInstance().getReference("pengaduan");
        preferences = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);
        final String pengguna = preferences.getString(KEYUSERNAME, "");

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


        Lapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databasePengaduan.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        int count = (int) ((dataSnapshot.getChildrenCount())+1);
//                        String countData = String.valueOf(count);
//                        String id = "pengaduan_"+countData;
                        Pengaduan pengaduan = new Pengaduan(alamat.getText().toString(), photoPath.getText().toString(), keterangan.getText().toString(), latitude.getText().toString(), longtitude.getText().toString(), pengguna);
                        String id = databasePengaduan.push().getKey();
                        databasePengaduan.child(id).setValue(pengaduan);
                        Intent in = new Intent(DasboardActivity.this, DaftarPengaduanActivity.class);
                        clearForm();
                        startActivity(in);
                        finish();


//                        if(Integer.parseInt(countData)  == 0){
//                            int no = 1;
//                            String idPengaduan = "pengaduan_"+no;
//                            Pengaduan pengaduan = new Pengaduan(alamat.getText().toString(), photoPath.getText().toString(), keterangan.getText().toString(), latitude.getText().toString(), longtitude.getText().toString(), pengguna);
//                            mDatabase.child(idPengaduan).setValue(pengaduan);
//                            Intent intent = new Intent(DasboardActivity.this, DaftarPengaduanActivity.class);
//                            startActivity(intent);
//                            clearForm();
//                            finish();
//                            Toast.makeText(getApplicationContext(), "Pengaduan berhasil", Toast.LENGTH_SHORT);
//                        }else{
//                            int jmlPengaduan = Integer.parseInt(countData) + 1;
//                            String idPengaduan = "pengaduan_"+jmlPengaduan;
//                            Pengaduan pengaduan = new Pengaduan(alamat.getText().toString(), photoPath.getText().toString(), keterangan.getText().toString(), latitude.getText().toString(), longtitude.getText().toString(), pengguna);
//                            mDatabase.child(idPengaduan).setValue(pengaduan);
//                            Intent intent = new Intent(DasboardActivity.this, DaftarPengaduanActivity.class);
//                            startActivity(intent);
//                            clearForm();
//                            finish();
//                            Toast.makeText(getApplicationContext(), "Pengaduan berhasil", Toast.LENGTH_SHORT);
//                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnLogout:
                Log.i("test","logout");
                Intent i = new Intent(this,LoginActivity.class);
                preferences = getSharedPreferences(KEYPREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
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

    public void clearForm(){
        photoPath.setText("");
        latitude.setText("");
        longtitude.setText("");
        alamat.setText("");
        keterangan.setText("");
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
                file = new File(Environment.getExternalStorageDirectory(), "Foto Jalan Rusak/img_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                if(items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                String outputPath = "/storage/emulated/0/Foto Jalan Rusak/";
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

//    private void copyFile(String inputPath, String inputFile, String outputPath){
//        InputStream in = null;
//        OutputStream out = null;
//        try{
//            File dir = new File(outputPath);
//            if(!dir.exists()){
//                dir.mkdirs();
//            }
//
//            in = new FileInputStream(inputPath + inputFile);
//            out = new FileOutputStream(outputPath + inputFile);
//
//            byte[] buffer = new byte[1024];
//            int read;
//
//            while ((read = in.read(buffer)) != -1){
//                out.write(buffer,0,read);
//            }
//            in.close();
//            in = null;
//
//            out.flush();
//            out = null;
//        }catch (Exception e){
//            Log.i("Tag",e.getMessage());
//        }
//    }
}
