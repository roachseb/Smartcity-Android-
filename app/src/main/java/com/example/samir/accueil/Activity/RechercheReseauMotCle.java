package com.example.samir.accueil.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.samir.accueil.Activity.ProfilReseau;
import com.example.samir.accueil.Constantes;
import com.example.samir.accueil.R;
import com.example.samir.accueil.RequestHandler;
import com.example.samir.accueil.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.util.Log;

import android.widget.Button;

public class RechercheReseauMotCle extends AppCompatActivity {

    private LinearLayout linearLayout;
    private String motCle;
    JSONObject obj;
    String si;
    Button bouton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_reseau_mot_cle);

        this.linearLayout = findViewById(R.id.LayoutRechercheReseauMotCle);
        this.motCle = SharedPrefManager.getInstance(this).getMotCleRecherche();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlRechercheReseau,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            int nbrTuple = Integer.parseInt(obj.getString("nbrTuple"));
                            String error = obj.getJSONObject("0").getString("error");

                            for (int i = 0;i<nbrTuple;i++) {
                                si = String.valueOf(i);

                                if (!obj.getJSONObject(si).getBoolean("error")) {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1000, 100);
                                    params.setMargins(50, 20, 0, 0);
                                    bouton = new Button(getApplicationContext());
                                    bouton.setId(i);
                                    bouton.setLayoutParams(params);
                                    bouton.setText(obj.getJSONObject(si).getString("nom"));
                                    bouton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            try{
                                                afficherReseau(obj.getJSONObject(String.valueOf(bouton.getId())).getInt("idReseau"), obj.getJSONObject(String.valueOf(bouton.getId())).getString("administrateur"), obj.getJSONObject(String.valueOf(bouton.getId())).getString("nom"), obj.getJSONObject(String.valueOf(bouton.getId())).getString("coordonnees"), obj.getJSONObject(String.valueOf(bouton.getId())).getString("motCle"), obj.getJSONObject(String.valueOf(bouton.getId())).getInt("public"));
                                            }catch (JSONException e) {
                                                Log.e("MYAPP", "unexpected JSON exception", e);
                                            }

                                        }
                                    });
                                    if(i%2 == 0){
                                        bouton.setBackgroundResource(R.color.agebg);
                                    } else {
                                        bouton.setBackgroundResource(R.color.pseudobg);
                                    }
                                    if(obj.getJSONObject(si).getInt("public") == 1){
                                        linearLayout.addView(bouton);
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "dvgjhvdd", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "ddd", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MotCle", motCle);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void afficherReseau(int id, String admin, String nom, String coord, String MotCle, int pub){

        SharedPrefManager.getInstance(getApplicationContext()).rechercheReseau(id, admin, nom, coord, MotCle, pub);
        startActivity(new Intent(getApplicationContext(), ProfilReseau.class));
        finish();


    }
}
