package com.artecinnovaciones.artecdemo.Activitys;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.artecinnovaciones.artecdemo.Conexion.Post;
import com.artecinnovaciones.artecdemo.Fragments.FavoritosFragment;
import com.artecinnovaciones.artecdemo.Fragments.PrincipalFragment;
import com.artecinnovaciones.artecdemo.Fragments.RecyclerFragment;
import com.artecinnovaciones.artecdemo.R;
import com.artecinnovaciones.artecdemo.Utilidades.Spreference;
import com.artecinnovaciones.artecdemo.Utilidades.Utils;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {

    private FABToolbarLayout morph;
    FloatingActionButton fab;
    String fabAC = "cerrado";
    MenuInflater inflater;
    Menu menuC;
    MenuItem item;
    LayerDrawable icon;
    int a = 0;


    Dialog customDialog = null;
    ImageButton aF,aC;

 /*   private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private GoogleCloudMessaging gcm = null;

    private String SENDER_ID = "58800470060";
    private String regid; */

    public Activity activity() {

        return PrincipalActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //       if( checkPlayServices(this) == true ){
        //getReigstrationId(this);
        aceptarN(this);
        //     }
        PrincipalFragment PrinFrag = new PrincipalFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.frag_l, PrinFrag).commit();

        fab = (FloatingActionButton) findViewById(R.id.fabtoolbar_fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        View uno, dos, tres, cuatro;

        uno = findViewById(R.id.home);
        dos = findViewById(R.id.favorito);
        tres = findViewById(R.id.buscar);
        cuatro = findViewById(R.id.pedidos);

        fab.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabtoolbar_fab:
                mostrar();
                break;
            case R.id.home:
                principal();
                cerrar();
                break;
            case R.id.favorito:
                favoritos();
                cerrar();
                break;
            case R.id.buscar:
                a += 1;
                notificacionMenu(a);
                //lista();
                //startActivity(new Intent(getApplicationContext(),ImagenesActivity.class));
                cerrar();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuC = menu;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menuC);
       /* item = menuC.findItem(R.id.action_shop);
        icon = (LayerDrawable) item.getIcon();
        Utils.setBadgeCount(PrincipalActivity.this, icon, 3)*/
        ;
        return true;
    }

    void notificacionMenu(int i) {
        //if (menuC != null) menuC.clear();
        inflater.inflate(R.menu.main, menuC);
        item = menuC.findItem(R.id.action_shop);
        icon = (LayerDrawable) item.getIcon();
        Utils.setBadgeCount(PrincipalActivity.this, icon, i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_shop:
                //showSnackBar("Añadir a contactos");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onBackPressed() {
        if (fabAC == "abierto") {
            cerrar();
        } else if (fabAC == "cerrado" && getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public void onFragmentInteraction(Uri uri) {

    }*/

    public void favoritos() {
        //FragmentManager fgMg = getFragmentManager();
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        FavoritosFragment FavFrag = new FavoritosFragment();
        //getFragmentManager().beginTransaction()
        //.replace(R.id.frag_l, FavFrag).commit();
        trans.replace(R.id.frag_l, FavFrag);
        trans.addToBackStack(null);
        trans.commit();
    }

    public void principal() {
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        PrincipalFragment PrinFrag = new PrincipalFragment();
        trans.replace(R.id.frag_l, PrinFrag);
        trans.addToBackStack(null);
        trans.commit();
    }

    public void lista() {
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        RecyclerFragment LisFrag = new RecyclerFragment();
        trans.replace(R.id.frag_l, LisFrag);
        trans.addToBackStack(null);
        trans.commit();
    }

    public void mostrar() {
        morph.show();
        fabAC = "abierto";
    }

    public void cerrar() {
        morph.hide();
        fabAC = "cerrado";
    }

    /////////////////////////////////////////////////////////////////
    // NOYIFICACIONES
    ////////////////////////////////////////////////////////////////

    public void aceptarN (final Context cont){
        boolean Spreferencem = new Spreference(getApplicationContext()).esprimeravezquesale();

        if (Spreferencem) {

            customDialog = new Dialog(this, android.R.style.Theme_Holo_Light_Panel);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setCancelable(false);
            customDialog.setContentView(R.layout.activity_dialog);

            TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
            contenido.setText("Ar-Tec Desea enviarle notificaciones\n¿Desea Aceptar?");

            aF = (ImageButton) customDialog.findViewById(R.id.aceptar);
            aC = (ImageButton) customDialog.findViewById(R.id.cancelar);

            aF.setImageResource(R.drawable.ic_aceptar);
            aC.setImageResource(R.drawable.ic_cancelar);

            aF.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Post post= new Post();
                    //String msg=
                    post.Sendpost("http://www.artecinnovaciones.com/registro_cel.php","id_movil","",1,cont);
                    //getReigstrationId(cont);
                    customDialog.dismiss();
                    //Toast.makeText(cont, msg, Toast.LENGTH_LONG).show();
                }
            });

            aC.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //methodAsyncTaskCalificacion("No");
                    customDialog.dismiss();
                }
            });

            customDialog.show();
        }
    }

/*
    public void getReigstrationId(final Context context){
        new AsyncTask<Void, Void, Void>(){

            private String msg;

            @Override
            protected Void doInBackground(Void... arg0) {

                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }

                try {
                    Log.i("Sender",SENDER_ID);

                    regid = gcm.register(SENDER_ID);

                    //msg = "Movil registrado, registration ID=" + regid;
                    Post post= new Post();
                    msg=post.Sendpost("http://www.artecinnovaciones.com/registro_cel.php","id_movil",regid,1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                Log.i("Developer",msg);
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    public static boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        // if (resultCode != ConnectionResult.SUCCESS) {
        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
            GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context, PLAY_SERVICES_RESOLUTION_REQUEST).show();
        } else {
            Log.i("Developer", "This device is not supported.");
        }
        return false;
        //   }
        // return true;
    } */

}
