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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.mbds.deptinfo.barcodebattler.R.id.arme;
import static com.mbds.deptinfo.barcodebattler.R.id.force;

public class ChooseMonster extends AppCompatActivity implements ListAdapter {

    ArrayList<Monster> monstersList ;
    ListView lv ;
    MySQLiteHelper db ;
    Monster m1 ;
    Monster m2 ;
    Button start ;
    ArrayList<Equipement> armes ;
    String arme1 ;
    String arme2 ;
    Spinner spinner ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_monster);
        armes =  new ArrayList<Equipement>();
        armes.add(new Equipement("sabre", 30,10));
        armes.add(new Equipement("bouclier", 0,40));
        armes.add(new Equipement("pistolet", 50,0));
        db = new MySQLiteHelper(getApplicationContext());
        monstersList = db.getMonsters(); //new ArrayList<>() ;
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(this);
        lv.setItemsCanFocus(false);
        Bitmap imageTest = new   BitmapFactory().decodeResource(getResources(), R.drawable.test);
        Bitmap imageTest2 = new   BitmapFactory().decodeResource(getResources(), R.drawable.test2);

        Monster monster1 = new Monster( 1,"Cool Cat","Cat",imageTest,100,34) ;
        Monster monster2 = new Monster(2, "Working Dog","Dog",imageTest2,100,34) ;
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
                i.putExtra("arme1",m1.getArme().getNom());
                i.putExtra("arme2",m2.getArme().getNom());
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
        int  force = monstersList.get(position).attack;
        t.setText(Integer.toString(force));
        final ImageView iv = (ImageView)  returnView.findViewById(R.id.img);
        iv.setImageBitmap(monstersList.get(position).image);
        final Monster monster = new Monster(0,text.toString(),txt.toString(),iv.getDrawingCache(),force,10);
        CheckBox cb = (CheckBox)  returnView.findViewById (R.id.check);
        cb.setTag (position);
        spinner = (Spinner) returnView.findViewById (R.id.spinner);
        ArrayList<String> weapon ;
        weapon = new ArrayList<>();
        for(int i = 0; i < armes.size(); i++) {
            weapon.add(armes.get(i).getNom()) ;
        }
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weapon);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return returnView ;
    }

    public void MyHandler(View v) {
        CheckBox cb = (CheckBox) v;
        int position =   Integer.parseInt(cb.getTag().toString());

        View o = lv.getChildAt(position).findViewById(
                R.id.check);

        if (cb.isChecked()) {
            o.setBackgroundResource(R.color.colorPrimaryDark);
            if (m1==null){
                m1 = (Monster) this.getItem(position);
                arme1 = String.valueOf(spinner.getSelectedItem());
                for(int i = 0; i < armes.size(); i++) {
                    if (arme1 ==armes.get(i).getNom()){
                        m1.setArme(armes.get(i));
                    };
                }
                Toast toast = Toast.makeText(ChooseMonster.this.getBaseContext(), m1.getNom(), Toast.LENGTH_LONG);
                toast.show();

            } else if (m2==null){
                m2 = (Monster) this.getItem(position);
                arme2 = String.valueOf(spinner.getSelectedItem()) ;
                for(int i = 0; i < armes.size(); i++) {
                    if (arme2 ==armes.get(i).getNom()){
                        m2.setArme(armes.get(i));
                    };
                }
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
