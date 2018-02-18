package com.mbds.deptinfo.barcodebattler;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {


    private static final int REQUEST_ENABLE_BT = 8;
    Button collection ;
    Button barcode ;
    Button blue ;
    BluetoothAdapter mBluetoothAdapter ;
    Button local ;
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // setContentView(R.layout.activity_menu);
        mBluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_menu);
        barcode = (Button) findViewById(R.id.barcode);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Menu.this, Barcode.class);
                Menu.this.startActivity(intent);
            }

        });
        test = (Button) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Menu.this, JoinOnlineGame.class);
                Menu.this.startActivity(intent);

            }

        });
        collection = (Button) findViewById(R.id.collection);
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Menu.this, Collection.class);
                Menu.this.startActivity(intent);
            }

        });
        local= (Button) findViewById(R.id.combat);
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Menu.this, ChooseMonster.class);
                Menu.this.startActivity(intent);
            }

        });

        blue = (Button) findViewById(R.id.blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    if (mBluetoothAdapter == null) {
                    Toast toast = Toast.makeText(Menu.this.getBaseContext(), "Le téléphone ne supporte pas le bluetooth", Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                } if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                if (mBluetoothAdapter.isEnabled()){
                    Intent intent =new Intent(Menu.this, BluetoothList.class);
                    Menu.this.startActivity(intent);
                }
                 //serverThread = new AcceptThread();
                //serverThread.start();

            }

        });*/
              /*  Intent intent = new Intent(Menu.this, NetworkCombat.class);
                Menu.this.startActivity(intent);*/
                Intent intent =new Intent(Menu.this, StartOnlineGame.class);
                Menu.this.startActivity(intent);
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
            int hasSDcardWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasSDcardReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);

            }


            if (hasSDcardWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }

            if (hasSDcardReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            }
            if (hasSDcardReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.BLUETOOTH);

            }
            if (hasSDcardReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.BLUETOOTH_ADMIN);

            }

            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
            }
        }
    }
}
