package com.example.rgb_detector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CropActivity extends AppCompatActivity {

    private static final String TAG = "CropActivity";

    Button crop_btn;
    Button analyze_btn;
    ImageView target_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        Intent intent = getIntent();
        Uri imageUrl;

        image_data img = (image_data)intent.getSerializableExtra("Image_Target") ;

        crop_btn = findViewById(R.id.crop_btn);
        analyze_btn = findViewById(R.id.analyze_btn);
        target_img = findViewById(R.id.targetImage);

        imageUrl = Uri.parse(img.getImgURL());

        target_img.setImageURI(imageUrl);

        crop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}