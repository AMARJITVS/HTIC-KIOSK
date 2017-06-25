package com.htic.amar.kiosk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SPO2_Main extends AppCompatActivity {

    //Button declaration
    public static Button spo2next,spo2back,spo2jump,spo2reload;
    //Edit text declaration
    public static EditText display;

    public static String spo2video;

    public static ProgressDialog dialog;

    //defines the central path class constructor
    CentralPath cp=new CentralPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spo2__main);
        //defines the progress dialogue
        dialog = new ProgressDialog(SPO2_Main.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("RETREIVING DATA...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        //shows the dialogue
        dialog.show();
        //Widget declaration
        spo2next = (Button) findViewById(R.id.spo2next);
        spo2reload = (Button) findViewById(R.id.spo2reload);
        display = (EditText) findViewById(R.id.display);

        //starts the SPO2 graph plotting activity
        spo2next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //sends data to graph activity to plot
                cp.spo2next();
                if(CENTRAL.graphdata!="") {
                    Intent i = new Intent(SPO2_Main.this, Spo2.class);
                    startActivity(i);
                }
            }
        });

        //reloads the video
        spo2reload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SPO2_Main.this,VideoActivity.class);
                i.putExtra("Video",spo2video);
                i.putExtra("Activity","SPO2");
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SPO2_Main.this, Login.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //gets data from the MC
        cp.spo2data();
        spo2next.setEnabled(false);
        //Always screen on condition
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //defines the video number
        spo2video="0";
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