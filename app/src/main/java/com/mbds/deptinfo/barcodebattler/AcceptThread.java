package com.mbds.deptinfo.barcodebattler;

/**
 * Created by deptinfo on 03/02/18.
 */
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import static android.provider.Settings.NameValueTable.NAME;

/**
 * Created by deptinfo on 26/10/17.
 */

public class AcceptThread extends Thread {
    private static final UUID MY_UUID = new UUID(2,5) ;
    private final BluetoothServerSocket mmServerSocket;
    private static final String TAG = "MY_APP_DEBUG_TAG";
    private final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    String message  ;
    private Context context;

    public AcceptThread(Context context) {
        this.context =context ;
        BluetoothServerSocket tmp = null;
        try {
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                manageMyConnectedSocket(socket);
                break;
            }
        }
    }

    private void manageMyConnectedSocket(BluetoothSocket socket){
        //MyBluetoothService mbs = new MyBluetoothService();
        //   Log.i("TEST", "ManageConnectedSocket accept");
        InputStream is = null;
        try {
            is = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] buffer = new char[512];

        InputStreamReader isr = new InputStreamReader(is);
        try
        {
            isr.read(buffer);
            message = isr.getEncoding() ;
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Log.d("DEBUG",new String(buffer));

        Log.d("DEBUG",new String(buffer));
        Log.d("DEBUG",new String(buffer));


    }



    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}







