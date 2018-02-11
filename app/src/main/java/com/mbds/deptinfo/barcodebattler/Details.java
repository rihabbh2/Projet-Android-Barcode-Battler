package com.mbds.deptinfo.barcodebattler;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    TextView nom;
    TextView categorie;
    TextView force;
    Bitmap image ;
    ImageView mImageView;
    private Button reseau;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mBluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();
        nom = (TextView)  findViewById(R.id.n);
        categorie=  (TextView)  findViewById(R.id.cat);
        force = (TextView) findViewById(R.id.forc) ;
        mImageView = (ImageView) findViewById(R.id.p) ;
        Intent i = getIntent();

        String name = "";
        String category= "";
        String temp = "";
        int strengh = 0;

        Intent intent = getIntent();
        if (null != intent) {
            name = intent.getStringExtra("nom");
            category = intent.getStringExtra("category");
            strengh = intent.getIntExtra("force", 0);
            if (intent.getStringExtra("images")!=null){
                temp = intent.getStringExtra("images");

                byte [] encodeByte= Base64.decode(temp,Base64.DEFAULT);
                this.image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                mImageView.setImageBitmap(image);
            }

        }

        nom.setText(name);
        categorie.setText(category);
        force.setText(Integer.toString(strengh));

        reseau = (Button) findViewById(R.id.reseau);
       reseau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    Toast toast = Toast.makeText(Details.this.getBaseContext(), "Le téléphone ne supporte pas le bluetooth", Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                } if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                if (mBluetoothAdapter.isEnabled()){
                    Intent intent =new Intent(Details.this, BluetoothList.class);
                    Details.this.startActivity(intent);
                }

            }
        }) ;

    }
}
