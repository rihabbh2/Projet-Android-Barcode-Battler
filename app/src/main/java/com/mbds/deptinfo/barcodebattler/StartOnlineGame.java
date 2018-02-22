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

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wInfo = wifiManager.getConnectionInfo();
                String macAddress = wInfo.getMacAddress();

                item.setImage(null);
                databaseCombat.child(macAddress).child("0").setValue(item);
                databaseCombat.child(macAddress).child("0").child("imgBase64").setValue(item.imgBase64);

                Intent i = new Intent(StartOnlineGame.this,NetworkCombat.class);
                i.putExtra("id",(item.getId()));
                i.putExtra("turn","0");
                i.putExtra("mac", macAddress);
                i.putExtra("image", item.getImgBase64());

                startActivity(i);
            }
        });
      //  Bitmap imageTest = new BitmapFactory().decodeResource(getResources(), R.drawable.test);
        Monster monster1 = new Monster( "1234567890128","test","test","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",100,10,100) ;
        monstersList.add(monster1);
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


