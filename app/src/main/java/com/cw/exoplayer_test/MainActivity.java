package com.cw.exoplayer_test;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

public class MainActivity extends AppCompatActivity {

    StyledPlayerView exoPlayerView;
    ExoPlayer exoPlayer;
    String videoURL="https://drive.google.com/file/d/1CP6W_yBo4e_eEUXDMJSCAdE_wIi4o_14/view?usp=share_link";

    // transformed path of Google drive path
//    String videoURL="https://drive.google.com/uc?export=download&id=1CP6W_yBo4e_eEUXDMJSCAdE_wIi4o_14";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exoPlayerView=findViewById(R.id.idExoPlayerVIew);
        try {
            exoPlayer= new ExoPlayer.Builder(getApplicationContext()).build();
            Uri videoUri= Uri.parse(getTransformedPath(videoURL));
            DefaultHttpDataSource.Factory dataSourceFactory=new DefaultHttpDataSource.Factory();
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory,extractorsFactory)
                                                                .createMediaSource(MediaItem.fromUri(videoUri));

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.setMediaSource(mediaSource);
            exoPlayer.prepare();
            exoPlayer.setPlayWhenReady(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    String getTransformedPath(String original_path){
        String transformedPath = original_path;
        if(original_path.contains("drive.google")) {
            transformedPath = "https://drive.google.com/uc?export=download&id=" + getGDriveFileId(original_path);
        }
        return transformedPath;
    }

    // get Google drive file ID
    // original:
    //      https://drive.google.com/file/d/1rMrKggOn3KcnuBRlhPjR-WsH0mWnAM-o/view?usp=drivesdk
    // download path:
    //      https://drive.google.com/uc?export=download&id=1rMrKggOn3KcnuBRlhPjR-WsH0mWnAM-o
    public static String getGDriveFileId(String originalUri){
        // remove view?usp=drivesdk
        String last = originalUri.substring(originalUri.lastIndexOf('/')+1);
        originalUri = originalUri.replace(last,"");
        // remove /
        originalUri = originalUri.substring(0,originalUri.length()-1);
        // get ID
        String id = originalUri.substring(originalUri.lastIndexOf('/')+1);
        return id;
    }
}