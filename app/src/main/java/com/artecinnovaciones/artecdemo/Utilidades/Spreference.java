package com.artecinnovaciones.artecdemo.Utilidades;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LAP-NIDIA on 03/06/2016.
 */
public class Spreference {

    public Spreference(Context context) {
        this.context = context;
        loadPreferences();
    }

    public boolean esprimeravezquesale() {
        return mLogPrefs.getBoolean(PREFS_UPDATE, true);
    }

    public void guardarvezquesale() {
        SharedPreferences.Editor editor = mLogPrefs.edit();
        editor.putBoolean(PREFS_UPDATE, false);
        editor.commit();
    }

    public int obtenerultimoId() {
        return mLogPrefs.getInt(PREFS_ULTIMOID, 0);
    }

    public void guardarUltimoId(int size) {
        SharedPreferences.Editor editor = mLogPrefs.edit();
        editor.putInt(PREFS_ULTIMOID, size);
        editor.commit();
    }

    public int obtenersize() {
        return mLogPrefs.getInt(PREFS_SIZE, 0);
    }

    public void guardarsize(int size, boolean flag) {
        SharedPreferences.Editor editor = mLogPrefs.edit();
        editor.putInt(PREFS_SIZE, size);
        editor.commit();
        guardarhaynoticiasnuevas(flag);
    }

    public void guardarhaynoticiasnuevas(boolean flag) {
        SharedPreferences.Editor editor = mLogPrefs.edit();
        editor.putBoolean(PREFS_FLAG, flag);
        editor.commit();
    }

    public boolean haynoticiasnuevas() {
        return mLogPrefs.getBoolean(PREFS_FLAG, false);
    }
    private void loadPreferences() {
        mLogPrefs = context.getSharedPreferences(PREFS_UPDATE, Context.MODE_PRIVATE);
    }

    private android.content.SharedPreferences mLogPrefs;
    private Context context;
    private static final String PREFS_UPDATE = "registrarCalificacion";
    private static final String PREFS_ULTIMOID = "ultimoId";
    private static final String PREFS_SIZE = "size";
    private static final String PREFS_FLAG = "haynoticiasnuevas";
}