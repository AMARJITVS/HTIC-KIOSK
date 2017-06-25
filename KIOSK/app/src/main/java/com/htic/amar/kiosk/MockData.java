package com.htic.amar.kiosk;



//generates data for SPO2 and ECG graph to plot
public class MockData {
    private static int ypoint=0;
    private static int gcheck=0,len;
    private static boolean e=true;
    public static String databit[];

    //To receive graphdata from MainActivity from MC
    public void receiveData(String data)
    {
        gcheck=0;
        //To store graphdata
        databit=new String[data.length()/5];
        int c=0;
        //For checking if data is received or not
        e=false;
        //Calculating the data length
        len=data.length();
        //For dividing the graph data in five and store it to plot
        for(int i=0;i<len;i++)
        {
            if(data.length()-c>=5)
            {
                databit[i]=data.substring(c,c+5);

                c=c+5;
            }
            else
            {
              break;
            }
        }


    }

    //sends y-axis data for SPO2 and ECG graph to plot
    public int getDataFromReceiver()
    {
        ypoint=Integer.parseInt(generateData());
            return ypoint;



    }

    //generates y axis value one by one to plot
    private static String generateData() {

        //Checks whether the data is received or not
            if (!e ) {
                for (int i = gcheck; i < len / 5; i++) {
                    gcheck = gcheck + 1;
                    //Returns y axis data one by one to plot
                    return databit[i];
                }
            }
            return "0";
    }

}
