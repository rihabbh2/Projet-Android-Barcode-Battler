package com.mbds.deptinfo.barcodebattler;

import android.graphics.Bitmap;

/**
 * Created by deptinfo on 05/02/18.
 */

public class Equipement {

    String nom ;
    int cap_attack ;
    int cap_def ;

    public Equipement(String nom, int cap_attack, int cap_def) {
        this.nom = nom;
        this.cap_attack = cap_attack;
        this.cap_def = cap_def;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
