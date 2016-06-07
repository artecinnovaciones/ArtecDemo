package com.artecinnovaciones.artecdemo.Fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artecinnovaciones.artecdemo.Activitys.ScrollingActivity;
import com.artecinnovaciones.artecdemo.R;
import com.artecinnovaciones.artecdemo.Utilidades.BotonClick;
import com.artecinnovaciones.artecdemo.Utilidades.ViewUtil;

public class PrincipalFragment extends Fragment {

    ImageView trompos,artec;
    BotonClick bc= new BotonClick();

    public PrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        metodo(view);
        return view;
    }

    private void metodo(View view) {
        trompos=(ImageView)ViewUtil.findViewById(view,R.id.imageTrompos);
        artec=(ImageView)ViewUtil.findViewById(view,R.id.imageArtec);
        trompos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bc.sonido(getActivity().getApplicationContext());
                //startActivity(new Intent(getActivity(), RecyclerActivity.class));
                lista();
            }
        });
        artec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bc.sonido(getActivity().getApplicationContext());
                startActivity(new Intent(getActivity(), ScrollingActivity.class));
            }
        });
    }

    public void lista(){
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        MenuRestFragment LisFrag = new MenuRestFragment();
        trans.replace(R.id.frag_l, LisFrag);
        trans.addToBackStack(null);
        trans.commit();
    }

}
