package com.htic.amar.kiosk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by DELL on 14-06-2017.
 */
//This class is used to scan the available BLE devices and connect.
public class Devices {
    private BluetoothAdapter mBluetoothAdapter;
    private List<BluetoothDevice> deviceList;
    private Context global;
    private heightandweight main=new heightandweight();
    private boolean deviceFound;


    Devices(Context c)
    {
        //storing context of called activity
        global=c;
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!c.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(c, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
        }
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) c.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(c, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        scanLeDevice(true);
        deviceFound = false;
        //To store the available devices
        deviceList = new ArrayList<BluetoothDevice>();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
    }
    //scanning method
    //scan for unlimited duration until it gets connected to both devices
    private void scanLeDevice(final boolean enable) {
        //showing scanning dialog
        heightandweight.dialog.show();
        if (enable) {
            // Stops scanning after a pre-defined scan period.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                    if (!deviceFound) {
//                        Toast.makeText(global, "DEVICE NOT FOUND", Toast.LENGTH_SHORT).show();
//                        MainActivity.dialog.hide();
//                        Log.d("dialog", "hide>>>>>>>>>>>>");
//                        MainActivity.disconnectdevices();
//                        scanLeDevice(true);
//                    }
//                }
//            }, SCAN_PERIOD);

            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);

        }

    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
                    //adding discovered devices
                            addDevice(device,rssi);

                }
            };

    private void addDevice(BluetoothDevice device, int rssi) {


        for (BluetoothDevice listDev : deviceList) {
            if (listDev.getAddress().equals(device.getAddress())) {
                //scanning the needed device address and connecting to it when discovered
                //First connects to the first device and then connects to the next
                if (device.getAddress().equals("CC:AD:92:B3:77:94") && heightandweight.device == 0) {
                    device1(device);
                } else if (device.getAddress().equals("FA:08:A9:0E:58:89") && heightandweight.device == 1) {
                    device2(device);
                }
                break;

            }
        }

        if (!deviceFound) {
            deviceList.add(device);
        }
    }

    public void device1(BluetoothDevice device)
    {
        //connecting to device address
        connect(device.getAddress());
        deviceFound = true;
        //incrementing the number of connected device
        heightandweight.device++;
        Toast.makeText(global,"CONNECTED TO "+device.getAddress(),Toast.LENGTH_SHORT).show();
    }
    public void device2(BluetoothDevice device)
    {
        //connecting to device address
        connect(device.getAddress());
        deviceFound = true;
        //incrementing the number of connected device
        heightandweight.device++;
        Toast.makeText(global,"CONNECTED TO "+device.getAddress(),Toast.LENGTH_SHORT).show();

    }
    //Connecting class
    public void connect(String address)
    {
        //hiding the scanning dialog
        heightandweight.dialog.hide();
        //stops the scan
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        //creates a bundle with the device address
            Bundle b = new Bundle();
            b.putString(BluetoothDevice.EXTRA_DEVICE, address);
            Intent result = new Intent();
            result.putExtras(b);
        //sends it back to the height and weight activity for creation
           main.result(result);
    }
}
