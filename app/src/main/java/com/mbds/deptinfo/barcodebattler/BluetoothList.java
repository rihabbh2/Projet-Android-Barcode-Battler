package com.mbds.deptinfo.barcodebattler;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.LogRecord;

public class BluetoothList extends AppCompatActivity implements ListAdapter {

    ListView lv;
    ArrayList<Appareil> LA ;
    BluetoothAdapter mBluetoothAdapter ;
    AcceptThread serverThread;
    ConnectThread connectThread ;
    Button share ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_list);
        mBluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        LA = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listb);


        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Appareil appareil = new Appareil(deviceName, deviceHardwareAddress,device);
                LA.add(appareil);
            }
        }
      /* final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress();
                    Appareil appareil = new Appareil(deviceName, deviceHardwareAddress);
                    LA.add(appareil);

                }
            }
        };

*/
        lv.setAdapter(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Appareil app = (Appareil) BluetoothList.this.getItem(position);
                connectThread = new ConnectThread(app.getBd(),mHandler);
                connectThread.start();
                Toast toast = Toast.makeText(BluetoothList.this.getBaseContext(), connectThread.test, Toast.LENGTH_LONG);
                toast.show();
               // Toast toast = Toast.makeText(BluetoothList.this.getBaseContext(), "Connecté avec " +app.getNomBluetooth(), Toast.LENGTH_LONG);
                //toast.show();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        serverThread.cancel();
    }

    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Toast.makeText(BluetoothList.this,msg.toString(),Toast.LENGTH_LONG);
        }
    };

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return LA.size();
    }

    @Override
    public Object getItem(int position) {
        return LA.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View returnView;
        if (convertView==null)
        {
            returnView=  View.inflate(this,R.layout.bluetooth,null);
        }
        else
        {
            returnView=convertView;
        }
        final TextView text =(TextView) returnView.findViewById(R.id.blue);
        text.setText(LA.get(position).getNomBluetooth());
        final TextView txt =(TextView) returnView.findViewById(R.id.machine);
        txt.setText(LA.get(position).getMachine());
        share = (Button) returnView.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverThread = new AcceptThread(BluetoothList.this);
                serverThread.start();
            }
        });
        return  returnView;

    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        if (LA.size()==0){
            return true;
        }
        return false;
    }
}
