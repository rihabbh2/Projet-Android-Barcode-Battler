package com.mbds.deptinfo.barcodebattler;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;

/**
 * Created by Rihab on 11/11/17.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "MONSTER " ;

    public MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_MONSTER = "CREATE TABLE IF NOT EXISTS MONSTER (ID TEXT, NOM TEXT, CATEGORIE TEXT, IMAGE TEXT,VIE TEXT,ATTACK TEXT,DEF TEXT); ";
        db.execSQL(CREATE_MONSTER );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //vide
    }

    public void addMonster(Monster monster)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO MONSTER (ID,NOM, CATEGORIE , VIE, IMAGE, ATTACK, DEF) VALUES ('" +monster.getId() +"','"+monster.getNom() +"','" +monster.getCategorie()+"','"+Integer.toString(monster.getVie())+"','"+monster.imgBase64+"','"+monster.getAttack()+"','"+monster.getDef()+"')");
        db.close();
    }

    public void deleteMonster(Monster monster)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("MONSTER","NOM=? and CATEGORIE=? and IMAGE=?" ,new String[]{monster.getNom() ,monster.getCategorie(),monster.getImgBase64()});
       // db.execSQL("DELETE FROM MONSTER WHERE  NOM =" +monster.getNom() +" AND CATEGORIE =" +monster.getCategorie()+" AND IMAGE = "+monster.getImgBase64()+" AND FORCE = "+monster.getForceBrute());
        db.close();
    }
    public void UpdateMonster(Monster monster)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  ID =" +monster.getId() +"NOM =" +monster.getNom() +", CATEGORIE =" +monster.getCategorie()+", IMAGE ="+monster.getImgBase64()+", VIE ="+monster.getVie()+", ATTACK ="+monster.getAttack()+", DEF ="+monster.getDef()+ " FROM MONSTER") ;
        db.close();


    }

    public ArrayList<Monster> getMonsters()
    {

        ArrayList<Monster> lp = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ID, NOM ,CATEGORIE,IMAGE,VIE, ATTACK,DEF  FROM MONSTER", null);
        if(c!=null && c.moveToFirst())
        {
            do
            {
                Monster p = new Monster();
                String id = c.getString(0);
                String nom = c.getString(1); // le champ 1
                String categorie = c.getString(2);
                String temp = c.getString(3);
                p.imgBase64=temp ;
                int vie = Integer.parseInt(c.getString(4) );
                int attack = Integer.parseInt(c.getString(5) );
                int def = Integer.parseInt(c.getString(6) );
                byte [] encodeByte= Base64.decode(temp,Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                p.setId(id);
                p.setNom(nom);
                p.setCategorie(categorie);
                p.setImage(image);
                p.setVie(vie);
                p.setAttack(attack);
                p.setDef(def);
                lp.add(p);
            }
            while(c.moveToNext());
        }
        return lp;


    }



}
