package com.htic.amar.kiosk;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    //defines the video names
    int[] id = {R.raw.raina,R.raw.lionelmessi,R.raw.kabali,R.raw.nysm,R.raw.ronaldinho};
    private VideoView videoView;
    Button reload,next;
    public static String videono,activity;

    //starts the video
    void startVideo(int i) {
        getWindow().setFormat(PixelFormat.UNKNOWN);
        //defines the video path
        String uriPath = "android.resource://" + getPackageName() + "/"+id[i];
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //gets the video number
        Intent i=getIntent();
        videono=i.getStringExtra("Video");
        activity=i.getStringExtra("Activity");

        //stores the video number received
        int videoplay=Integer.parseInt(videono);

        reload=(Button) findViewById(R.id.reload);
        next=(Button) findViewById(R.id.next);
        videoView = (VideoView) findViewById(R.id.videoview);

        //using the video number the video is started
        switch (videoplay) {
            case 0:
                startVideo(videoplay);
                break;
            case 1:
                startVideo(videoplay);

                break;
            case 2:
                startVideo(videoplay);
                break;
            case 3:
                startVideo(videoplay);
                break;
            case 4:
                startVideo(videoplay);
                break;
            case 5:
                startVideo(videoplay);
                break;
        }

        //reloads the video
        reload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                videoView.seekTo(0);

            }
        });
        //using the activity name received using the intent the activity is called
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(activity.equals("SPO2")) {
                    Intent i = new Intent(VideoActivity.this, SPO2_Main.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

                if(activity.equals("BP")) {
                    Intent i = new Intent(VideoActivity.this, Bp.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

                if(activity.equals("TEMP")) {
                    Intent i = new Intent(VideoActivity.this, Temp.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

                if(activity.equals("ECG")) {
                    Intent i = new Intent(VideoActivity.this, ECG_Main.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

                if(activity.equals("HW")) {
                    Intent i = new Intent(VideoActivity.this, heightandweight.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

            }
        });

        //on completion of the video the respective activity is started
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(activity.equals("SPO2")) {
                    Intent i = new Intent(VideoActivity.this, SPO2_Main.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

                if(activity.equals("BP")) {
                    Intent i = new Intent(VideoActivity.this, Bp.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

                if(activity.equals("TEMP")) {
                    Intent i = new Intent(VideoActivity.this, Temp.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

                if(activity.equals("ECG")) {
                    Intent i = new Intent(VideoActivity.this, ECG_Main.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

                if(activity.equals("HW")) {
                    Intent i = new Intent(VideoActivity.this, heightandweight.class);
                    i.putExtra("Video", videono);
                    startActivity(i);
                }

            }
        });

    }

}
