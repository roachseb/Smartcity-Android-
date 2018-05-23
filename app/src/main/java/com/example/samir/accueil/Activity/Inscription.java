package com.example.samir.accueil.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.example.samir.accueil.Constantes;
import com.example.samir.accueil.R;
import com.example.samir.accueil.RequestHandler;
import com.example.samir.accueil.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Inscription extends AppCompatActivity {

    private EditText editTextNom, editTextPrenom, editTextPseudo, editTextEmail, editTextAge, editTextMdp;
    private Button boutonInscription;
    private ProgressDialog progressDialog;
    private Button boutonDejaInscrit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        if(SharedPrefManager.getInstance(this).connecte()){
            finish();
            startActivity(new Intent(this, ProfilActivity.class));
            return;
        }

        editTextNom = findViewById(R.id.Nom);
        editTextPrenom = findViewById(R.id.Prenom);
        editTextPseudo = findViewById(R.id.Pseudo);
        editTextEmail = findViewById(R.id.E_mail);
        editTextAge = findViewById(R.id.Age);
        editTextMdp = findViewById(R.id.MDP);

        boutonInscription = findViewById(R.id.Valider);
        boutonDejaInscrit = findViewById(R.id.DejaInscrit);

        progressDialog = new ProgressDialog(this);

        boutonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inscription();
            }
        });

        boutonDejaInscrit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intentconnexion = new Intent(Inscription.this, Accueil.class);
                startActivity(Intentconnexion);
            }
        });
    }

    public void inscription(){

        final String nom = editTextNom.getText().toString().trim();
        final String prenom = editTextPrenom.getText().toString().trim();
        final String pseudo = editTextPseudo.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final int age = Integer.parseInt(editTextAge.getText().toString());
        final String mdp = editTextMdp.getText().toString().trim();

        progressDialog.setMessage("Inscription en cours...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlInscription,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String reponse) {
                        progressDialog.dismiss();

                        try{
                            JSONObject jsonObject = new JSONObject(reponse);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Nom", nom);
                params.put("Prenom", prenom);
                params.put("Pseudo", pseudo);
                params.put("Email", email);
                params.put("Age", String.valueOf(age));
                params.put("MotDePasse", mdp);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
