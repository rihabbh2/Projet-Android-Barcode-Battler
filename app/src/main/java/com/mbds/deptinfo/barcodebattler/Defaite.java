package com.mbds.deptinfo.barcodebattler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Defaite extends AppCompatActivity {

    private Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaite);

        ImageView imageView = (ImageView) findViewById(R.id.defaite);
        imageView.setImageResource(R.drawable.victory);

        retour = (Button) findViewById(R.id.acceuil);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Defaite.this, Menu.class);
                Defaite.this.startActivity(intent);
            }

        });
    }
}
