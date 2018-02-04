package com.mbds.deptinfo.barcodebattler;


import android.bluetooth.BluetoothDevice;

/**
 * Created by deptinfo on 26/10/17.
 */

public class Appareil {

    String nomBluetooth ;
    String machine ;
    BluetoothDevice bd ;

    public  Appareil(){

    }
    public Appareil(String nomBluetooth, String machine) {
        this.nomBluetooth = nomBluetooth;
        this.machine = machine;
    }

    public Appareil(String nomBluetooth, String machine, BluetoothDevice bd) {
        this.nomBluetooth = nomBluetooth;
        this.machine = machine;
        this.bd = bd;
    }

    public BluetoothDevice getBd() {
        return bd;
    }

    public void setBd(BluetoothDevice bd){
        this.bd = bd ;
    }
    public String getNomBluetooth() {
        return nomBluetooth;
    }

    public String getMachine() {
        return machine;
    }

    public void setNomBluetooth(String nomBluetooth) {
        this.nomBluetooth = nomBluetooth;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }
}