package com.htic.amar.kiosk;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DELL on 08-06-2017.
 */

public class CentralPath {

    public static CENTRAL central;
    public static Context global;

    MockData mock = new MockData();

    public void con(Context c) {
        //getting the context of the called activity and starts the central class for getting data
        global = c;
        central = new CENTRAL(global);
    }

    public void spo2data() {

        //configuring the MC
        central.configure();

        //checks if configured or not
        if (FT311UARTInterface.checks) {
            CENTRAL.displaybox=true;
            CENTRAL.graphdata = "";
            //clears the data storage variable
            central.readSB.delete(0, central.readSB.length());
            central.check = true;
            //sends command tto the MC
            central.writeData("MEASR_SPO2");
           // Toast.makeText(global, "DATA_RECEIVED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(global, "DEVICE NOT CONFIGURED...", Toast.LENGTH_SHORT).show();
        }
    }

    public void spo2next() {

        //checks if configured or not
        if (FT311UARTInterface.checks) {
            //checks if graphdata is present
            if (CENTRAL.graphdata != "") {
                //sends graph data to the spo2 graph activity
                mock.receiveData(CENTRAL.graphdata);
                //clears the data storage variable
                central.readSB.delete(0, central.readSB.length());
            }
        } else {
            Toast.makeText(global, "DEVICE NOT CONFIGURED...", Toast.LENGTH_SHORT).show();
        }
    }
    public void ecgdata() {

        //checks if configured or not
        if (FT311UARTInterface.checks) {
            CENTRAL.displaybox=false;
            CENTRAL.graphdata = "";
            //clears the data storage variable
            central.readSB.delete(0, central.readSB.length());
            central.check = true;
            //sends command to the MC. In final app change this command to the ECG data command
            central.writeData("MEASR_SPO2");
           // Toast.makeText(global, "DATA_RECEIVED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(global, "DEVICE NOT CONFIGURED...", Toast.LENGTH_SHORT).show();
        }
    }

    public void ecgnext() {

        //checks if configured or not
        if (FT311UARTInterface.checks) {
            if (CENTRAL.graphdata != "") {
                //sends graph data to the ecg graph activity
                mock.receiveData(CENTRAL.graphdata);
                //clears the data storage variable
                central.readSB.delete(0, central.readSB.length());

            }
        } else {
            Toast.makeText(global, "DEVICE NOT CONFIGURED...", Toast.LENGTH_SHORT).show();
        }
    }

    public void bpdata() {

        //checks if configured or not
        if (FT311UARTInterface.checks) {
            //setting the correct display view to display
            CENTRAL.displayview=0;
            central.check = false;
            //clears the data storage variable
            central.readSB.delete(0, central.readSB.length());
            //sends command to the MC
            central.writeData("MEASR_HBPM");
        } else {
            Toast.makeText(global, "DEVICE NOT CONFIGURED...", Toast.LENGTH_SHORT).show();
        }
    }
    public void tempdata() {

        //checks if configured or not
        if (FT311UARTInterface.checks) {
            //setting the correct display view to display
            CENTRAL.displayview=1;
            central.check = false;
            //clears the data storage variable
            central.readSB.delete(0, central.readSB.length());
            //sends command to the MC
            central.writeData("MEASR_TEMP");
        } else {
            Toast.makeText(global, "DEVICE NOT CONFIGURED...", Toast.LENGTH_SHORT).show();
        }
    }
}

