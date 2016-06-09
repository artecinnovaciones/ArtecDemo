package com.artecinnovaciones.artecdemo.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.artecinnovaciones.artecdemo.Conexion.Post;
import com.artecinnovaciones.artecdemo.R;
import com.artecinnovaciones.artecdemo.Utilidades.CustomItemClickListener;
import com.artecinnovaciones.artecdemo.Adapters.Datos;
import com.artecinnovaciones.artecdemo.Adapters.MyAdapter;
import com.artecinnovaciones.artecdemo.Utilidades.Utils;
import com.artecinnovaciones.artecdemo.Utilidades.ViewUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

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
    ImageButton aF,aC;

    Menu m;
    MenuItem item;
    LayerDrawable icon;
    int a=0;
    String msg;

    Post post= new Post();

    LinearLayout errorC;

    public RecyclerFragment(){    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        msg=getArguments().getString("list");
        //msg = getArguments() != null ? getArguments().getString("list");
        View view = inflater.inflate(R.layout.activity_recycler, container, false);

        descargarImagen(view,msg);
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
        m=menu;

        item = m.findItem(R.id.action_shop);
        icon = (LayerDrawable) item.getIcon();

    }

    private void descargarImagen(final View view,final String tipo){
        nombre.clear();
        precio.clear();
        info.clear();
        id.clear();

        persons = new ArrayList<>();

        rv= ViewUtil.findViewById(view, R.id.rv);

        GridLayoutManager llm = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Cargando datos...");
        pDialog.show();

        errorC=(LinearLayout)ViewUtil.findViewById(view,R.id.error_internet);

        String url1="http://artecinnovaciones.com/Peticion_json.php";
        if (tipo.equals("fav")){
            url1="http://artecinnovaciones.com/json_favoritos.php";
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
                            id.add(jsonArray.getJSONObject(i).getString("id"));
                            nombre.add(jsonArray.getJSONObject(i).getString("titulo_img"));
                            precio.add(jsonArray.getJSONObject(i).getString("precio_img"));
                            info.add(jsonArray.getJSONObject(i).getString("descripcion_img"));

                            persons.add(new Datos(jsonArray.getJSONObject(i).getString("titulo_img"),
                                    jsonArray.getJSONObject(i).getString("precio_img"),
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

    private void initializeAdapter(){
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
                post.Sendpost("http://www.artecinnovaciones.com/favoritos.php","id_prod",id.get(i).toString(),2,getActivity().getApplicationContext());
            }
        });

        aC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                aC.startAnimation(anim);
                a+=1;
                Utils.setBadgeCount(RecyclerFragment.this.getActivity(), icon, a);
                //msg=
                post.Sendpost("http://www.artecinnovaciones.com/carrito.php", "id_prod", id.get(i).toString(), 3, getActivity().getApplicationContext());
            }
        });

        customDialog.show();

    }
}

