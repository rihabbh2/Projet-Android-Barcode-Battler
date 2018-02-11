package com.mbds.deptinfo.barcodebattler;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;


/**
 * Created by deptinfo on 26/10/17.
 */

public class ConnectThread extends Thread
{

    private static final UUID MY_UUID = new UUID(2,5) ;
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private static final String TAG = "MY_APP_DEBUG_TAG";
    private  Handler mHandler= null;
    public String test ;


    public ConnectThread(BluetoothDevice device, Handler handler)
    {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mHandler = handler;
        mmDevice = device;

        try
        {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        }
        catch (IOException e)
        {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.cancelDiscovery();

        try
        {
            mmSocket.connect();
            Log.e(TAG, "connect");

        }
        catch (IOException connectException)
        {
            // Unable to connect; close the socket and return.
            try
            {
                mmSocket.close();
            }
            catch (IOException closeException)
            {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        MyBluetoothService mbs = new MyBluetoothService(mmSocket,mHandler);
        for(int i = 0 ; i < 10 ; i++)
            {
                mbs.cThread.write("Bonjour".getBytes());
                test= "Message Envoyé" ;
            }

    }

    private void manageMyConnectedSocket(BluetoothSocket mmSocket)
    {
      //  Log.i("Test", "ManageConnectedSocket connect");

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OutputStream os = null;
        try
        {
            os = mmSocket.getOutputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        OutputStreamWriter osw = new OutputStreamWriter(os);
        try
        {
            osw.write("mon message");
            osw.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e)
        {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }
}
