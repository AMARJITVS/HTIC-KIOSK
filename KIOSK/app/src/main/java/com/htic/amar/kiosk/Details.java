package com.htic.amar.kiosk;

/**
 * Created by DELL on 02-06-2017.
 */
//This class is for storing data in the firebase.
    //This class is used to retreive the necessary attributes to store in the firebase when needed.
public class Details {

    public String bps,bpd,spo2,height,weight,heart_rate,temp;
    public Details()
    {}


public Details(String bps, String bpd, String height, String weight, String heart_rate, String spo2, String temp)
{
    this.bps=bps;
    this.bpd=bpd;
    this.spo2=spo2;
    this.heart_rate=heart_rate;
    this.height=height;
    this.weight=weight;
    this.temp=temp;

}

    public String getTemp() {
        return temp;
    }


    public String getBps() {
        return bps;
    }

    public String getBpd() {
        return bpd;
    }

    public String getSpo2() {
        return spo2;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeart_rate() {
        return heart_rate;
    }
}
