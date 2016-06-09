package com.artecinnovaciones.artecdemo.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artecinnovaciones.artecdemo.R;
import com.artecinnovaciones.artecdemo.Utilidades.ViewUtil;

public class MenuRestFragment extends Fragment {

    ImageView comidas, bebidas, reservar;
    MediaPlayer c;

    public MenuRestFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu_rest_fragment, container, false);
        metodo(view);
        return view;
    }

    private void metodo(View view) {
        comidas=(ImageView)ViewUtil.findViewById(view, R.id.comidas);
        bebidas=(ImageView)ViewUtil.findViewById(view,R.id.bebidas);
        comidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = MediaPlayer.create(getActivity(), R.raw.click);
                c.start();
                lista();
            }
        });
        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ReservarFragment.class));
            }
        });
    }

    public void lista(){
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        RecyclerFragment LisFrag = new RecyclerFragment(2);
        trans.replace(R.id.frag_l, LisFrag);
        trans.addToBackStack(null);
        trans.commit();
    }
}
