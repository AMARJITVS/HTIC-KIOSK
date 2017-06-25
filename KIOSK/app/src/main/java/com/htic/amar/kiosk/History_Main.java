package com.htic.amar.kiosk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
//This activity gets data from the firebase database to plot the graph
public class History_Main extends AppCompatActivity {

    DatabaseReference retref;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button graph,back;
    HistoryMockData mock=new HistoryMockData();
    public static ArrayList<String> bpslist=new ArrayList<>();
    public static ArrayList<String> bpdlist=new ArrayList<>();
    public static ArrayList<String> spo2list=new ArrayList<>();
    public static ArrayList<String> hrlist=new ArrayList<>();
    public static ArrayList<String> wlist=new ArrayList<>();
    public static ArrayList<String> templist=new ArrayList<>();
    public static ArrayList<String> hlist=new ArrayList<>();

    public static ArrayList<String> bpsdate=new ArrayList<>();
    public static ArrayList<String> bpddate=new ArrayList<>();
    public static ArrayList<String> spo2date=new ArrayList<>();
    public static ArrayList<String> hrdate=new ArrayList<>();
    public static ArrayList<String> wdate=new ArrayList<>();
    public static ArrayList<String> tempdate=new ArrayList<>();
    public static ArrayList<String> hdate=new ArrayList<>();
    public static int i;
    public ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__main);
        //defines the progress dialogue
        dialog = new ProgressDialog(History_Main.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading data from server... Please Wait");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        //defines reference to the firebase database
        retref = database.getReference(""+Result.aadhaarstring);

        graph=(Button) findViewById(R.id.historystart);
        back=(Button) findViewById(R.id.historyback);

        //gets back to the previous activity
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                clear();
                Intent intent=new Intent(History_Main.this, Result.class);
                startActivity(intent);
            }
        });

        //starts the graph plotting activity and sends data to graph activity to plot
        graph.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                mock.receiveData(bpslist,bpsdate,bpdlist,bpddate,spo2list,spo2date,wlist,wdate,hrlist,hrdate,templist,tempdate);
                Intent intent=new Intent(History_Main.this, HistoryGraph.class);
                startActivity(intent);
            }
        });

    }


    //starts getting details from the firebase database
    @Override
    protected void onResume() {
        super.onResume();
        graph.setEnabled(false);

        dialog.show();
        new Thread(new Runnable() {

            @Override
            public void run() {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            clear();
                            getBPSDetails();
                            getBPDDetails();
                            getHRDetails();
                            getWDetails();
                            getHDetails();
                            getSPO2Details();
                            getTEMPDetails();

                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
        }).start();


        //adds always screen on state
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }


    //clears all variables
    public void clear()
    {
        bpslist.removeAll(bpslist);
        bpdlist.removeAll(bpdlist);
        hrlist.removeAll(hrlist);
        hlist.removeAll(hlist);
        wlist.removeAll(wlist);
        spo2list.removeAll(spo2list);
        templist.removeAll(templist);

        bpsdate.removeAll(bpsdate);
        bpddate.removeAll(bpddate);
        hrdate.removeAll(hrdate);
        hdate.removeAll(hdate);
        wdate.removeAll(wdate);
        spo2date.removeAll(spo2date);
        tempdate.removeAll(tempdate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clear();
        Intent intent=new Intent(History_Main.this, Result.class);
        startActivity(intent);
    }

    //
    @Override
    protected void onDestroy() {
        //Clears Always screen on condition
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //Destroys accessory
        System.exit(0);
        super.onDestroy();
    }




    //gets BP systolic details
    public void getBPSDetails()
    {

        retref.child("BLOOD_PRESSURE_SYS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    String date=ds.getKey().toString();

                    for(DataSnapshot ds1:ds.getChildren()) {

                        String data =ds1.getValue().toString();

                            bpslist.add(data);
                            bpsdate.add(date);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    //gets BP diastolic details
    public void getBPDDetails()
    {
        retref.child("BLOOD_PRESSURE_DIA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    String date=ds.getKey().toString();

                    for(DataSnapshot ds1:ds.getChildren()) {

                        String data =ds1.getValue().toString();

                            bpdlist.add(data);
                            bpddate.add(date);

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    //gets heart rate details
    public void getHRDetails()
    {
        retref.child("HEART_RATE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    String date=ds.getKey().toString();

                    for(DataSnapshot ds1:ds.getChildren()) {

                        String data =ds1.getValue().toString();

                            hrlist.add(data);
                            hrdate.add(date);

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    //gets weight details
    public void getWDetails()
    {
        retref.child("WEIGHT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    String date=ds.getKey().toString();

                    for(DataSnapshot ds1:ds.getChildren()) {

                        String data =ds1.getValue().toString();

                            wlist.add(data);
                            wdate.add(date);

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    //gets height details but it is not used for plotting
    public void getHDetails()
    {
        retref.child("HEIGHT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    String date=ds.getKey().toString();

                    for(DataSnapshot ds1:ds.getChildren()) {

                        String data =ds1.getValue().toString();
                            hlist.add(data);
                            hdate.add(date);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    //gets SPO2 details
    public void getSPO2Details()
    {

        retref.child("SPO2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    String date=ds.getKey().toString();

                    for(DataSnapshot ds1:ds.getChildren()) {

                        String data =ds1.getValue().toString();

                            spo2list.add(data);
                            spo2date.add(date);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    //gets temperature details
    public void getTEMPDetails()
    {
        retref.child("TEMPERATURE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                    String date=ds.getKey().toString();

                    for(DataSnapshot ds1:ds.getChildren()) {

                        String data =ds1.getValue().toString();

                            templist.add(data);
                            tempdate.add(date);

                    }

                }
                graph.setEnabled(true);
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }




}
