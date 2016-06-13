package com.artecinnovaciones.artecdemo.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artecinnovaciones.artecdemo.Conexion.Post;
import com.artecinnovaciones.artecdemo.R;
import com.artecinnovaciones.artecdemo.Utilidades.CustomItemClickListener;
import com.artecinnovaciones.artecdemo.Adapters.Datos;
import com.artecinnovaciones.artecdemo.Adapters.MyAdapter;
import com.artecinnovaciones.artecdemo.Utilidades.Utils;
import com.artecinnovaciones.artecdemo.Utilidades.ViewUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RecyclerFragment extends Fragment {

    private List<Datos> persons;
    private RecyclerView rv;

    ArrayList nombre = new ArrayList();
    ArrayList precio = new ArrayList();
    ArrayList info = new ArrayList();
    ArrayList id = new ArrayList();

    Dialog customDialog = null;

    Animation anim;
    ImageButton aF, aC;

    Menu m;
    MenuItem item;
    LayerDrawable icon;
    int a = 0;
    int msg = 0;
    private String idString = "";
    String url1 = "";

    Post post = new Post();

    LinearLayout errorC;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private GoogleCloudMessaging gcm = null;

    private String SENDER_ID = "58800470060";
    private String regid;

    public RecyclerFragment(int i) {
        msg = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //msg=getArguments().getString("list");
        //int a=Integer.parseInt(msg);
        View view = inflater.inflate(R.layout.activity_recycler, container, false);

        descargarImagen(view);
        return view;
    }

  /*  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }*/

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //PrincipalActivity getA=new PrincipalActivity();
        //getA.getActivity().getMenuInflater();
        getActivity().getMenuInflater();
        m = menu;

        item = m.findItem(R.id.action_shop);
        icon = (LayerDrawable) item.getIcon();

    }

    private void descargarImagen(final View view) {
        nombre.clear();
        precio.clear();
        info.clear();
        id.clear();

        persons = new ArrayList<>();

        rv = ViewUtil.findViewById(view, R.id.rv);

        GridLayoutManager llm = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Cargando datos...");
        pDialog.show();

        errorC = (LinearLayout) ViewUtil.findViewById(view, R.id.error_internet);

        if (msg == 1) {
            //  getReigstrationId(getActivity().getApplicationContext());
            url1 = "http://artecinnovaciones.com/json_favoritos.php?id_user=" + Post.regid;

        }
        if (msg == 2) {
            url1 = "http://artecinnovaciones.com/Peticion_json.php";
        }

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.post(url1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    pDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String pre = "$ " + jsonArray.getJSONObject(i).getString("precio_img");
                            id.add(jsonArray.getJSONObject(i).getString("id"));
                            nombre.add(jsonArray.getJSONObject(i).getString("titulo_img"));
                            precio.add(pre);
                            info.add(jsonArray.getJSONObject(i).getString("descripcion_img"));

                            persons.add(new Datos(jsonArray.getJSONObject(i).getString("titulo_img"),
                                    pre,
                                    jsonArray.getJSONObject(i).getString("img")));
                        }
                        initializeAdapter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pDialog.dismiss();
                errorC.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initializeAdapter() {
        MyAdapter adapter = new MyAdapter(persons, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                openInfo(position);
            }
        });
        rv.setAdapter(adapter);
    }

    private void openInfo(final int i) {

        customDialog = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_Panel);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCancelable(true);
        customDialog.setContentView(R.layout.activity_dialog);

        //String idP=id.get(i).toString();

        anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.escalar);

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText(nombre.get(i).toString());

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText(info.get(i).toString());

        aF = (ImageButton) customDialog.findViewById(R.id.aceptar);
        aC = (ImageButton) customDialog.findViewById(R.id.cancelar);

        aF.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //customDialog.dismiss();
                aF.startAnimation(anim);

                //msg=
                post.Sendpost("http://www.artecinnovaciones.com/favoritos.php", "id_prod", id.get(i).toString(), 2, getActivity().getApplicationContext());
            }
        });

        aC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                aC.startAnimation(anim);
                a += 1;
                Utils.setBadgeCount(RecyclerFragment.this.getActivity(), icon, a);
                //msg=
                post.Sendpost("http://www.artecinnovaciones.com/carrito.php", "id_prod", id.get(i).toString(), 3, getActivity().getApplicationContext());
            }
        });

        customDialog.show();

    }
/*
    public void getReigstrationId(final Context context) {
        new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(Void... arg0) {

                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }

                try {
                    Log.i("Sender", SENDER_ID);

                    regid = gcm.register(SENDER_ID);


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return "http://artecinnovaciones.com/json_favoritos.php?id_user=" + regid;
            }

            @Override
            protected void onPostExecute(String result) {
                url1 = result;

            }
        }.execute();

    }*/
}