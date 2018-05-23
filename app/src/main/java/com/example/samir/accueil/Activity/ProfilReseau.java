package com.example.samir.accueil.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class ProfilReseau extends AppCompatActivity {


    private TextView textViewNomR, textViewCoordonees, textViewMotCle;
    private Button boutonMessage, boutonAbonner;
    private ProgressDialog progressDialog;
    JSONObject obj;
    String si;
    private final int idDestinataire = SharedPrefManager.getInstance(this).getIdReseau();
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_reseau);

        textViewNomR = findViewById(R.id.TextViewNomR);
        textViewCoordonees = findViewById(R.id.TextViewCoordonnees);
        //textViewMotCle = findViewById(R.id.TextViewMotCle);

        textViewNomR.setText(SharedPrefManager.getInstance(this).getNomReseau());
        textViewCoordonees.setText(SharedPrefManager.getInstance(this).getCoordonneesReseau());
        //textViewMotCle.setText(SharedPrefManager.getInstance(this).getMotCleReseau());

        boutonAbonner = findViewById(R.id.BoutonAbonner);
        boutonMessage = findViewById(R.id.BoutonMessage);

        boutonMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfilReseau.this, AjoutMessage.class));
            }
        });

        boutonAbonner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Sabonner();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Abonnement en cours...");

        this.linearLayout = findViewById(R.id.linearLayout2);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlAfficheMessage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            obj = new JSONObject(response);
                            int nbrTuple = Integer.parseInt(obj.getString("nbrTuple"));

                            for (int i = 0;i<nbrTuple;i++) {
                                si = String.valueOf(i);

                                if (!obj.getJSONObject(si).getBoolean("error")) {

                                    LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
                                    TextView pseudo = new TextView(getApplicationContext());
                                    TextView contenu = new TextView(getApplicationContext());
                                    View vu = new View(getApplicationContext());

                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    LinearLayout.LayoutParams paramPseudo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    LinearLayout.LayoutParams paramContenu = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    LinearLayout.LayoutParams paramVu = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);

                                    linearLayout1.setOrientation(LinearLayout.VERTICAL);
                                    linearLayout1.setLayoutParams(layoutParams);
                                    linearLayout.addView(linearLayout1);

                                    pseudo.setLayoutParams(paramPseudo);
                                    pseudo.setText(obj.getJSONObject(si).getString("idAuteur"));
                                    pseudo.setTextColor(Color.parseColor("#49BEFF"));
                                    pseudo.setTextSize(13);

                                    contenu.setLayoutParams(paramContenu);
                                    contenu.setText(obj.getJSONObject(si).getString("contenu"));
                                    pseudo.setTextSize(18);

                                    paramVu.setMargins(0,15,0,0);
                                    vu.setLayoutParams(paramVu);
                                    vu.setBackgroundResource(R.color.grey);

                                    linearLayout1.addView(pseudo);
                                    linearLayout1.addView(contenu);
                                    linearLayout1.addView(vu);

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
                params.put("IdDestinataire", String.valueOf(idDestinataire));

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



    }

    public void Sabonner(){
        final int utilisateur = SharedPrefManager.getInstance(this).getIdUtilisateur();
        final int reseau = SharedPrefManager.getInstance(this).getIdReseau();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlAbonnement,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                boutonAbonner.setText("Se desabonner");
                                boutonAbonner.setBackgroundResource(R.drawable.desabonnement);
                                boutonAbonner.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        desabonner();
                                    }
                                });                            } else {
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
                params.put("IdAbonne", String.valueOf(utilisateur));
                params.put("IdAbonnement", String.valueOf(reseau));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void desabonner(){
        final int utilisateur = SharedPrefManager.getInstance(this).getIdUtilisateur();
        final int reseau = SharedPrefManager.getInstance(this).getIdReseau();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.UrlDesabonnement,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                boutonAbonner.setText("S'abonner");
                                boutonAbonner.setBackgroundResource(R.drawable.abonnement);
                                boutonAbonner.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Sabonner();
                                    }
                                });
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
                params.put("IdAbonne", String.valueOf(utilisateur));
                params.put("IdAbonnement", String.valueOf(reseau));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
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
