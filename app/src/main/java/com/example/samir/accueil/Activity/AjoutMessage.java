package com.example.samir.accueil.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;
import android.content.Intent;

public class AjoutMessage extends AppCompatActivity {

    private EditText editTextMessage;
    private Button boutonEnvoyerMessage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_message);

        editTextMessage = findViewById(R.id.Message);
        boutonEnvoyerMessage = findViewById(R.id.BoutonEnvoyerMessage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Envoie du message...");

        boutonEnvoyerMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nouveauMessage();
            }
        });
    }

    public void nouveauMessage(){
        final int auteur = SharedPrefManager.getInstance(this).getIdUtilisateur();
        final int destinataire = SharedPrefManager.getInstance(this).getIdReseau();
        final String contenu = editTextMessage.getText().toString().trim();
        final int etat = 1;

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlAjoutMessage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), ProfilReseau.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IdAuteur", String.valueOf(auteur));
                params.put("IdDestinataire", String.valueOf(destinataire));
                params.put("Contenu", contenu);
                params.put("Public", String.valueOf(etat));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
