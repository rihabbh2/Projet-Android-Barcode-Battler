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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.mbds.deptinfo.barcodebattler.R.id.force;

public class ChooseMonster extends AppCompatActivity implements ListAdapter {

    ArrayList<Monster> monstersList ;
    ListView lv ;
    MySQLiteHelper db ;
    Monster m1 ;
    Monster m2 ;
    Button start ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_monster);
        db = new MySQLiteHelper(getApplicationContext());
        monstersList = db.getMonsters(); //new ArrayList<>() ;
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(this);
        lv.setItemsCanFocus(false);
        Bitmap imageTest = new   BitmapFactory().decodeResource(getResources(), R.drawable.test);
        Monster monster1 = new Monster( "Cool Cat","Cat",imageTest,100) ;
        Monster monster2 = new Monster( "Killer Cat","Cat",imageTest,100) ;
        monstersList.add(monster1);
        monstersList.add(monster2);

        start= (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseMonster.this, LocalCombat.class);
                i.putExtra("nom1", m1.getNom());
                i.putExtra("category1",m1.getCategorie());
                i.putExtra("images1", m1.getImgBase64());
                i.putExtra("nom2", m2.getNom());
                i.putExtra("category2",m2.getCategorie());
                i.putExtra("images2", m2.getImgBase64());
                startActivity(i);
            }

        });
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
        final TextView t =(TextView) returnView.findViewById(force);
        int  force = monstersList.get(position).forceBrute;
        t.setText(Integer.toString(force));
        final ImageView iv = (ImageView)  returnView.findViewById(R.id.img);
        iv.setImageBitmap(monstersList.get(position).image);
        final Monster monster = new Monster(text.toString(),txt.toString(),iv.getDrawingCache(),force);
        CheckBox cb = (CheckBox)  returnView.findViewById (R.id.check);
        cb.setTag (position);
        return returnView ;
    }

    public void MyHandler(View v) {
        CheckBox cb = (CheckBox) v;
        int position =   Integer.parseInt(cb.getTag().toString());

        // On récupère l'élément sur lequel on va changer la couleur
        View o = lv.getChildAt(position).findViewById(
                R.id.check);

        //On change la couleur
        if (cb.isChecked()) {
            o.setBackgroundResource(R.color.colorPrimaryDark);
            if (m1==null){
                m1 = (Monster) this.getItem(position);
                Toast toast = Toast.makeText(ChooseMonster.this.getBaseContext(), m1.getNom(), Toast.LENGTH_LONG);
                toast.show();

            } else if (m2==null){
                m2 = (Monster) this.getItem(position);
            }
        } else {
            o.setBackgroundResource(R.color.colorPrimary);
        }
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
