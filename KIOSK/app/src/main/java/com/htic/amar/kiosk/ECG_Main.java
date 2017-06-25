package com.htic.amar.kiosk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ECG_Main extends AppCompatActivity {


    //Button declaration
    public static Button ecgnext,ecgreload;
    //Edit text declaration
    public static EditText display;

    public static String ecgvideo;

    //Constructor for central path
    CentralPath cp=new CentralPath();

    public static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg__main);


        dialog = new ProgressDialog(ECG_Main.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("RETREIVING DATA...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        //showing dialog as getting data
        dialog.show();
    //Widget declaration
        ecgnext = (Button) findViewById(R.id.ecgnext);
        display = (EditText) findViewById(R.id.display);
        ecgreload = (Button) findViewById(R.id.ecgreload);

        //video reload button
        ecgreload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(ECG_Main.this,VideoActivity.class);
                i.putExtra("Video",ecgvideo);
                i.putExtra("Activity","ECG");
                startActivity(i);

            }
        });

        //for starting graph activity
        ecgnext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                cp.ecgnext();
                if(CENTRAL.graphdata!="") {
                    Intent i = new Intent(ECG_Main.this, Ecg.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ECG_Main.this, Temp.class);
        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //for getting data from MC
        cp.ecgdata();
        ecgnext.setEnabled(false);
        //Always screen on condition
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //sets the video number
        ecgvideo="3";
    }

    @Override
    protected void onDestroy() {
        //Clears Always screen on condition
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //Destroys accessory
        super.onDestroy();
        System.exit(0);
    }
}
