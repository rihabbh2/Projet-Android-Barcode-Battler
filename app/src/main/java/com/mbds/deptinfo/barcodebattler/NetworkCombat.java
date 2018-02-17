package com.mbds.deptinfo.barcodebattler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicMarkableReference;


public class NetworkCombat extends AppCompatActivity {


    private DatabaseReference databaseCombat;
    Monster m1 ;
    Monster m2 ;
    Button attack;
    Button defense ;
    int myturn ;
    int oturn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_combat);


        databaseCombat = FirebaseDatabase.getInstance().getReference();
        databaseCombat = databaseCombat.child("Combat").child("Combat1") ;

     //   DatabaseReference combat  = databaseCombat.child("Combat1") ;
        databaseCombat.addChildEventListener(new ChildEventListener() {
            int i = 0;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Monster m = (Monster) dataSnapshot.getValue(Monster.class);
                if (m.taken==false && m1==null) {
                    m.setTaken(true);
                    m1= m ;
                    databaseCombat.child(Integer.toString(i)).child("taken").setValue(true);
                   TextView text =(TextView) findViewById(R.id.n);
                    text.setText(m1.getNom());
                    DatabaseReference refLife = databaseCombat.child(Integer.toString(i)).child("vie");
                    refLife.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            TextView txt =(TextView) findViewById(R.id.forc);
                            int vie = dataSnapshot.getValue(Integer.class) ;
                            txt.setText(Integer.toString(vie));

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    i=i+1 ;
                    if (m2==null){
                        myturn=0;
                        oturn=1 ;
                    } else {
                        myturn=1;
                        oturn=0 ;
                    }
                } else  {
                    m2=m ;
                    TextView text2 =(TextView) findViewById(R.id.n1);
                    text2.setText(m2.getNom());
                     TextView txt2 =(TextView) findViewById(R.id.cat1);
                    txt2.setText(m2.getCategorie());
                    DatabaseReference refLife = databaseCombat.child(Integer.toString(i)).child("vie");
                    refLife.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            TextView txt =(TextView) findViewById(R.id.forc1);
                            int vie = dataSnapshot.getValue(Integer.class) ;
                            txt.setText(Integer.toString(vie));

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    i=i+1 ;

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
        }) ;

        attack= (Button) findViewById(R.id.attack);
        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int attack = (int) (Math.random() * m1.getAttack());
                m2.setVie(m2.getVie()-attack);
                databaseCombat.child(Integer.toString(oturn)).child("vie").setValue(m2.getVie());
                TextView txt =(TextView) findViewById(R.id.forc1);
                txt.setText(Integer.toString(m2.getVie()));


            }
        });

       defense= (Button) findViewById(R.id.def);
       defense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int def = (int) (Math.random() * m1.getDef());
                m1.setVie(m1.getVie()+def);
                databaseCombat.child(Integer.toString(myturn)).child("vie").setValue(m1.getVie());
                TextView txt =(TextView) findViewById(R.id.forc);
                txt.setText(Integer.toString(m1.getVie()));

            }
        });


    }



}
