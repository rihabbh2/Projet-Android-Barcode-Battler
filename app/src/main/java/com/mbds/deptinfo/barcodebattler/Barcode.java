package com.mbds.deptinfo.barcodebattler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Barcode extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView ScannerView;
    MySQLiteHelper db ;
    private DatabaseReference databaseMonster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        databaseMonster = FirebaseDatabase.getInstance().getReference();
        databaseMonster = databaseMonster.child("Monster") ;
        QRCScanner(this.ScannerView) ;
        db = new MySQLiteHelper(getApplicationContext());


    }

    //initialise le scanner
    public  void QRCScanner (View view){
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();

    }

    @Override
    public void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    // Récupération du contenu
    @Override
    public void handleResult(final Result rawResult) {
      //  Log.e("handler", rawResult.getText()); // Prints scan results
       // Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

    /*    if (rawResult.getText().toString().equals("1234567890128") ){
            Bitmap imageTest = new BitmapFactory().decodeResource(getResources(), R.drawable.test);
            Monster monster1 = new Monster( 1,"test","test",imageTest,100,10,100) ;
            db.addMonster(monster1);
            Intent intent = new Intent(Barcode.this, Collection.class);
            Barcode.this.startActivity(intent);
        } else {
            Toast toast = Toast.makeText(Barcode.this, "Code Bar non enregistré dans la BD", Toast.LENGTH_LONG);
            toast.show();
        }*/

       // databaseMonster= databaseMonster.child(rawResult.getText().toString());

        databaseMonster.addChildEventListener(new ChildEventListener() {
            String result = rawResult.getText().toString() ;
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Monster m = (Monster) dataSnapshot.getValue(Monster.class);
                if (Integer.toString(m.id).equals(result)){
                    db.addMonster(m);
                    Intent intent = new Intent(Barcode.this, Collection.class);
                    Barcode.this.startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(Barcode.this, "Code Bar non enregistré dans la BD", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // afficher le résultat dans une dialog box.
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contenu du code");
        builder.setMessage(rawResult.getText());
        AlertDialog alerte = builder.create();
        alerte.show();
        // Pour redémarrer le scanner.
        ScannerView.resumeCameraPreview(this);*/

    }
}