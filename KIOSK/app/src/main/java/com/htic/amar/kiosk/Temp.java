package com.htic.amar.kiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Temp extends AppCompatActivity {

    Button temp,tempreload;

    //defines the central path constructor
    CentralPath cp = new CentralPath();
    public static String tempvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);



        tempreload = (Button) findViewById(R.id.tempreload);

        temp = (Button) findViewById(R.id.tempbutton);

        //reloads the video
        tempreload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Temp.this,VideoActivity.class);
                i.putExtra("Video",tempvideo);
                i.putExtra("Activity","TEMP");
                startActivity(i);
            }
        });

        //starts the ECG video activity after getting temperature data from the MC
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.tempdata();
                Intent intent = new Intent(Temp.this, VideoActivity.class);
                intent.putExtra("Video","3");
                intent.putExtra("Activity","ECG");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Temp.this, Bp.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Clears Always screen on condition
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        System.exit(0);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //Always makes screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //defines the video number
        tempvideo="2";

    }
}
