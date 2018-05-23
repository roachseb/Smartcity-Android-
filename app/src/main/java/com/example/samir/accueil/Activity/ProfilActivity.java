package com.example.samir.accueil.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samir.accueil.R;
import com.example.samir.accueil.SharedPrefManager;

public class ProfilActivity extends AppCompatActivity {


    private TextView textViewNom, textViewPseudo, textViewEmail, textViewAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        if(!SharedPrefManager.getInstance(this).connecte()){
            finish();
            Intent intent = new Intent(this,MainActivity.class);//Add Stuff
            startActivity(intent);
        }

        textViewNom = findViewById(R.id.TextViewNomPrenom);
        textViewPseudo = findViewById(R.id.TextViewPseudo);
        textViewEmail = findViewById(R.id.TextViewEmail);
        textViewAge = findViewById(R.id.TextViewAge);

        textViewNom.setText(SharedPrefManager.getInstance(this).getNomUtilisateur()+" "+SharedPrefManager.getInstance(this).getPrenomUtilisateur());
        textViewPseudo.setText(SharedPrefManager.getInstance(this).getPseudoUtilisateur());
        textViewEmail.setText(SharedPrefManager.getInstance(this).getEmailUtilisateur());
        textViewAge.setText(String.valueOf(SharedPrefManager.getInstance(this).getAgeUtilisateur())+" ans");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.menuDeconnexion:
                SharedPrefManager.getInstance(this).deconnexion();
                startActivity(new Intent(this, Accueil.class));
                break;

            case R.id.menuOption:
                Toast.makeText(this, "Vous avez cliqué sur les options", Toast.LENGTH_LONG).show();
                break;

            case R.id.menuActualité:
                startActivity(new Intent(this, this.getClass()));
                break;

            case R.id.AjoutReseau:
                startActivity(new Intent(this, AjoutReseau.class));
                break;

            case R.id.RechercheReseau:
                startActivity(new Intent(this, RechercheReseau.class));
                break;
        }

        return true;
    }
}
