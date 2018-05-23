package com.example.samir.accueil.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.samir.accueil.Constantes;
import com.example.samir.accueil.R;
import com.example.samir.accueil.RequestHandler;
import com.example.samir.accueil.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RechercheReseau extends AppCompatActivity {

    private EditText editTextNom;
    private Button boutonRechercher;
    private Spinner spinnerMotCleRecherche;
    private String motCleRecherche = "debut";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_reseau);

        editTextNom = findViewById(R.id.NomRecherche);
        spinnerMotCleRecherche = findViewById(R.id.MotCleRecherche);

        List listMotCleRecherche = new ArrayList();
        listMotCleRecherche.add("Alimentation");
        listMotCleRecherche.add("Cinema");
        listMotCleRecherche.add("Culture");
        listMotCleRecherche.add("Economie");
        listMotCleRecherche.add("Humanitaire");
        listMotCleRecherche.add("Informatique");
        listMotCleRecherche.add("Jeux_Video");
        listMotCleRecherche.add("Musique");
        listMotCleRecherche.add("Rencontre");
        listMotCleRecherche.add("Science");
        listMotCleRecherche.add("Shopping");
        listMotCleRecherche.add("Sport");
        listMotCleRecherche.add("Voiture");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listMotCleRecherche);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMotCleRecherche.setAdapter(adapter);
        spinnerMotCleRecherche.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                motCleRecherche = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                motCleRecherche = null;
            }
        });

        boutonRechercher = findViewById(R.id.Rechercher);

        boutonRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!editTextNom.getText().toString().isEmpty()){
                    rechercherReseau();
                }else{
                    SharedPrefManager.getInstance(getApplicationContext()).rechercheReseauMotCle(motCleRecherche);
                    startActivity(new Intent(getApplicationContext(), RechercheReseauMotCle.class));
                }
            }
        });

        progressDialog = new ProgressDialog(this);
    }

    public void rechercherReseau(){

        final String nomR = editTextNom.getText().toString().trim();

        progressDialog.setMessage("Recherche en cours");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlRechercheReseau,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).rechercheReseau(obj.getInt("idReseau"), obj.getString("administrateur"), obj.getString("nom"), obj.getString("coordonnees"), obj.getString("motCle"), obj.getInt("public"));
                                startActivity(new Intent(getApplicationContext(), ProfilReseau.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Nom", nomR);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void displayMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
                finish();
                startActivity(new Intent(this, Accueil.class));
                break;

            case R.id.menuOption:
                Toast.makeText(this, "Vous avez cliqué sur les options", Toast.LENGTH_LONG).show();
                break;

            case R.id.menuActualité:
                finish();
                startActivity(new Intent(this, this.getClass()));
                break;

            case R.id.AjoutReseau:
                finish();
                startActivity(new Intent(this, AjoutReseau.class));
                break;

            case R.id.RechercheReseau:
                finish();
                startActivity(new Intent(this, RechercheReseau.class));
                break;
        }

        return true;
    }
}
