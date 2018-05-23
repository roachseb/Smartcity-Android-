package com.example.samir.accueil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

public class Accueil extends AppCompatActivity {


    private Button pasEncore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        pasEncore = findViewById(R.id.Inscription);
        pasEncore.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent intentInscription = new Intent(Accueil.this, Inscription.class);
                startActivity(intentInscription);
            }
        });
    }

}
