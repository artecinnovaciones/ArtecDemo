package com.artecinnovaciones.artecdemo.Utilidades;

import android.app.Activity;
import android.view.View;

/**
 * Created by LAP-NIDIA on 20/05/2016.
 */
public class ViewUtil {
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(View view, int resource){
        return ( T ) view.findViewById(resource);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(Activity activity, int resource){
        return ( T ) activity.findViewById(resource);
    }
}
