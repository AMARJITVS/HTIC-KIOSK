package com.htic.amar.kiosk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

//This is the startup activity
public class MainActivity extends AppCompatActivity {

    Button Loginbutton;
    //defines the Central path object
    CentralPath cp=new CentralPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Loginbutton = (Button) findViewById(R.id.Loginbutton);

        Loginbutton.setOnClickListener(new View.OnClickListener() {

            // @Override
            public void onClick(View v) {
                //stsrts the login activity
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                //starts the central path class to run the read and write handler to configure communication between the app and the microcontroller
                cp.con(getApplicationContext());
            }
        });


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













