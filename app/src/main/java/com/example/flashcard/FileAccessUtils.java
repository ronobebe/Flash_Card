package com.example.flashcard;


import android.os.Environment;

import java.io.File;

public class FileAccessUtils {
    private static FileAccessUtils fileAccessUtil;

    public static FileAccessUtils getInstance() {
        if (fileAccessUtil == null) {
            fileAccessUtil = new FileAccessUtils();
        }
        return fileAccessUtil;
    }

    public File getExternalStoragePublicDirectory(String type) {
        return Environment.getExternalStoragePublicDirectory(type);
    }

    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }
}
