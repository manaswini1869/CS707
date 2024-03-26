package com.example.a707;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button welcomeButton = findViewById(R.id.welcomeButton);

        Button myVideos = findViewById(R.id.video_list);
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueToSecondPage();
            }
        });

        myVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToVideosPages();
            }
        });
    }

    public void continueToSecondPage() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void goToVideosPages() {
        Intent intent = new Intent(this, VideoList.class);
        startActivity(intent);
    }
}