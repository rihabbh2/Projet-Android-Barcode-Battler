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
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MONSTER " ;

    public MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_MONSTER = "CREATE TABLE IF NOT EXISTS MONSTER   ( NOM TEXT, CATEGORIE TEXT, IMAGE TEXT, FORCE TEXT); ";
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
        db.execSQL("INSERT INTO MONSTER (NOM, CATEGORIE , FORCE, IMAGE) VALUES ('" +monster.getNom() +"','" +monster.getCategorie()+"','"+Integer.toString(monster.getForceBrute())+"','"+monster.getImgBase64()+"')");
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
        db.execSQL("UPDATE NOM =" +monster.getNom() +", CATEGORIE =" +monster.getCategorie()+", IMAGE ="+monster.getImgBase64()+"FORCE ="+monster.getForceBrute()+ " FROM MONSTER") ;
        db.close();


    }

    public ArrayList<Monster> getMonsters()
    {

        ArrayList<Monster> lp = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT NOM ,CATEGORIE,IMAGE,FORCE FROM MONSTER", null);
        if(c!=null && c.moveToFirst())
        {
            do
            {
                Monster p = new Monster();
                String nom = c.getString(0); // le champ 1
                String categorie = c.getString(1);
                String temp = c.getString(2);
                int force = Integer.parseInt(c.getString(3) );
                byte [] encodeByte= Base64.decode(temp,Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                p.setNom(nom);
                p.setCategorie(categorie);
                p.setImage(image);
                p.setForceBrute(force) ;
                lp.add(p);
            }
            while(c.moveToNext());
        }
        return lp;


    }



}