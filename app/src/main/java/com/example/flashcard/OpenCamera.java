package com.example.flashcard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class OpenCamera {


    Context context;

    String packageName;

    public OpenCamera(Context context, String packageName) {
        this.context = context;
        this.packageName = packageName;
    }

    public static final String NEW_IMG_LOCATION = "Android/data/" + BuildConfig.APPLICATION_ID + "/Image";


    public boolean isCameraAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private Intent createCameraIntent() {

        Uri fileUri;

        if (isCameraAvailable()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (android.os.Build.VERSION.SDK_INT >= 24) {

            File    photofile = getOutputMediaFile(MEDIA_TYPE_IMAGE);


                fileUri = FileProvider.getUriForFile(context.getApplicationContext(),
                        packageName + ".fileprovider", photofile);

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                Log.e(TAG, "onClick: " + fileUri.toString());


            }
            else {

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            }

            return  intent;

        } else {
           return  null;
        }

    }


    public  Intent openCameraOnly(Context ctxt) {

        Intent intent = createCameraIntent();

        if (intent != null) {
            if (intent.resolveActivity(ctxt.getPackageManager()) != null) {
                return intent;
            } else {
                return null;
            }
        }


            return null;

    }


    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }



    public File getOutputMediaFile(int type) {

        Log.d(TAG, "openCameraOnly : 2");
        File sdcard = FileAccessUtils.getInstance().getExternalStorageDirectory(/*Environment.DIRECTORY_DCIM*/);
        File mediaStorageDir = new File(sdcard, NEW_IMG_LOCATION);
        // Create the storage directory if it does not exist
        Log.d(TAG, "openCameraOnly : 3");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("", "Oops! Failed create "
                    + "" + " directory");
            Log.d(TAG, "openCameraOnly : 4");
            return null;
        }
        Log.d(TAG, "openCameraOnly : 5");
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        Log.d(TAG, "openCameraOnly : 6");
        String imageFileName = "IMG_" + timeStamp + ".png";

        if (type == MEDIA_TYPE_IMAGE) {
            Log.d(TAG, "openCameraOnly : 7");
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + imageFileName);
            Log.d(TAG, "openCameraOnly : 8");
        } else {
            Log.d(TAG, "openCameraOnly : 9");
            return null;
        }

        Log.e(TAG, "create file: " + mediaFile.toString());
        Log.d(TAG, "openCameraOnly : 10");
        return mediaFile;
    }


}
