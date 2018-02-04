package com.mbds.deptinfo.barcodebattler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    TextView nom;
    TextView categorie;
    TextView force;
    Bitmap image ;
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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

    }
}
