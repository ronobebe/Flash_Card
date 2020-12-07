package com.example.flashcard;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission_group.CAMERA;
import static android.Manifest.permission_group.STORAGE;

public class BaseActivity extends AppCompatActivity  {
    public static final int PERMISSION_REQUEST_CODE = 200;

public  static  final String BUNDLE_KEY="BUNDLE_KEY";

/*
public  boolean checkSelfPermission(){

    int writeExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
    int camera = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
    int readExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);


    return writeExternalStorage == PackageManager.PERMISSION_GRANTED &&
            camera == PackageManager.PERMISSION_GRANTED &&
            readExternalStorage == PackageManager.PERMISSION_GRANTED;
}

public  void requestPermission() {

    ActivityCompat.requestPermissions(this, new String[]{STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

}
*/


    public boolean hasPermission(String permission) {
        return  ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void askForMultiplePermissions(){
        String cameraPermissin = Manifest.permission.CAMERA;
        String readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        String writeExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        List<String> permissionList = new ArrayList<>();

        if (!hasPermission(cameraPermissin)){
            permissionList.add(cameraPermissin);
        }
        if (!hasPermission(writeExternalStoragePermission)){
            permissionList.add(writeExternalStoragePermission);
        }
        if (!hasPermission(readExternalStoragePermission)){
            permissionList.add(readExternalStoragePermission);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST_CODE);
        }
    }

}
