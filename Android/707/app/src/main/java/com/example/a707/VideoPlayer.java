package com.example.a707;

import static com.example.a707.VideoList.videoFiles;

import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayer extends AppCompatActivity {

    StyledPlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Initialize PlayerView
        playerView = findViewById(R.id.idExoPlayerVIew);

        // Get video position from intent
        position = getIntent().getIntExtra("position", -1);
        String path = videoFiles.get(position).getPath();

        if (path != null) {
            // Create a Uri from the video path
            Uri uri = Uri.parse(path);

            // Create a SimpleExoPlayer instance
            simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();

            // Create a DataSource Factory
            DataSource.Factory factory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "My App Name"));

            // Create a MediaSource
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory)
                    .createMediaSource(MediaItem.fromUri(uri));

            // Prepare the player with the MediaSource
            simpleExoPlayer.setMediaSource(mediaSource);
            simpleExoPlayer.prepare();
            simpleExoPlayer.setPlayWhenReady(true);

            // Set the player to PlayerView
            playerView.setPlayer(simpleExoPlayer);
            playerView.setKeepScreenOn(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the player when the activity is destroyed
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
    }
}
