package com.htic.amar.kiosk;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

public class Login extends AppCompatActivity {

    Button submit;
    EditText aadhaartext;
    VideoView kioskvideo;
    public static String aadhaarno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //confirms that a user is entered
        Result.firebasecheck=true;
        submit=(Button) findViewById(R.id.submit);
        aadhaartext=(EditText) findViewById(R.id.aadhaartext);
        kioskvideo=(VideoView) findViewById(R.id.kioskvideo);

        //starts the video
        startVideo();

        //stores the aadhaar number and starts the SPO2 main video activity
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                aadhaarno=aadhaartext.getText().toString().trim();
                if(!TextUtils.isEmpty(aadhaarno)&&aadhaarno.length()==12) {
                    Result.aadhaarno=aadhaarno;
                    Intent intent = new Intent(Login.this, VideoActivity.class);
                    intent.putExtra("Video","0");
                    intent.putExtra("Activity","SPO2");
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"WELCOME!!!",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Aadhaar ID",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //onCompletion of the video starts the video again and again
        kioskvideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                kioskvideo.start();

            }
        });

    }
    //starts the video
    void startVideo() {
        getWindow().setFormat(PixelFormat.UNKNOWN);
        //defines the video path
        String uriPath = "android.resource://" + getPackageName() + "/"+R.raw.yuvrajsingh;
        Uri uri = Uri.parse(uriPath);
        kioskvideo.setVideoURI(uri);
        kioskvideo.requestFocus();
        kioskvideo.start();
    }



    @Override
    protected void onResume()
    {
        super.onResume();
        //Always makes screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
    @Override
    protected void onDestroy() {
        //Clears Always screen on condition
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //Destroys accessory
        System.exit(0);
        super.onDestroy();
    }
}
