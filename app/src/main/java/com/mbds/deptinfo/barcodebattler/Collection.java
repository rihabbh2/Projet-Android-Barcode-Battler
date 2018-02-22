package com.mbds.deptinfo.barcodebattler;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static com.mbds.deptinfo.barcodebattler.R.id.force;

public class Collection extends AppCompatActivity implements ListAdapter {

    ArrayList<Monster> monstersList ;
    ListView lv ;
    MySQLiteHelper db ;
    Button delete ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        db = new MySQLiteHelper(getApplicationContext());
        monstersList = db.getMonsters(); //new ArrayList<>() ;
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(this);
        lv.setItemsCanFocus(false);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Monster item = (Monster) Collection.this.getItem(position);
                Intent i = new Intent(Collection.this, Details.class);
                i.putExtra("nom", item.getNom());
                i.putExtra("category", item.getCategorie());
                i.putExtra("images", item.getImgBase64());
                i.putExtra("vie", Integer.toString(item.getVie()));
                i.putExtra("attack", Integer.toString(item.getAttack()));
                i.putExtra("def", Integer.toString(item.getDef()));
                startActivity(i);
            }
        });
        Bitmap imageTest = new   BitmapFactory().decodeResource(getResources(), R.drawable.test);
        Monster monster1 = new Monster( "1","test","test",imageTest,100,10,60) ;
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
            returnView=  View.inflate(this,R.layout.racine,null);
        }
        else
        {
            returnView=convertView;
        }
        final TextView text =(TextView) returnView.findViewById(R.id.nom);
        text.setText(monstersList.get(position).nom);
        final TextView txt =(TextView) returnView.findViewById(R.id.prenom);
        txt.setText(monstersList.get(position).categorie);


        delete = (Button) returnView.findViewById(R.id.del);
        final ImageView iv = (ImageView)  returnView.findViewById(R.id.img);
        iv.setImageBitmap(monstersList.get(position).image);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteMonster(monstersList.get(position));
                monstersList.remove(position);
                lv.invalidateViews();//refresh
            }

        });
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
