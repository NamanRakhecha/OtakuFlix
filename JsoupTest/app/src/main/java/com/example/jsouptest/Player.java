package com.example.jsouptest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URL;

public class Player extends AppCompatActivity {
    SimpleExoPlayer simpleExoPlayer;
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView fullscreen;
    boolean flag = false;
    Uri videoURl;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();
        Intent intent = getIntent();
        String vidUrl = intent.getStringExtra(Adapter.VideoLink);
        playerView = (PlayerView) findViewById(R.id.player);
        videoURl = Uri.parse(vidUrl);



    }

    private void initializePlayer() {
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(videoURl);
        simpleExoPlayer.setMediaItem(mediaItem);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
        simpleExoPlayer.seekTo(currentWindow, playbackPosition);
        simpleExoPlayer.prepare();
    }
    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Util.SDK_INT>=24){
            initializePlayer();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>=24){

            releasePlayer();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
        if((Util.SDK_INT<24 || simpleExoPlayer == null)){
            initializePlayer();

    }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Util.SDK_INT<24){
            releasePlayer();
        }

    }


}