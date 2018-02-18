package com.mbds.deptinfo.barcodebattler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinOnlineGame extends AppCompatActivity {

    EditText inputMac;
    Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_online_game);

        inputMac = (EditText) findViewById(R.id.mac);
        valider = (Button) findViewById(R.id.valider) ;


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mac = inputMac.getText().toString();
                Intent i = new Intent(JoinOnlineGame.this, SelectMonster.class);
                i.putExtra("mac", mac);
                startActivity(i);

            }
        });
    }
}
