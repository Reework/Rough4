package com.shamrock.reework.activity.exoplayerview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.shamrock.R;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class PatientAwaernessVideoPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PATIENTAWERNESSEXTRA = "patientvideos";
    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;
    private ProgressBar pbIndicatorLoading;
    private ImageView ivFullScreen;
    private ImageView mFullScreenIcon;
    private FrameLayout mFullScreenButton;
    private Dialog mFullScreenDialog;
    private boolean mExoPlayerFullscreen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_awaerness_video_preview);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseVideoPlayer();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseVideoPlayer();
    }
    public void releaseVideoPlayer() {
        if (player != null) {
            player.release();
        }
        player = null;
    }

    private void init() {

        ImageView ivBack = findViewById(R.id.iv_back);
        TextView txtHeader = findViewById(R.id.txt_header);
        txtHeader.setText("Video");
        ivBack.setOnClickListener(this);
        pbIndicatorLoading = findViewById(R.id.pb_indicator_loading);
        simpleExoPlayerView = findViewById(R.id.player_view);
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(new DefaultBandwidthMeter.Builder().build()));

//        extractYoutubeUrl();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "vidapp"), null);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(new String("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4").replaceAll(" ", "%20")));

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                Log.d("exo", "timeLine Changed");
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                pbIndicatorLoading.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.d("exo", "loding changed= " + isLoading);
                if (isLoading) {
                    pbIndicatorLoading.setVisibility(View.VISIBLE);
                } else {
                    pbIndicatorLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d("exo", "state changed");
                pbIndicatorLoading.setVisibility(View.GONE);
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.e("exo", "exoplayer error", error);
                pbIndicatorLoading.setVisibility(View.GONE);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {
                Log.d("exo", "seek processed");
                pbIndicatorLoading.setVisibility(View.VISIBLE);

            }
        });
        player.prepare(mediaSource);

        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(true);
        //end
        initFullscreenDialog();
        initFullscreenButton();
        //   }


    }



    @Override
    public void onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
        super.onBackPressed();
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void initFullscreenButton() {

//        PlaybackControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
//        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
//        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
//        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mExoPlayerFullscreen)
//
//                    openFullscreenDialog();
//                else
//                    closeFullscreenDialog();
//            }
//        });
    }

    private void openFullscreenDialog() {
        ((ViewGroup) simpleExoPlayerView.getParent()).removeView(simpleExoPlayerView);
        mFullScreenDialog.addContentView(simpleExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(PatientAwaernessVideoPreviewActivity.this, R.drawable.back_arrow));

        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {
        ((ViewGroup) simpleExoPlayerView.getParent()).removeView(simpleExoPlayerView);
        ((RelativeLayout) findViewById(R.id.rl_videoview)).addView(simpleExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(PatientAwaernessVideoPreviewActivity.this, R.drawable.back_arrow));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }

    }

    private void extractYoutubeUrl() {
         String GRID_YOUTUBE_ID = "8saOHjoDX94";
         String BASE_URL = "https://www.youtube.com";
         String youtubeLink = BASE_URL + "/watch?v=" + GRID_YOUTUBE_ID;
        @SuppressLint("StaticFieldLeak") YouTubeExtractor mExtractor = new YouTubeExtractor(this) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
                if (sparseArray != null) {
                   String url=sparseArray.get(17).getUrl();
                    Toast.makeText(PatientAwaernessVideoPreviewActivity.this, ""+url, Toast.LENGTH_SHORT).show();
                }
            }
        };
        mExtractor.extract(youtubeLink, true, true);
    }
}
