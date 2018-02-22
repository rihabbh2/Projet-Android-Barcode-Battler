package com.mbds.deptinfo.barcodebattler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestFireBase extends AppCompatActivity {

    private DatabaseReference databaseMonster;
  //  private FirebaseDatabase;
    private ArrayList<String> monsterList = new ArrayList<>();
    private ListView listViewMonster;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fire_base);

        databaseMonster = FirebaseDatabase.getInstance().getReference();
        databaseMonster = databaseMonster.child("Monster");

        listViewMonster = (ListView) findViewById(R.id.test);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monsterList);
        listViewMonster.setAdapter(arrayAdapter);

        DatabaseReference test  = databaseMonster.child("0") ;


        test.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               TextView txt = (TextView) findViewById(R.id.txt) ;
               // Monster m = (Monster) dataSnapshot.getValue(Monster.class);
               // Toast toast = Toast.makeText(TestFireBase.this.getBaseContext(),dataSnapshot.getValue().toString() , Toast.LENGTH_LONG);
               // toast.show();
                txt.setText(dataSnapshot.getValue().toString());
              //  txt.setText(m.toString());

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

        databaseMonster.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Monster m = (Monster) dataSnapshot.getValue(Monster.class);
                String mstring = m.toString();

                monsterList.add(mstring);

                // Notify the ArrayAdapter that there was a change
                arrayAdapter.notifyDataSetChanged();

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
    }

}
