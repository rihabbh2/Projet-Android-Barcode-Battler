package com.mbds.deptinfo.barcodebattler;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Barcode extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView ScannerView;
    MySQLiteHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
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
    public void handleResult(Result rawResult) {
        //Récupérer le résultat du scanner ici
        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        if (rawResult.getText().toString().equals("1234567890128") ){
            Bitmap imageTest = new BitmapFactory().decodeResource(getResources(), R.drawable.test);
            Monster monster1 = new Monster( "test","test",imageTest,100) ;
            db.addMonster(monster1);
            Toast toast = Toast.makeText(Barcode.this, "Monstre crée", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(Barcode.this, "Code Bar non enregistré dans la BD", Toast.LENGTH_LONG);
            toast.show();
        }
        // afficher le résultat dans une dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contenu du code");
        builder.setMessage(rawResult.getText());
        AlertDialog alerte = builder.create();
        alerte.show();
        // Pour redémarrer le scanner.
        ScannerView.resumeCameraPreview(this);

    }
}