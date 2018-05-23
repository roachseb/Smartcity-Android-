package com.example.samir.accueil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

public class Inscription extends AppCompatActivity {

    private Button dejaInscrit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        /*dejaInscrit.findViewById(R.id.DejaInscrit);
        dejaInscrit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent connecter = new Intent(Inscription.this, Accueil.class);
                startActivity(connecter);
            }
        });*/
    }
}
