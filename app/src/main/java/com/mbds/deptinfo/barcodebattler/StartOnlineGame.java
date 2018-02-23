package com.mbds.deptinfo.barcodebattler;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.mbds.deptinfo.barcodebattler.R.id.force;

public class StartOnlineGame extends AppCompatActivity implements ListAdapter {


    ArrayList<Monster> monstersList ;
    ListView lv ;
    MySQLiteHelper db ;
    private DatabaseReference databaseCombat;
    int gameId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster_online);
        db = new MySQLiteHelper(getApplicationContext());
        monstersList = db.getMonsters(); //new ArrayList<>() ;
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(this);
        lv.setItemsCanFocus(false);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Monster item = (Monster) StartOnlineGame.this.getItem(position);
                databaseCombat = FirebaseDatabase.getInstance().getReference();
                databaseCombat = databaseCombat.child("Combat") ;
                item.setImage(null);

                gameId = (int) (Math.random()*10000);
                String mac = Integer.toString(gameId)+"id" ;

                databaseCombat.child(mac).child("0").setValue(item);
                databaseCombat.child(mac).child("0").child("imgBase64").setValue(item.imgBase64);

                Intent i = new Intent(StartOnlineGame.this,NetworkCombat.class);
                i.putExtra("id",(item.getId()));
                i.putExtra("turn","0");
                i.putExtra("mac", mac);
                i.putExtra("image", item.getImgBase64());

                startActivity(i);
            }
        });

    //    monstersList.add(monster1);
        //monstersList.add(monster1);
    }

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
        return monstersList.size();
    }

    @Override
    public Object getItem(int position) {
        return monstersList.get(position);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View returnView;
        if (convertView==null)
        {
            returnView=  View.inflate(this,R.layout.network_racine,null);
        }
        else
        {
            returnView=convertView;
        }
        final TextView text =(TextView) returnView.findViewById(R.id.nom);
        text.setText(monstersList.get(position).nom);
        final TextView txt =(TextView) returnView.findViewById(R.id.prenom);
        txt.setText(monstersList.get(position).categorie);
        final TextView t =(TextView) returnView.findViewById(force);
        int  vie = monstersList.get(position).vie;
        t.setText(Integer.toString(vie));

        final ImageView iv = (ImageView)  returnView.findViewById(R.id.img);
        iv.setImageBitmap(monstersList.get(position).image);
        //       final Monster p = new Monster(text.toString(),txt.toString(),iv.getDrawingCache(),force);

        return returnView ;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        if (monstersList.size()==0){
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            if(requestCode == 42)
            {
                lv.invalidateViews();//refresh

            }


        }
    }
}


