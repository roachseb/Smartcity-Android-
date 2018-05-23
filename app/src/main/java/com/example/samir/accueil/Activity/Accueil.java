package com.example.samir.accueil.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.example.samir.accueil.Constantes;
import com.example.samir.accueil.R;
import com.example.samir.accueil.RequestHandler;
import com.example.samir.accueil.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Accueil extends AppCompatActivity {

    private EditText editTextPseudo, editTextMdp;
    private Button boutonConnexion, boutonPasEncore;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        if(SharedPrefManager.getInstance(this).connecte()){
            finish();
            startActivity(new Intent(this, ProfilActivity.class));
            return;
        }

        editTextPseudo = findViewById(R.id.Pseudo);
        editTextMdp = findViewById(R.id.MDP);

        boutonConnexion = findViewById(R.id.Connexion);
        boutonPasEncore = findViewById(R.id.Inscription);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connexion en cours...");

        boutonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connexion();
            }
        });
        boutonPasEncore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intentInscription = new Intent(Accueil.this, Inscription.class);
                startActivity(intentInscription);
            }
        });
    }

    public void connexion() {

        final String pseudo = editTextPseudo.getText().toString().trim();
        final String mdp = editTextMdp.getText().toString().trim();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlConnexion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).connexion(obj.getInt("id"), obj.getString("nom"), obj.getString("prenom"), obj.getString("pseudo"), obj.getString("email"), obj.getInt("age"));
                                startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                                finish();
                            } else {
                                displayMessage(obj.getString("message"));
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
                        displayMessage(error.getMessage());
                    }
        }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Pseudo", pseudo);
                    params.put("MotDePasse", mdp);

                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

        public void displayMessage(String message){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
}
