package com.htic.amar.kiosk;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Spo2 extends AppCompatActivity {

    Button spo2next;
    private LineGraphSeries<DataPoint> series;
    private int lastX = 1;
    MockData mock=new MockData();
    private boolean threadb=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spo2);

        spo2next=(Button) findViewById(R.id.spo2next);

        //stops the graph and starts BP video activity
        spo2next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(threadb) {
                    Thread.currentThread().interrupt();
                    //Sending a empty data to graph to stop its plotting
                    CENTRAL.graphdata = "";
                    mock.receiveData(CENTRAL.graphdata);
                }

                Intent intent=new Intent(Spo2.this,VideoActivity.class);
                intent.putExtra("Video","1");
                intent.putExtra("Activity","BP");
                startActivity(intent);
            }
        });
        //Plot data button
        plot();

        getWindow().setSoftInputMode(3);
        //Graph view
        GraphView graph = (GraphView) findViewById(R.id.spo2graph);
        series = new LineGraphSeries<>();
        // styling series
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(0);
        // custom paint to make a dotted line
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5f);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        series.setCustomPaint(paint);
        series.setTitle("SPO2 DATA");
        //Add series to graph
        graph.addSeries(series);
        //Graph view styling
        graph.setTitle("SPO2 READINGS");
        graph.setTitleTextSize(40f);
        graph.setTitleColor(Color.BLUE);
        graph.getGridLabelRenderer().setLabelVerticalWidth(30);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("NUMBER OF DATA");
        graph.getGridLabelRenderer().setVerticalAxisTitle("SPO2 DATA");
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.RED);
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        graph.getGridLabelRenderer().setGridColor(Color.BLACK);
        graph.getGridLabelRenderer().setHighlightZeroLines(true);
        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);
        // activate horizontal scrolling
        graph.getViewport().setScrollable(true);
        // activate vertical scrolling
        graph.getViewport().setScrollableY(true);
        //Max and min of x axis
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(200);
        //On touch listener ---- displays the touched point value
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "DATA POINT: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //To add data entry to the graph
    private void addEntry() {
        // here, we choose to display max points on the viewport and we scroll to end
        series.appendData(new DataPoint(lastX++,mock.getDataFromReceiver()), true,MockData.databit.length);
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
            Thread.currentThread().interrupt();
            //Sending a empty data to graph to stop its plotting
            CENTRAL.graphdata = "";
            mock.receiveData(CENTRAL.graphdata);
        }
        Intent intent=new Intent(Spo2.this,SPO2_Main.class);
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

    public void plot()
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < MockData.databit.length +10; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //For checking if graph is plotted or not
                            threadb=true;
                            //Adds new points in the graph
                            addEntry();
                        }
                    });
                    // sleep to slow down the addition of entries
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

}
