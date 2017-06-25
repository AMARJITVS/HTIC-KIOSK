package com.htic.amar.kiosk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Bp extends AppCompatActivity {
    //Constructor for central path
    CentralPath cp=new CentralPath();
    public Button bp;
    public static TextView bpreload;
    public static String bpvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);

        bpreload = (Button) findViewById(R.id.bpreload);

        bp = (Button) findViewById(R.id.bpbutton);
        //Video reload button
        bpreload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Bp.this,VideoActivity.class);
                i.putExtra("Video",bpvideo);
                i.putExtra("Activity","BP");
                startActivity(i);
            }
        });


        //button for getting bp data
        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gets bp data
             cp.bpdata();
                //starts video activity
                Intent intent = new Intent(Bp.this, VideoActivity.class);
                intent.putExtra("Video","2");
                intent.putExtra("Activity","TEMP");
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //Always makes screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //assigns video for bp
        bpvideo="1";

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //goes back to spo2 but not needed in the final app
        Intent intent=new Intent(Bp.this,SPO2_Main.class);
        startActivity(intent);
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