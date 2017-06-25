package com.htic.amar.kiosk;

import java.util.ArrayList;

//Generates data for history graph to plot
public class HistoryMockData {
    public static int ypoint=0;
    public static int bpscheck=0,bpdcheck=0,len,spo2check,wcheck,hrcheck,tempcheck;
    public static boolean e=true;
    public static ArrayList<String> bpslist=new ArrayList<>();
    public static ArrayList<String> bpdlist=new ArrayList<>();
    public static ArrayList<String> spo2list=new ArrayList<>();
    public static ArrayList<String> hrlist=new ArrayList<>();
    public static ArrayList<String> wlist=new ArrayList<>();
    public static ArrayList<String> templist=new ArrayList<>();

    public static ArrayList<String> bpsdate=new ArrayList<>();
    public static ArrayList<String> bpddate=new ArrayList<>();
    public static ArrayList<String> spo2date=new ArrayList<>();
    public static ArrayList<String> hrdate=new ArrayList<>();
    public static ArrayList<String> wdate=new ArrayList<>();
    public static ArrayList<String> tempdate=new ArrayList<>();

    //To receive graphdata from history_main from MC
    public void receiveData(ArrayList<String> bpsY, ArrayList<String> bpsX,ArrayList<String> bpdY, ArrayList<String> bpdX, ArrayList<String> spo2Y, ArrayList<String> spo2X, ArrayList<String> wY, ArrayList<String> wX, ArrayList<String> hrY, ArrayList<String> hrX, ArrayList<String> tempY, ArrayList<String> tempX)
    {
        //clears variables
        bpscheck=0;
        bpdcheck=0;
        hrcheck=0;
        tempcheck=0;
        wcheck=0;
        spo2check=0;


        //For checking if data is received or not
        e=false;

        //stores graph data
        bpslist=bpsY;
        bpsdate=bpsX;

        String []bps = new String[bpsdate.size()];
        bpsdate.toArray(bps);

        bpdlist=bpdY;
        bpddate=bpdX;

        String []bpd = new String[bpddate.size()];
        bpddate.toArray(bpd);

        spo2list=spo2Y;
        spo2date=spo2X;

        String []spo2d = new String[spo2date.size()];
        spo2date.toArray(spo2d);

        wlist=wY;
        wdate=wX;

        String []wd = new String[wdate.size()];
        wdate.toArray(wd);

        hrlist=hrY;
        hrdate=hrX;

        String []hrd = new String[hrdate.size()];
        hrdate.toArray(hrd);

        templist=tempY;
        tempdate=tempX;

        String []tempd = new String[tempdate.size()];
        tempdate.toArray(tempd);

        //sends the date variables to the graph activity
        HistoryGraph.Date(bps,bpd,spo2d,wd,tempd,hrd);

    }

    //clears the variables
    public void clear()
    {
        bpslist.removeAll(bpslist);
        bpdlist.removeAll(bpdlist);
        hrlist.removeAll(hrlist);
        wlist.removeAll(wlist);
        spo2list.removeAll(spo2list);
        templist.removeAll(templist);

        bpsdate.removeAll(bpsdate);
        bpddate.removeAll(bpddate);
        hrdate.removeAll(hrdate);
        wdate.removeAll(wdate);
        spo2date.removeAll(spo2date);
        tempdate.removeAll(tempdate);

    }


    //sends BP systolic y-axis value to plot
    public int getBPSDataFromReceiver()
    {
        ypoint=Integer.parseInt(generateBPSData());
            return ypoint;

    }

    //generates y axis value one by one to plot
    private static String generateBPSData() {
        //Checks whether the data is received or not
            if (!e ) {
                for (int i = bpscheck; i < bpslist.size(); i++) {
                    bpscheck = bpscheck + 1;
                    //Returns y axis data one by one to plot
                    return bpslist.get(i);
                }
            }
            return "0";
    }

    //sends BP diastolic y-axis value to plot
    public int getBPDDataFromReceiver()
    {
        ypoint=Integer.parseInt(generateBPDData());
        return ypoint;



    }

    //generates y axis value one by one to plot
    private static String generateBPDData() {
        //Checks whether the data is received or not
        if (!e ) {
            for (int i = bpdcheck; i < bpdlist.size(); i++) {
                bpdcheck = bpdcheck + 1;
                //Returns y axis data one by one to plot
                return bpdlist.get(i);
            }
        }
        return "0";
    }

    //sends SPO2 y-axis data to plot
    public int getSPO2DataFromReceiver()
    {
        ypoint=Integer.parseInt(generateSPO2Data());
        return ypoint;



    }

    //generates y axis value one by one to plot
    private static String generateSPO2Data() {

        //Checks whether the data is received or not
        if (!e ) {
            for (int i = spo2check; i < spo2list.size(); i++) {
                spo2check = spo2check + 1;
                //Returns y axis data one by one to plot
                return spo2list.get(i);
            }
        }
        return "0";
    }


    //sends weight y-axis data to plot
    public int getWDataFromReceiver()
    {
        ypoint=Integer.parseInt(generateWData());
        return ypoint;



    }

    //generates y axis value one by one to plot
    private static String generateWData() {

        //Checks whether the data is received or not
        if (!e ) {
            for (int i = wcheck; i < wlist.size(); i++) {
                wcheck = wcheck + 1;
                //Returns y axis data one by one to plot
                return wlist.get(i);
            }
        }
        return "0";
    }


    //sends heart rate y-axis data to plot
    public int getHRDataFromReceiver()
    {
        ypoint=Integer.parseInt(generateHRData());
        return ypoint;



    }

    //generates y axis value one by one to plot
    private static String generateHRData() {

        //Checks whether the data is received or not
        if (!e ) {
            for (int i = hrcheck; i < hrlist.size(); i++) {
                hrcheck = hrcheck + 1;
                //Returns y axis data one by one to plot
                return hrlist.get(i);
            }
        }
        return "0";
    }


    //sends temperature y-axis data to plot
    public int getTEMPDataFromReceiver()
    {
        ypoint=Integer.parseInt(generateTEMPData());
        return ypoint;



    }

    //generates y axis value one by one to plot
    private static String generateTEMPData() {
        //Checks whether the data is received or not
        if (!e ) {
            for (int i = tempcheck; i < templist.size(); i++) {
                tempcheck = tempcheck + 1;
                //Returns y axis data one by one to plot
                return templist.get(i);
            }
        }
        return "0";
    }

}
