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

    Dialog customDialog = null;

    Animation anim;
    ImageButton aF,aC;

    Menu m;
    MenuItem item;
    LayerDrawable icon;
    int a=0;

    LinearLayout errorC;

    public RecyclerFragment(){    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        m=menu;

        item = m.findItem(R.id.action_shop);
        icon = (LayerDrawable) item.getIcon();

    }

    private void descargarImagen(final View view){
        nombre.clear();
        precio.clear();
        info.clear();

        persons = new ArrayList<>();

        rv= ViewUtil.findViewById(view, R.id.rv);

        GridLayoutManager llm = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Cargando datos...");
        pDialog.show();

        errorC=(LinearLayout)ViewUtil.findViewById(view,R.id.error_internet);

        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.post("http://artecinnovaciones.com/Peticion_json.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    pDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {
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

    private void openInfo(int i) {

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
                //menuP=new PrincipalActivity();
                //menuP.notificacionMenu(4);
            }
        });

        aC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                aC.startAnimation(anim);
                a+=1;
                Utils.setBadgeCount(RecyclerFragment.this.getActivity(), icon, a);
            }
        });

        customDialog.show();

    }

    /*private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Datos("Emma Wilson", "23 years old", R.drawable.bebidas));
        persons.add(new Datos("Lavery Maiss", "25 years old", R.drawable.comidas));
        persons.add(new Datos("Lillie Watts", "35 years old", R.drawable.info));
        persons.add(new Datos("Emma Wilson", "23 years old", R.drawable.bebidas));
        persons.add(new Datos("Lavery Maiss", "25 years old", R.drawable.comidas));
        persons.add(new Datos("Lillie Watts", "35 years old", R.drawable.info));
        persons.add(new Datos("Emma Wilson", "23 years old", R.drawable.bebidas));
        persons.add(new Datos("Lavery Maiss", "25 years old", R.drawable.comidas));
        persons.add(new Datos("Lillie Watts", "35 years old", R.drawable.info));
    }

    private class ImagenAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView imgnombre, imgprecio;

        public ImagenAdapter(Context applicationContext){
            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagen.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup viewGroup= (ViewGroup)layoutInflater.inflate(R.layout.lista_item,null);
            smartImageView=(SmartImageView)viewGroup.findViewById(R.id.imagen1);
            imgnombre=(TextView)viewGroup.findViewById(R.id.imgnombre);
            imgprecio=(TextView)viewGroup.findViewById(R.id.imgprecio);

            String urlFinal="http://artecinnovaciones.com/uploads"+imagen.get(position).toString();
            Rect rect=new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());

            smartImageView.setImageUrl(urlFinal,rect);

            imgnombre.setText(nombre.get(position).toString());
            imgprecio.setText(precio.get(position).toString());

            return viewGroup;
        }
    }*/
}

