package com.artecinnovaciones.artecdemo.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artecinnovaciones.artecdemo.Adapters.Datos;
import com.artecinnovaciones.artecdemo.Adapters.MyAdapter;
import com.artecinnovaciones.artecdemo.Conexion.Post;
import com.artecinnovaciones.artecdemo.R;
import com.artecinnovaciones.artecdemo.Utilidades.CustomItemClickListener;
import com.artecinnovaciones.artecdemo.Utilidades.Utils;
import com.artecinnovaciones.artecdemo.Utilidades.ViewUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FavoritosFragment extends Fragment {

    private List<Datos> persons;
    private RecyclerView rv;

    ArrayList nombre = new ArrayList();
    ArrayList precio = new ArrayList();
    ArrayList info = new ArrayList();
    ArrayList id = new ArrayList();

    int c=0,t=0;

    Post post = new Post();

    RelativeLayout errorC;

    Dialog customDialog = null;

    Animation anim;
    ImageButton aF, aC;

    TextView T_c,T_t;

    public FavoritosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.favoritos_fragment, container, false);
        descargarImagen(view);
        return view;
    }

    private void descargarImagen(final View view) {
        nombre.clear();
        precio.clear();
        info.clear();
        id.clear();

        persons = new ArrayList<>();

        rv = ViewUtil.findViewById(view, R.id.LY_compras);

        T_c=ViewUtil.findViewById(view,R.id.total_art);
        T_t=ViewUtil.findViewById(view,R.id.total_pagar);

        GridLayoutManager llm = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Cargando datos...");
        pDialog.show();

        errorC = ViewUtil.findViewById(view, R.id.LY_vacio);

        String url1 = "http://artecinnovaciones.com/json_carrito.php?id_user=" + Post.regid;

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

                            c+=jsonArray.getJSONObject(i).getInt("cant");
                            t+=jsonArray.getJSONObject(i).getInt("total");

                            persons.add(new Datos(jsonArray.getJSONObject(i).getString("titulo_img"),
                                    pre,
                                    jsonArray.getJSONObject(i).getString("img")));
                        }
                        initializeAdapter();
                        T_c.setText(c);
                        T_t.setText(t);
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
                //openInfo(position);
            }
        });
        rv.setAdapter(adapter);
    }

  /*  private void openInfo(final int i) {

        customDialog = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_Panel);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCancelable(true);
        customDialog.setContentView(R.layout.activity_dialog);

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
                //msg=
                post.Sendpost("http://www.artecinnovaciones.com/carrito.php", "id_prod", id.get(i).toString(), 3, getActivity().getApplicationContext());
            }
        });

        customDialog.show();

    } */

}
