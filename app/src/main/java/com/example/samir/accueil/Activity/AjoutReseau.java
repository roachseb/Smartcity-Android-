package com.example.samir.accueil.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Spinner;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class AjoutReseau extends AppCompatActivity {

    private String motCle = "debut";
    private int reseauPublic = 1 , idUser = SharedPrefManager.getInstance(this).getIdUtilisateur();
    private EditText editTextNom, editTextCoordonnees;
    private Switch switchPrive;
    private Spinner spinnerMotCle;
    private Button boutonValider;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_reseau);

        editTextNom = findViewById(R.id.NomReseau);
        editTextCoordonnees = findViewById(R.id.Coordonnees);
        switchPrive = findViewById(R.id.Prive);
        spinnerMotCle = findViewById(R.id.MotCle);
        boutonValider = findViewById(R.id.ValiderReseau);
        progressDialog = new ProgressDialog(this);

        List listMotCle = new ArrayList();
        listMotCle.add("Alimentation");
        listMotCle.add("Cinema");
        listMotCle.add("Culture");
        listMotCle.add("Economie");
        listMotCle.add("Humanitaire");
        listMotCle.add("Informatique");
        listMotCle.add("Jeux_Video");
        listMotCle.add("Musique");
        listMotCle.add("Rencontre");
        listMotCle.add("Science");
        listMotCle.add("Shopping");
        listMotCle.add("Sport");
        listMotCle.add("Voiture");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listMotCle);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMotCle.setAdapter(adapter);
        spinnerMotCle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                motCle = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                motCle = null;
            }
        });

        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutReseau();
            }
        });

    }

    public void ajoutReseau(){

        final String nomR = editTextNom.getText().toString().trim();
        final String coordonnees = editTextCoordonnees.getText().toString().trim();
        final String motCleFinal = motCle;
        final int rpublic = reseauPublic;
        final int idU = idUser;


        progressDialog.setMessage("Création du réseau en cours...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlAjoutReseau,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            displayMessage(obj.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        displayMessage("erreur");
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Administrateur", String.valueOf(idU));
                params.put("Nom", nomR);
                params.put("Coordonnees", coordonnees);
                params.put("MotCle", motCleFinal);
                params.put("Public", String.valueOf(rpublic));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onChecked(View view){
        view = this.getWindow().getDecorView();

        if(switchPrive.isChecked()){
            reseauPublic = 0;
        }else{
            reseauPublic = 1;
        }
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

            case R.id.RechercheReseau:
                finish();
                startActivity(new Intent(this, RechercheReseau.class));
        }

        return true;
    }

    public void displayMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
