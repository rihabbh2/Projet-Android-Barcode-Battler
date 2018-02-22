package com.mbds.deptinfo.barcodebattler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ChooseMonster2 extends AppCompatActivity implements ListAdapter {

    ArrayList<Monster> monstersList ;
    ListView lv ;
    MySQLiteHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_monster2);



        db = new MySQLiteHelper(getApplicationContext());
        monstersList = db.getMonsters(); //new ArrayList<>() ;
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(this);
        lv.setItemsCanFocus(false);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                    temp = intent.getStringExtra("images");
                }

                Monster item = (Monster) ChooseMonster2.this.getItem(position);
                Intent i = new Intent(ChooseMonster2.this, LocalCombat.class);
                i.putExtra("nom1",name);
                i.putExtra("category1",category);
                i.putExtra("images1",temp);
                i.putExtra("vie1", vie);
                i.putExtra("nom2", item.getNom());
                i.putExtra("category2", item.getCategorie());
                i.putExtra("images2", item.getImgBase64());
                i.putExtra("vie2", Integer.toString(item.getVie()));
              //  i.putExtra("attack", Integer.toString(item.getAttack()));
               // i.putExtra("def", Integer.toString(item.getDef()));
                startActivity(i);
            }
        });
        Bitmap imageTest = new   BitmapFactory().decodeResource(getResources(), R.drawable.test);
        Monster monster1 = new Monster("1","test","test",imageTest,100,10,60) ;
        monstersList.add(monster1);
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
            returnView=  View.inflate(this,R.layout.choose_monster,null);
        }
        else
        {
            returnView=convertView;
        }
        final TextView text =(TextView) returnView.findViewById(R.id.nom);
        text.setText(monstersList.get(position).nom);
        final TextView txt =(TextView) returnView.findViewById(R.id.prenom);
        txt.setText(monstersList.get(position).categorie);


        final ImageView iv = (ImageView)  returnView.findViewById(R.id.img);
        iv.setImageBitmap(monstersList.get(position).image);

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
