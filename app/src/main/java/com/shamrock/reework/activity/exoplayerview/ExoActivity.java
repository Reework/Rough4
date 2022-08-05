package com.shamrock.reework.activity.exoplayerview;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.shamrock.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ExoActivity extends AppCompatActivity  {


    private static final String TAG = "MainActivity";
    private PlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private TextView resolutionTextView;
    YouTubePlayer youTubePlayers;
    YouTubePlayerView youTubePlayerView;
    private String VideoIDStr="";
    TextView txt_title_details,txt_description;
    ArrayList<String> aaa;
    int count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo);

        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Video");
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        tvTitle.setText(getIntent().getStringExtra("title"));
        VideoIDStr=getIntent().getStringExtra("VideoID");
        txt_title_details = findViewById(R.id.txt_title_details);
        txt_description = findViewById(R.id.txt_description);
        if ((getIntent().getStringExtra("title")!=null)){
            txt_title_details.setText(getIntent().getStringExtra("title"));

        }
        if (getIntent().getStringExtra("description")!=null){
            txt_description.setText(getIntent().getStringExtra("description"));

        }

        if (getIntent().getStringArrayListExtra("youtubelist_Name")!=null){
            aaa=getIntent().getStringArrayListExtra("youtubelist_Name");
        }


         youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {


                if (VideoIDStr.isEmpty()){
//                    String videoId = "J-EEotdw5YU";
//                    youTubePlayers=youTubePlayer;
//                    youTubePlayer.loadVideo(videoId, 0);

                }else {
//                    String videoId = "J-EEotdw5YU";
                    youTubePlayers=youTubePlayer;
                    youTubePlayer.loadVideo(VideoIDStr, 0);

                }


            }

            @Override
            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);

                if (state.name().equalsIgnoreCase("ENDED")){

if (aaa!=null){
    if (!aaa.isEmpty()){
        count++;
        if (count<=aaa.size()){
            try {
//                youTubePlayer.loadVideo(aaa.get(count),0);

            }catch (Exception e){
                Toast.makeText(ExoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
                }
            }

            @Override
            public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float second) {
                super.onCurrentSecond(youTubePlayer, second);

                if (second>1){
                    youTubePlayerView.getPlayerUiController().showFullscreenButton(true);
//                    youTubePlayerView.getPlayerUiController().setVideoTitle(getIntent().getStringExtra("title"));


                }
            }
        });

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                youTubePlayers.play();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }


        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (youTubePlayerView!=null){
            youTubePlayerView.release();

        }
    }
}