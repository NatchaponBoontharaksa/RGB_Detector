package com.example.rgb_detector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int[] data = {1, 2, 3, 4};

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = findViewById(R.id.listView);
        Button camera_btn = findViewById(R.id.camera_btn);
        Button gallery_btn = findViewById(R.id.gallery_btn);

    }
}