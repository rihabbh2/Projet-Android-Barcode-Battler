package com.mbds.deptinfo.barcodebattler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Rihab on 11/11/17.
 */

public class Monster implements Parcelable  {

    String id ;
    String nom;
    String categorie;
    Bitmap image ;
    String  imgBase64 ;
    //Equipement arme ;
    int attack ;
    int def ;
    int vie ;
  //  public boolean taken ;

   // int forceTotale ;
    public static final Parcelable.Creator<Monster> CREATOR = new Parcelable.Creator<Monster>()
    {
        @Override
        public Monster createFromParcel(Parcel source)
        {
            return new Monster(source);
        }

        @Override
        public Monster[] newArray(int size)
        {
            return new Monster[size];
        }
    };

    public Monster(String id ,String nom, String categorie, Bitmap image, int attack , int def, int vie) {
        this.id = id ;
        this.nom = nom;
        this.categorie = categorie;
        this.image = image;
        this.attack = attack ;
        this.def = def ;
        this.vie = vie ;
    }

    public Monster(String id ,String nom, String categorie,String imgBase64, int attack , int def, int vie) {
        this.id = id ;
        this.nom = nom;
        this.categorie = categorie;
        this.imgBase64 = imgBase64 ;
        this.attack = attack ;
        this.def = def ;
        this.vie = vie ;
    }
    public Monster(String id ,String nom, String categorie, int attack , int def, int vie) {
        this.id = id ;
        this.nom = nom;
        this.categorie = categorie;
        this.attack = attack ;
        this.def = def ;
        this.vie = vie ;
    }


    public Monster(Parcel in)
    {

        String[] data = new String[5];
        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = data[0] ; this.nom = data[1]; this.categorie = data[2] ; this.imgBase64= data[3]; this.attack= Integer.parseInt(data[4]); this.def = Integer.parseInt(data[5]);
        if(imgBase64.length()>0)
        {
            byte [] encodeByte= Base64.decode(imgBase64,Base64.DEFAULT);
            this.image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }

    }

    public Monster()
    {

    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImgBase64() {
        if(image==null)
            return "";
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        this.image.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        this.imgBase64= Base64.encodeToString(b, Base64.DEFAULT);
        return imgBase64 ;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

  //  public boolean isTaken() {
//        return taken;
  //  }

    //public void setTaken(boolean taken) {
        //this.taken = taken;
    //}

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        if(image!=null)
        {
            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            imgBase64= Base64.encodeToString(b, Base64.DEFAULT);
        }
        else {imgBase64="";}
        String attack =   Integer.toString(this.attack) ;
        String def =   Integer.toString(this.def) ;
        String[] monster = {id,nom, categorie,imgBase64,attack,def} ;
        dest.writeStringArray(monster);
    }


   // public Equipement getArme() {
     //   return arme;
    //}

    //public void setArme(Equipement arme) {
      //  this.arme = arme;
    //}

    @Override
    public String toString() {
        return this.nom ;
    }
}
