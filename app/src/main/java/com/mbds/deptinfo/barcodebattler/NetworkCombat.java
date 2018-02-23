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


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class NetworkCombat extends AppCompatActivity {


    private DatabaseReference databaseCombat;
    Monster m1 ;
    Monster m2 ;
    Button attack;
    Button defense ;
    String id ;
    String turn ;
    String oturn ;
    String mac ;
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_combat);

        Intent intent = getIntent();
        if (null != intent) {
            id = intent.getStringExtra("id");
            turn = intent.getStringExtra("turn") ;
            mac= intent.getStringExtra("mac");
        }

        databaseCombat = FirebaseDatabase.getInstance().getReference();
        databaseCombat = databaseCombat.child("Combat").child(mac) ;
            databaseCombat.addChildEventListener(new ChildEventListener() {
                public Bitmap image;

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Monster m = (Monster) dataSnapshot.getValue(Monster.class);
                    if (m.id.equals(id) && turn.equals(dataSnapshot.getKey())) {//(m.taken==false && m1==null) {
                        if (turn.equals("0")  ) {
                            oturn = "1";
                        } else {
                            oturn="0" ;
                        }
                        //m.setTaken(true);
                        m1 = m;
                        databaseCombat.child(turn).child("taken").setValue(true);

                        mImageView = (ImageView) findViewById(R.id.p) ;

                        if (m1.imgBase64.equals(null)) {
                        } else {
                            String temp = m1.imgBase64;

                            byte [] encodeByte= Base64.decode(temp,Base64.DEFAULT);
                            this.image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                            mImageView.setImageBitmap(image);
                        }
                        TextView t = (TextView) findViewById(R.id.gid);
                        t.setText(mac);
                        TextView text = (TextView) findViewById(R.id.n);
                        text.setText(m1.getNom());
                        TextView txt = (TextView) findViewById(R.id.cat);
                        txt.setText(m1.getCategorie());
                        DatabaseReference refLife = databaseCombat.child(turn).child("vie");
                        refLife.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TextView txt = (TextView) findViewById(R.id.forc);
                                int vie = dataSnapshot.getValue(Integer.class);
                                txt.setText(Integer.toString(vie));
                                if (vie <= 0) {
                                    Intent intent = new Intent(NetworkCombat.this, Defaite.class);
                                    NetworkCombat.this.startActivity(intent);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    } else {
                        if (turn.equals("0")  ) {
                            oturn = "1";
                        } else {
                            oturn="0" ;
                        }
                        m2 = m;
                        TextView text2 = (TextView) findViewById(R.id.n1);
                        text2.setText(m2.getNom());
                        TextView txt2 = (TextView) findViewById(R.id.cat1);
                        txt2.setText(m2.getCategorie());
                        mImageView = (ImageView) findViewById(R.id.p1) ;
                        if (m2.getImgBase64()!=null){
                            String temp = m2.imgBase64;

                            byte [] encodeByte= Base64.decode(temp,Base64.DEFAULT);
                            this.image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                            mImageView.setImageBitmap(image);
                        }
                        DatabaseReference refLife = databaseCombat.child(oturn).child("vie");
                        refLife.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TextView txt = (TextView) findViewById(R.id.forc1);
                                int vie = dataSnapshot.getValue(Integer.class);
                                txt.setText(Integer.toString(vie));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        DatabaseReference refImg = databaseCombat.child(oturn).child("imgBase64");
                        refImg.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String temp =dataSnapshot.getValue(String.class);
                                byte [] encodeByte= Base64.decode(temp,Base64.DEFAULT);
                                Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                mImageView.setImageBitmap(image);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        attack= (Button) findViewById(R.id.attack);
        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int attack = (int) (Math.random() * m1.getAttack());
                m2.setVie(m2.getVie()-attack);
                databaseCombat.child(oturn).child("vie").setValue(m2.getVie());
                TextView text = (TextView) findViewById(R.id.arme);
                text.setText("-" +attack);
                if (m2.getVie()<=0) {
                    Intent intent =new Intent(NetworkCombat.this, Victoire.class);
                    NetworkCombat.this.startActivity(intent);
                }


            }
        });

       defense= (Button) findViewById(R.id.def);
       defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int def = (int) (Math.random() * m1.getDef());
                m1.setVie(m1.getVie()+def);
                databaseCombat.child(turn).child("vie").setValue(m1.getVie());
                TextView text = (TextView) findViewById(R.id.arme);
                text.setText("+" +def);
            }
        });



    }



}
