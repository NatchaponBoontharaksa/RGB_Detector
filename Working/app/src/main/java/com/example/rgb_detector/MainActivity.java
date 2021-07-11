package com.example.rgb_detector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
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

    static Context myContext;

    // Create image folder for RGB_Detector application
    static final String appDirectoryName = "RGB_Detector";

    private static final String TAG = "MainActivity";

    private static final int CAMERA_REQ_CODE = 42;
    private String pictureFilePath;

    private ImageView imgview;

    ArrayList<image_data> imageList = new ArrayList<>();

    image_data[] tmp_image_data;
    private static int image_name = 1;

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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getPackageManager()) != null){
//            startActivityForResult(cameraIntent, CAMERA_REQ_CODE);
            File pictureFile = null;
            try {
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAMERA_REQ_CODE);
            }
        }

    }

    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "RGB_Detector_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }

    private void addToGallery() {

        FileOutputStream outputStream = null;
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File dir = new File(file.getAbsolutePath() + appDirectoryName);
        if (!dir.exists()) {
            dir.mkdirs();
        }


//        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Log.d(TAG, pictureFilePath);
//        File f = new File(pictureFilePath);
//        Uri picUri = Uri.fromFile(f);
//        galleryIntent.setData(picUri);
//        this.sendBroadcast(galleryIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQ_CODE && resultCode == Activity.RESULT_OK){
            File imgFile = new File(pictureFilePath);
            addToGallery();
            if (imgFile.exists())
            {
                imgview.setImageURI(Uri.fromFile(imgFile));
            }
//            Bundle extras = data.getExtras();
//            Bitmap imgBitmap = (Bitmap) extras.get("data");
//            image_data tmp = new image_data(, image_name);
//            imgview.setImageBitmap(imgBitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}