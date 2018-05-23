package com.example.samir.accueil;

import com.android.volley.RequestQueue;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.toolbox.Volley;
import com.android.volley.Request;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String prefName = "myPref";
    private static final String keyId = "utilisateurId";
    private static final String keyNom = "utilisateurNom";
    private static final String keyPrenom = "utilisateurPrenom";
    private static final String keyPseudo = "utilisateurPseudo";
    private static final String keyEmail = "utilisateurEmail";
    private static final String keyAge = "utilisateurAge";
    private static final String keyIdReseau = "IdReseau";
    private static final String keyAdministrateurReseau = "AdministrateurReseau";
    private static final String keyNomReseau = "NomReseau";
    private static final String keyCoordonneesReseau = "CoordonneesReseau";
    private static final String keyMotCleReseau = "MotCleReseau";
    private static final String keyPublicReseau = "PublicReseau";
    private static final String keyMotCleRecherche = "MotCleRecherche";


    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //UTILISATEUR
    public boolean connexion(int id, String nom, String prenom, String pseudo, String email, int age){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(keyId, id);
        editor.putString(keyNom, nom);
        editor.putString(keyPrenom, prenom);
        editor.putString(keyPseudo, pseudo);
        editor.putString(keyEmail, email);
        editor.putInt(keyAge, age);

        editor.apply();

        return true;
    }

    public boolean connecte(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return (sharedPreferences.getString(keyPseudo, null) != null);
    }

    public boolean deconnexion(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public int getIdUtilisateur(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyId, 0);
    }
    public String getNomUtilisateur(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyNom, null);
    }

    public String getPrenomUtilisateur(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyPrenom, null);
    }

    public String getPseudoUtilisateur(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyPseudo, null);
    }

    public String getEmailUtilisateur(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyEmail, null);
    }

    public int getAgeUtilisateur(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyAge, 0);
    }

    //RESEAUX SOCIAUX
    public boolean rechercheReseau(int id, String administrateur, String nom, String coordonnees, String motCle, int prive){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(keyIdReseau, id);
        editor.putString(keyAdministrateurReseau, administrateur);
        editor.putString(keyNomReseau, nom);
        editor.putString(keyCoordonneesReseau, coordonnees);
        editor.putString(keyMotCleReseau, motCle);
        editor.putInt(keyPublicReseau, prive);

        editor.apply();

        return true;
    }

    public boolean rechercheReseauMotCle(String motCleRecherche){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(keyMotCleRecherche, motCleRecherche);

        editor.apply();

        return true;
    }

    public int getIdReseau(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyIdReseau, 0);
    }

    public String getAdministrateurReseau(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyAdministrateurReseau, null);
    }

    public String getNomReseau(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyNomReseau, null);
    }

    public String getCoordonneesReseau(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyCoordonneesReseau, null);
    }

    public String getMotCleReseau(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyMotCleReseau, null);
    }

    public int getPriveReseau(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyPublicReseau, 0);
    }

    public String getMotCleRecherche(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyMotCleRecherche, null);
    }
}