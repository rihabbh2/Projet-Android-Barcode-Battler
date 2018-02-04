package com.mbds.deptinfo.barcodebattler;

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

public class LocalCombat extends AppCompatActivity {

    TextView nom1;
    TextView categorie1;
    TextView force1;
    Bitmap image1 ;
    ImageView mImageView1;
    TextView nom2;
    TextView categorie2;
    TextView force2;
    Bitmap image2 ;
    ImageView mImageView2;
    private Button attack;
    Monster m1;
    Monster m2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_combat);
        nom1 = (TextView)  findViewById(R.id.n);
        categorie1=  (TextView)  findViewById(R.id.cat);
        force1 = (TextView) findViewById(R.id.forc) ;
        mImageView1 = (ImageView) findViewById(R.id.p) ;
        nom2 = (TextView)  findViewById(R.id.n1);
        categorie2=  (TextView)  findViewById(R.id.cat1);
        force2 = (TextView) findViewById(R.id.forc1) ;
        mImageView2 = (ImageView) findViewById(R.id.p1) ;
        Intent i = getIntent();

        String name1 = "";
        String category1= "";
        String temp1 = "";
        int strengh1 = 0;

        String name2 = "";
        String category2= "";
        String temp2 = "";
        int strengh2 = 0;

        Intent intent = getIntent();
        if (null != intent) {
            name1 = intent.getStringExtra("nom1");
            category1 = intent.getStringExtra("category1");
            strengh1 = intent.getIntExtra("force1", 0);
            name2 = intent.getStringExtra("nom2");
            category2 = intent.getStringExtra("category2");
            strengh2 = intent.getIntExtra("force2", 0);
            if (intent.getStringExtra("images1")!=null){
                temp1 = intent.getStringExtra("images1");

                byte [] encodeByte= Base64.decode(temp1,Base64.DEFAULT);
                this.image1 = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                mImageView1.setImageBitmap(image1);
            }
            if (intent.getStringExtra("images2")!=null){
                temp1 = intent.getStringExtra("images2");

                byte [] encodeByte= Base64.decode(temp1,Base64.DEFAULT);
                this.image2 = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                mImageView2.setImageBitmap(image2);
            }
        }

        nom1.setText(name1);
        categorie1.setText(category1);
        nom2.setText(name2);
        categorie2.setText(category2);
        m1 = new Monster(nom1.toString(),category1,image1, 100);
        m2 = new Monster(nom2.toString(),category2,image2, 100);
        force2.setText(Integer.toString(m2.getForceBrute()));
        force1.setText(Integer.toString(m1.getForceBrute()));


        attack= (Button) findViewById(R.id.attack);
        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int attack1=  (int) (Math.random()*20);
                m2.setForceBrute(m2.getForceBrute()-attack1) ;
                force2.setText(""+m2.getForceBrute());
                if (m2.getForceBrute() <= 0){
                    Toast toast1 = Toast.makeText(LocalCombat.this.getBaseContext(), "Vous avez gagné", Toast.LENGTH_LONG);
                    toast1.show();
                } else {
                    int attack2 = (int) (Math.random() * 20);
                    m1.setForceBrute(m1.getForceBrute() - attack2);
                    force1.setText(""+m1.getForceBrute());
                    if (m1.getForceBrute() <= 0) {
                        Toast toast2 = Toast.makeText(LocalCombat.this.getBaseContext(),"Vous avez perdu", Toast.LENGTH_LONG);
                        toast2.show();
                    }
                }
            }

        });


    }


}
