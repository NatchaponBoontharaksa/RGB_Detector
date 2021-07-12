package com.example.rgb_detector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    int[] data = {1, 2, 3, 4};

    String cameraPermission[];
    String storagePermission[];

    // Create image folder for RGB_Detector application
    static final String appDirectoryName = "RGB_Detector";
    String pictureFile;

    private static final String TAG = "MainActivity";

    private static final int CAMERA_REQ_CODE = 42;
    private String pictureFilePath;

    private ImageView imgview;

    Uri image_uri;
    ListView mListView;

    ArrayList<image_data> imageList = new ArrayList<>();

    image_data tmp_image_data;
    private int image_cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        setContentView(R.layout.activity_main);
//        ListView mListView = findViewById(R.id.listView);
        Button camera_btn = findViewById(R.id.camera_btn);
        Button gallery_btn = findViewById(R.id.gallery_btn);
        imgview = findViewById(R.id.imageShow);
        mListView = findViewById(R.id.listView);

//        checkDir(myContext);

        if (!checkCameraPermission()) {
            requestCameraPermission();
        }else {

        }

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openCamera();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    private void checkDir(Context context) {
//        File file = new File(context.getFilesDir(), appDirectoryName);
//
//        if (!file.mkdirs()){
//
//        } else {
//            Log.d(TAG, "Cannot create image fodler for RGB_Detector");
//        }
//    }

    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQ_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void openCamera() throws IOException {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, CAMERA_REQ_CODE);

    }

    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        pictureFile = "RGB_Detector_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQ_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "uri: " + image_uri);
            tmp_image_data = new image_data(image_uri.toString(), (image_cnt+1) + "");
            imageList.add(tmp_image_data);
            imageListAdapter adapter = new imageListAdapter(this, R.layout.img_list, imageList);
            mListView.setAdapter(adapter);
            image_cnt++;

//            imgview.setImageURI(image_uri);
//            Bundle extras = data.getExtras();
//            Bitmap imgBitmap = (Bitmap) extras.get("data");
//            image_data tmp = new image_data(, image_name);
//            imgview.setImageBitmap(imgBitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}