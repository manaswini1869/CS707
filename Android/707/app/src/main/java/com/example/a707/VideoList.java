package com.example.a707;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoList extends AppCompatActivity {
    RecyclerView recyclerView;
    View view;

    VideoListAdapter videoListAdapter;
    private static final int REQUEST_CODE_PERMISSION = 123;
    static ArrayList<VideoFiles> videoFiles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.video_list);
        Log.i("INFO", "video files"+videoFiles);
        setContentView(R.layout.video_list);
        recyclerView = findViewById(R.id.filesRV);
        if (videoFiles != null && videoFiles.size() > 0){
            videoListAdapter = new VideoListAdapter(this, videoFiles);
            recyclerView.setAdapter(videoListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        }
        permission();
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(VideoList.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
        else{
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            videoFiles = getAllVideos(this);
            if (videoFiles != null && videoFiles.size() > 0){
                videoListAdapter = new VideoListAdapter(this, videoFiles);
                recyclerView.setAdapter(videoListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                videoFiles = getAllVideos(this);
            }
            else{
                ActivityCompat.requestPermissions(VideoList.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }
        }
    }

    public ArrayList<VideoFiles> getAllVideos(Context context){
        ArrayList<VideoFiles> tempVideoFiles = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.RELATIVE_PATH,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String title = cursor.getString(3);
                String size = cursor.getString(2);
                String duration = cursor.getString(4);
                String date = cursor.getString(5);
                VideoFiles videoFiles = new VideoFiles(id, path, title,size, duration, date);
                Log.i("INFO", "path"+path);
                tempVideoFiles.add(videoFiles);
                Log.i("INFO","video files"+videoFiles.getTitle() + videoFiles.getId() + videoFiles.getPath() +
                        videoFiles.getSize() + videoFiles.getDuration() + videoFiles.getDate());
            }
            cursor.close();
        }
        return tempVideoFiles;
    }
}