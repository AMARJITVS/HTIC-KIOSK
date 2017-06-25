package com.htic.amar.kiosk;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Result extends AppCompatActivity {

    Button Exit,history;
    TextView aadhaartext,bp,hr,temp,spo2,weight,height;
    public static String aadhaarno="DUMMY",bpsys="",bpdia="",hrvalue="",tempvalue="",spo2value="",weightvalue="",heightvalue="";
    //getting instance for firebase database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //for checking if user is logged in or not
    public static boolean firebasecheck=true;

    DatabaseReference ref;

    String formattedDate;
    String formattedTime;
    public static String macAddress;
    public Calendar c = Calendar.getInstance();
    public static String aadhaarstring,bpsstring,bpdstring,hrstring,tempstring,weightstring,heightstring,spo2string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Declaring Wifi manager to get the mac address
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();

        aadhaartext=(TextView) findViewById(R.id.aadharresult);
        bp=(TextView) findViewById(R.id.bpresult);
        hr=(TextView) findViewById(R.id.hrresult);
        temp=(TextView) findViewById(R.id.tempresult);
        spo2=(TextView) findViewById(R.id.spo2result);
        weight=(TextView) findViewById(R.id.wresult);
        height=(TextView) findViewById(R.id.hresult);
        Exit=(Button) findViewById(R.id.exit);
        history=(Button) findViewById(R.id.history);
        //defining the database reference
        ref=database.getReference();
        //getting the mac address
        macAddress = wInfo.getMacAddress();

        //getting the date
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        formattedDate = df.format(c.getTime());

        //getting the time
        SimpleDateFormat ds = new SimpleDateFormat("HH:mm:ss");
        formattedTime=ds.format(c.getTime());

        //sets the aadhaar  number
        aadhaartext.setText(aadhaarno);

        //sets the BP
        if(bpsys!="" && bpdia!="")
        bp.setText(""+bpsys+"/"+bpdia+" "+" mm/Hg");
        else
            bp.setText("NULL");
        //sets the HR
        if(hrvalue!="")
        hr.setText(hrvalue);
        else
        hr.setText("NULL");
        //sets the Temperature
        if(tempvalue!="")
        temp.setText(""+tempvalue+" \u00b0C");
        else
        temp.setText("NULL");
        //sets the SPO2
        if(spo2value!="")
        spo2.setText(spo2value);
        else
        spo2.setText("NULL");
        //sets the Weight
        if(weightvalue!="")
        weight.setText(""+weightvalue+" Kg");
        else
        weight.setText("NULL");
        //sets the Height
        if(heightvalue!="")
        height.setText(""+heightvalue+" cm");
        else
        height.setText("NULL");

        //gets back to the login activity after clearing the variables
        Exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Result.this,Login.class);
                startActivity(intent);
                clear();
            }
        });

        //starts the history_main activity
        history.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Result.this,History_Main.class);
                startActivity(intent);
            }
        });

        //checks if user is logged in and stores the data in the firebase
        if(firebasecheck)
        addUser();

    }

    //clears all the variables and sets the default values in the text views
    public void clear()
    {
        aadhaarno="";
        bpdia="";
        bpsys="";
        hrvalue="";
        tempvalue="";
        spo2value="";
        weightvalue="";
        heightvalue="";
        aadhaartext.setText(aadhaarno);
        bp.setText(bpsys);
        hr.setText(hrvalue);
        temp.setText(tempvalue);
        spo2.setText(spo2value);
        weight.setText(weightvalue);
        height.setText(heightvalue);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Result.this,heightandweight.class);
        startActivity(intent);
    }
    //adds user in the firebase
    public void addUser()
    {
        //resets the firebase user check
        firebasecheck=false;

        aadhaarstring=aadhaarno;
        bpsstring=bpsys;
        bpdstring=bpdia;
        hrstring=hrvalue;
        weightstring=weightvalue;
        heightstring=heightvalue;
        tempstring=tempvalue;
        spo2string=spo2value;

        //defines the details class constructor and gets the data from the details class and stores the data in the firebase database
            Details detail=new Details(bpsstring,bpdstring,heightstring,weightstring,hrstring,spo2string,tempstring);
        if(detail.getBps()!="")
            ref.child(aadhaarstring).child("BLOOD_PRESSURE_SYS").child(formattedDate).child(formattedTime).setValue(detail.getBps());
        if(detail.getBpd()!="")
            ref.child(aadhaarstring).child("BLOOD_PRESSURE_DIA").child(formattedDate).child(formattedTime).setValue(detail.getBpd());
            ref.child(aadhaarstring).child("KIOSK_ADDRESS").child(formattedDate).child(formattedTime).setValue(macAddress);
        if(detail.getSpo2()!="")
            ref.child(aadhaarstring).child("SPO2").child(formattedDate).child(formattedTime).setValue(detail.getSpo2());
        if(detail.getHeart_rate()!="")
            ref.child(aadhaarstring).child("HEART_RATE").child(formattedDate).child(formattedTime).setValue(detail.getHeart_rate());
        if(detail.getHeight()!="")
            ref.child(aadhaarstring).child("HEIGHT").child(formattedDate).child(formattedTime).setValue(detail.getHeight());
        if(detail.getWeight()!="")
            ref.child(aadhaarstring).child("WEIGHT").child(formattedDate).child(formattedTime).setValue(detail.getWeight());
        if(detail.getTemp()!="")
            ref.child(aadhaarstring).child("TEMPERATURE").child(formattedDate).child(formattedTime).setValue(detail.getTemp());

            Toast.makeText(getApplicationContext(),"USER ADDED",Toast.LENGTH_SHORT).show();

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

    }

}

