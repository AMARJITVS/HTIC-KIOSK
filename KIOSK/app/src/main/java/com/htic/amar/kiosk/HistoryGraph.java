package com.htic.amar.kiosk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;

public class HistoryGraph extends AppCompatActivity {


    private LineGraphSeries<DataPoint> BPSseries;
    private LineGraphSeries<DataPoint> BPDseries;
    private LineGraphSeries<DataPoint> Wseries;
    private LineGraphSeries<DataPoint> TEMPseries;
    private LineGraphSeries<DataPoint> SPO2series;
    private LineGraphSeries<DataPoint> HRseries;

    History_Main hm=new History_Main();
    Result res=new Result();
    Button back,exit;

    GraphView BPgraph;
    GraphView Wgraph;
    GraphView TEMPgraph;
    GraphView HRgraph;
    GraphView SPO2graph;

    private int lastBPSX =1;
    private int lastBPDX =1;
    private int lastSPO2X =1;
    private int lastHRX =1;
    private int lastWX =1;
    private int lastTEMPX =1;

    HistoryMockData mock=new HistoryMockData();
    private boolean threadb=false;
    Paint paint;

    private static String bpsdate[];
    private static String bpddate[];
    private static String hrdate[];
    private static String spo2date[];
    private static String wdate[];
    private static String tempdate[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_graph);

        getWindow().setSoftInputMode(3);

