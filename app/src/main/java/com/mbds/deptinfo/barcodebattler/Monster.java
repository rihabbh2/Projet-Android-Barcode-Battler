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

    String nom;
    String categorie;
    Bitmap image ;
    String  imgBase64 ;
    Equipement arme ;
    int forceBrute;
    int cap_attack ;
    int cap_def ;

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

    public Monster(String nom, String categorie, Bitmap image, int forceBrute) {
        this.nom = nom;
        this.categorie = categorie;
        this.image = image;
        this.forceBrute = forceBrute;
    }

    public Monster(Parcel in)
    {

        String[] data = new String[3];
        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.nom = data[0]; this.categorie = data[1] ; this.imgBase64= data[2]; this.forceBrute= Integer.parseInt(data[3]);
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

    public int getForceBrute() {
        return forceBrute;
    }

    public void setForceBrute(int forceBrute) {
        this.forceBrute = forceBrute;
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
        String forcebrute =   Integer.toString(forceBrute) ;
        String[] monster = {nom, categorie,imgBase64,forcebrute} ;
        dest.writeStringArray(monster);
    }


    public Equipement getArme() {
        return arme;
    }

    public void setArme(Equipement arme) {
        this.arme = arme;
    }

    public int getCap_attack() {
        return cap_attack;
    }

    public void setCap_attack(int cap_attack) {
        this.cap_attack = cap_attack;
    }

    public int getCap_def() {
        return cap_def;
    }

    public void setCap_def(int cap_def) {
        this.cap_def = cap_def;
    }
}
