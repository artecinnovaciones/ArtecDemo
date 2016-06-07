package com.artecinnovaciones.artecdemo.Utilidades;

import android.content.Context;
import android.media.MediaPlayer;

import com.artecinnovaciones.artecdemo.R;

/**
 * Created by LAP-NIDIA on 01/06/2016.
 */
public class BotonClick {

    public MediaPlayer clic;

    public void sonido(Context cont){
        clic = MediaPlayer.create(cont, R.raw.click);
        clic.start();
    }
}