        back=(Button) findViewById(R.id.historyback);
        exit=(Button) findViewById(R.id.exit);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(threadb) {
                    //Interrupting thread to stop
                    Thread.currentThread().interrupt();
                    ArrayList<String> a=new ArrayList<>();
                    mock.receiveData(a,a,a,a,a,a,a,a,a,a,a,a);

                }
                hm.clear();
                mock.clear();
                Intent intent=new Intent(HistoryGraph.this,Result.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryGraph.this,Login.class);
                startActivity(intent);
            }
        });

        //Graph view
        BPgraph = (GraphView) findViewById(R.id.BPgraph);
        Wgraph = (GraphView) findViewById(R.id.Wgraph);
        TEMPgraph = (GraphView) findViewById(R.id.TEMPgraph);
        HRgraph = (GraphView) findViewById(R.id.HRgraph);
        SPO2graph = (GraphView) findViewById(R.id.SPO2graph);
        // data
        BPSseries = new LineGraphSeries<>();
        // styling series
        BPSseries.setDrawDataPoints(true);
        BPSseries.setDataPointsRadius(5);
        // custom paint to make a dotted line
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5f);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        BPSseries.setCustomPaint(paint);
        BPSseries.setTitle("BP_SYS DATA");
        //Add series to graph
        BPgraph.addSeries(BPSseries);

        BPDseries = new LineGraphSeries<>();
        // styling series
        BPDseries.setDrawDataPoints(true);
        BPDseries.setDataPointsRadius(5);
        // custom paint to make a dotted line
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5f);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        BPDseries.setCustomPaint(paint);
        BPDseries.setTitle("BP_DIA DATA");
        //Add series to graph
        BPgraph.addSeries(BPDseries);

        //Graph view styling
        BPgraph.setTitle("BP READINGS");
        BPgraph.setTitleTextSize(40f);
        BPgraph.setTitleColor(Color.BLUE);
        BPgraph.getGridLabelRenderer().setLabelVerticalWidth(30);
        BPgraph.getGridLabelRenderer().setHorizontalAxisTitle("NUMBER OF DATA");
        BPgraph.getGridLabelRenderer().setVerticalAxisTitle("BP DATA");
        BPgraph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.RED);
        BPgraph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        BPgraph.getGridLabelRenderer().setGridColor(Color.BLACK);
        BPgraph.getGridLabelRenderer().setHighlightZeroLines(true);
        // activate horizontal zooming and scrolling
        BPgraph.getViewport().setScalable(true);
        // activate horizontal scrolling
        BPgraph.getViewport().setScrollable(true);
        // activate vertical scrolling
        BPgraph.getViewport().setScrollableY(true);
        //Max and min of x axis
        BPgraph.getViewport().setXAxisBoundsManual(true);
        BPgraph.getViewport().setMinX(0);
        BPgraph.getViewport().setMaxX(10);



        //On touch listener ---- displays the touched point value
        BPSseries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "DATA POINT: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });


        //On touch listener ---- displays the touched point value
        BPDseries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "DATA POINT: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });


        SPO2series = new LineGraphSeries<>();
        // styling series
        SPO2series.setDrawDataPoints(true);
        SPO2series.setDataPointsRadius(5);
        // custom paint to make a dotted line
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5f);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        SPO2series.setCustomPaint(paint);
        SPO2series.setTitle("SPO2 DATA");
        //Add series to graph
        SPO2graph.addSeries(SPO2series);

        //Graph view styling
        SPO2graph.setTitle("SPO2 READINGS");
        SPO2graph.setTitleTextSize(40f);
        SPO2graph.setTitleColor(Color.BLUE);
        SPO2graph.getGridLabelRenderer().setLabelVerticalWidth(30);
        SPO2graph.getGridLabelRenderer().setHorizontalAxisTitle("DATE");
        SPO2graph.getGridLabelRenderer().setVerticalAxisTitle("SPO2 DATA");
        SPO2graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.RED);
        SPO2graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        SPO2graph.getGridLabelRenderer().setGridColor(Color.BLACK);
        SPO2graph.getGridLabelRenderer().setHighlightZeroLines(true);
        // activate horizontal zooming and scrolling
        SPO2graph.getViewport().setScalable(true);
        // activate horizontal scrolling
        SPO2graph.getViewport().setScrollable(true);
        // activate vertical scrolling
        SPO2graph.getViewport().setScrollableY(true);
        //Max and min of x axis
        SPO2graph.getViewport().setXAxisBoundsManual(true);
        SPO2graph.getViewport().setMinX(0);
        SPO2graph.getViewport().setMaxX(10);

        //On touch listener ---- displays the touched point value
        SPO2series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "DATA POINT: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        Wseries = new LineGraphSeries<>();
        // styling series
        Wseries.setDrawDataPoints(true);
        Wseries.setDataPointsRadius(5);
        // custom paint to make a dotted line
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5f);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        Wseries.setCustomPaint(paint);
        Wseries.setTitle("WEIGHT DATA");
        //Add series to graph
        Wgraph.addSeries(Wseries);

        //Graph view styling
        Wgraph.setTitle("WEIGHT READINGS");
        Wgraph.setTitleTextSize(40f);
        Wgraph.setTitleColor(Color.BLUE);
        Wgraph.getGridLabelRenderer().setLabelVerticalWidth(30);
        Wgraph.getGridLabelRenderer().setHorizontalAxisTitle("DATE");
        Wgraph.getGridLabelRenderer().setVerticalAxisTitle("WEIGHT DATA");
        Wgraph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.RED);
        Wgraph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        Wgraph.getGridLabelRenderer().setGridColor(Color.BLACK);
        Wgraph.getGridLabelRenderer().setHighlightZeroLines(true);
        // activate horizontal zooming and scrolling
        Wgraph.getViewport().setScalable(true);
        // activate horizontal scrolling
        Wgraph.getViewport().setScrollable(true);
        // activate vertical scrolling
        Wgraph.getViewport().setScrollableY(true);
        //Max and min of x axis
        Wgraph.getViewport().setXAxisBoundsManual(true);
        Wgraph.getViewport().setMinX(0);
        Wgraph.getViewport().setMaxX(10);

        //On touch listener ---- displays the touched point value
        Wseries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "DATA POINT: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        HRseries = new LineGraphSeries<>();
        // styling series
        HRseries.setDrawDataPoints(true);
        HRseries.setDataPointsRadius(5);
        // custom paint to make a dotted line
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5f);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        HRseries.setCustomPaint(paint);
        HRseries.setTitle("HEIGHT DATA");
        //Add series to graph
        HRgraph.addSeries(HRseries);

        //Graph view styling
        HRgraph.setTitle("HEART RATE READINGS");
        HRgraph.setTitleTextSize(40f);
        HRgraph.setTitleColor(Color.BLUE);
        HRgraph.getGridLabelRenderer().setLabelVerticalWidth(30);
        HRgraph.getGridLabelRenderer().setHorizontalAxisTitle("DATE");
        HRgraph.getGridLabelRenderer().setVerticalAxisTitle("HEART RATE DATA");
        HRgraph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.RED);
        HRgraph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        HRgraph.getGridLabelRenderer().setGridColor(Color.BLACK);
        HRgraph.getGridLabelRenderer().setHighlightZeroLines(true);
        // activate horizontal zooming and scrolling
        HRgraph.getViewport().setScalable(true);
        // activate horizontal scrolling
        HRgraph.getViewport().setScrollable(true);
        // activate vertical scrolling
        HRgraph.getViewport().setScrollableY(true);
        //Max and min of x axis
        HRgraph.getViewport().setXAxisBoundsManual(true);
        HRgraph.getViewport().setMinX(0);
        HRgraph.getViewport().setMaxX(10);

        //On touch listener ---- displays the touched point value
        HRseries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "DATA POINT: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        TEMPseries = new LineGraphSeries<>();
        // styling series
        TEMPseries.setDrawDataPoints(true);
        TEMPseries.setDataPointsRadius(5);
        // custom paint to make a dotted line
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5f);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        TEMPseries.setCustomPaint(paint);
        TEMPseries.setTitle("TEMPERATURE DATA");
        //Add series to graph
        TEMPgraph.addSeries(TEMPseries);

        //Graph view styling
        TEMPgraph.setTitle("TEMPERATURE READINGS");
        TEMPgraph.setTitleTextSize(40f);
        TEMPgraph.setTitleColor(Color.BLUE);
        TEMPgraph.getGridLabelRenderer().setLabelVerticalWidth(30);
        TEMPgraph.getGridLabelRenderer().setHorizontalAxisTitle("DATE");
        TEMPgraph.getGridLabelRenderer().setVerticalAxisTitle("TEMPERATURE DATA");
        TEMPgraph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.RED);
        TEMPgraph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        TEMPgraph.getGridLabelRenderer().setGridColor(Color.BLACK);
        TEMPgraph.getGridLabelRenderer().setHighlightZeroLines(true);
        // activate horizontal zooming and scrolling
        TEMPgraph.getViewport().setScalable(true);
        // activate horizontal scrolling
        TEMPgraph.getViewport().setScrollable(true);
        // activate vertical scrolling
        TEMPgraph.getViewport().setScrollableY(true);
        //Max and min of x axis
        TEMPgraph.getViewport().setXAxisBoundsManual(true);
        TEMPgraph.getViewport().setMinX(0);
        TEMPgraph.getViewport().setMaxX(10);

        //On touch listener ---- displays the touched point value
        TEMPseries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "DATA POINT: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        plottemp();
    }



    //To add data entry to the graph
    private void addBPEntry() {
        // here, we choose to display max points on the viewport and we scroll to end
        BPSseries.appendData(new DataPoint(lastBPSX++,mock.getBPSDataFromReceiver()), true,HistoryMockData.bpslist.size());
        BPDseries.appendData(new DataPoint(lastBPDX++,mock.getBPDDataFromReceiver()), true,HistoryMockData.bpdlist.size());

    }
    //To add data entry to the graph
    private void addSPO2Entry() {
        // here, we choose to display max points on the viewport and we scroll to end
        SPO2series.appendData(new DataPoint(lastSPO2X++,mock.getSPO2DataFromReceiver()), true,HistoryMockData.spo2list.size());
    }
    //To add data entry to the graph
    private void addHREntry() {
        // here, we choose to display max points on the viewport and we scroll to end
        HRseries.appendData(new DataPoint(lastHRX++,mock.getHRDataFromReceiver()), true,HistoryMockData.hrlist.size());
    }
    //To add data entry to the graph
    private void addWEntry() {
        // here, we choose to display max points on the viewport and we scroll to end
        Wseries.appendData(new DataPoint(lastWX++,mock.getWDataFromReceiver()), true,HistoryMockData.wlist.size());
    }
    //To add data entry to the graph
    private void addTEMPEntry() {
        // here, we choose to display max points on the viewport and we scroll to end
        TEMPseries.appendData(new DataPoint(lastTEMPX++,mock.getTEMPDataFromReceiver()), true,HistoryMockData.templist.size());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //Always makes screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(threadb) {
            //Interrupting thread to stop
            Thread.currentThread().interrupt();
            ArrayList<String> a=new ArrayList<>();
            mock.receiveData(a,a,a,a,a,a,a,a,a,a,a,a);

        }
        hm.clear();
        mock.clear();
        Intent intent=new Intent(HistoryGraph.this,Result.class);
        startActivity(intent);

    }


    @Override
    protected void onStop()
    {
        super.onStop();
        //Explicit garbage collection
        System.gc();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // clears always makes screen on condition
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        System.exit(0);
    }

    public static void Date(String bps[],String bpd[],String spo2[],String w[],String temp[],String hr[])
    {
        bpsdate=bps;
        bpddate=bpd;
        spo2date=spo2;
        wdate=w;
        tempdate=temp;
        hrdate=hr;
    }

    public void plottemp()
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Log.d("GRAPH STARTED", "$$$$$$$$$$$$");


                for (int i = 0; i < HistoryMockData.bpslist.size(); i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //For checking if graph is plotted or not
                            threadb = true;
                            //Adds new points in the graph
                            addBPEntry();
                        }
                    });
                }
                for (int j = 0; j < HistoryMockData.hrlist.size(); j++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //For checking if graph is plotted or not
                            threadb = true;
                            //Adds new points in the graph
                            addHREntry();
                        }
                    });
                }
                for (int k = 0; k < HistoryMockData.spo2list.size(); k++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //For checking if graph is plotted or not
                            threadb = true;
                            //Adds new points in the graph
                            addSPO2Entry();
                        }
                    });
                }
                for (int l = 0; l < HistoryMockData.wlist.size(); l++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //For checking if graph is plotted or not
                            threadb = true;
                            //Adds new points in the graph
                            addWEntry();
                        }
                    });
                }
                for (int m = 0; m < HistoryMockData.templist.size(); m++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //For checking if graph is plotted or not
                            threadb = true;
                            //Adds new points in the graph
                            addTEMPEntry();
                        }
                    });
                }

                // sleep to slow down the add of entries
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    // manage error ...
                }
            }
        }).start();
    }

}
