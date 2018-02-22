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
    Bitmap image ;
    ImageView mImageView;
    TextView life;
    TextView attaq;
    TextView defend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        nom = (TextView)  findViewById(R.id.n);
        categorie=  (TextView)  findViewById(R.id.cat);
        life = (TextView) findViewById(R.id.vie) ;
        attaq = (TextView) findViewById(R.id.attack) ;
        defend = (TextView) findViewById(R.id.def) ;
        mImageView = (ImageView) findViewById(R.id.p) ;
        Intent i = getIntent();

        String name = "";
        String category= "";
        String temp = "";
        String vie = "";
        String attack = "";
        String def = "";


        Intent intent = getIntent();
        if (null != intent) {
            name = intent.getStringExtra("nom");
            category = intent.getStringExtra("category");
            vie= intent.getStringExtra("vie");
            attack= intent.getStringExtra("attack");
            def= intent.getStringExtra("def");

            if (intent.getStringExtra("images")!=null){
                temp = intent.getStringExtra("images");

                byte [] encodeByte= Base64.decode(temp,Base64.DEFAULT);
                this.image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                mImageView.setImageBitmap(image);
            }

        }

        nom.setText(name);
        categorie.setText(category);
       life.setText(vie);
       attaq.setText(attack);
       defend.setText(def);



    }
}
