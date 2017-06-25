package com.htic.amar.kiosk;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import android.widget.Toast;


/**
 * Created by DELL on 07-06-2017.
 */

public class CENTRAL {

    StringBuffer readSB = new StringBuffer();

    /* thread to read the data */
    private handler_thread handlerThread;

    /* declare a FT311 UART interface variable */
    private FT311UARTInterface uartInterface;

    //graph data from MC to graph activity
    public static String graphdata;

    public static boolean displaybox=true;
    public static int displayview=0;
    /* local variables */
    private byte[] writeBuffer;
    private byte[] readBuffer;
    private char[] readBufferToChar;
    private int[] actualNumBytes;
    //for differentiating data to plot graph i.e, SPO2 and ECG and the other data attributes
    public boolean check = false;
    private int numBytes;
    private byte status;

    //Configuration settings
    private static int baudRate = 115200; /* baud rate */
    private static byte stopBit = 1; /* 1:1stop bits, 2:2 stop bits */
    private static byte dataBit = 8; /* 8:8bit, 7: 7bit */
    private static byte parity = 0; /* 0: none, 1: odd, 2: even, 3: mark, 4: space */
    private static byte flowControl = 0; /* 0:none, 1: flow control(CTS,RTS) */

    //Context
    private Context global_context;
    //Configure check
    private boolean bConfiged = false;


    CENTRAL(Context global) {

        //context of the called activity
        global_context=global;


       		/* allocate buffer */
        writeBuffer = new byte[64];
        readBuffer = new byte[4096];
        readBufferToChar = new char[4096];
        actualNumBytes = new int[1];

        //FT311UARTInterface call statement
        uartInterface = new FT311UARTInterface(global_context);


        //Start read handler
        handlerThread = new handler_thread(handler);

        handlerThread.start();


        //Calling Resume accessory
        if (2 == uartInterface.ResumeAccessory()) {
            System.exit(0);
        }

    }

    //Configuring the FTDI chip
    public void configure()
    {
        if (!bConfiged) {
            bConfiged = true;
            uartInterface.SetConfig(baudRate, dataBit, stopBit, parity, flowControl);
            Toast.makeText(global_context, "CONFIGURED", Toast.LENGTH_SHORT).show();
        }
    }
    //Handler to receive data from MC
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            for (int i = 0; i < actualNumBytes[0]; i++) {
                readBufferToChar[i] = (char) readBuffer[i];
            }
            appendData(readBufferToChar, actualNumBytes[0]);
        }
    };

    /* usb input data handler */
    private class handler_thread extends Thread {
        Handler mHandler;

        /* constructor */
        handler_thread(Handler h) {

            mHandler = h;
        }


        public void run() {
            Message msg;

            while (true) {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {

                }

                status = uartInterface.ReadData(4096, readBuffer, actualNumBytes);

                if (status == 0x00 && actualNumBytes[0] > 0) {
                    msg = mHandler.obtainMessage();
                    mHandler.sendMessage(msg);
                }

            }
        }
    }

        //Writes or sends data to the MC
        public void writeData(String data)

        {
            String srcStr = data;
            String destStr;

            destStr = srcStr;
            numBytes = destStr.length();
            for (int i = 0; i < numBytes; i++) {
                writeBuffer[i] = (byte) destStr.charAt(i);
            }
            uartInterface.SendData(numBytes, writeBuffer);
        }


        //USING RECEIVED DATA FROM MC
        public void appendData(char[] data, int len) {
            String tempdata,temp;
            String bp[]=new String[2];
            //DATA FROM BP,TEMP,DEVICE ON,DEVICE OFF
            if (!check) {
                if (len >= 1)
                    readSB.append(String.copyValueOf(data, 0, len));

                if(displayview==0) {

                    //Filtering the incoming data
                    temp= readSB.toString().trim();
                    temp=temp.replaceAll("S","");
                    temp=temp.replaceAll("D","");
                    temp=temp.replaceAll("mm/Hg","");
                    bp=temp.split("/");
                    bp[1]=bp[1].trim();
                    Result.bpsys=bp[0];
                    Result.bpdia=bp[1];
                }
                if(displayview==1) {
                    //Filtering the incoming data
                    temp= readSB.toString().trim();
                    temp=temp.replaceAll("T","");
                    temp=temp.replaceAll("degC","");
                    temp=temp.trim();
                    Result.tempvalue=temp;
                }

            } else {
                //DATA FROM SPO2 AND ECG
                if (len >= 1)
                    readSB.append(String.copyValueOf(data, 0, len));
                tempdata = readSB.toString();
                if (tempdata.contains("SPO2_END")) {
                    tempdata = tempdata.replaceAll("SPO2_END", "");
                    if(displaybox) {
                        //Already defined data
                        Result.spo2value="170";
                        Result.hrvalue="76";
                        SPO2_Main.dialog.hide();
                        SPO2_Main.spo2next.setEnabled(true);
                    }
                    else {
                        ECG_Main.dialog.hide();
                        ECG_Main.ecgnext.setEnabled(true);
                    }
                }
                readSB = new StringBuffer(tempdata);
                tempdata = readSB.toString();
                //sending data to graph for plotting for both ECG and SPO2
                graphdata = tempdata;
                if(displaybox)
                    //display is set invisible
                SPO2_Main.display.setText(graphdata);
                else
                    //display is set invisible
                    ECG_Main.display.setText(graphdata);
            }

        }
    }