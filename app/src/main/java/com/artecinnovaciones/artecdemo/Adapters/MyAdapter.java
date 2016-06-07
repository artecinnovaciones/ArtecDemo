package com.artecinnovaciones.artecdemo.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artecinnovaciones.artecdemo.R;
import com.artecinnovaciones.artecdemo.Utilidades.CustomItemClickListener;
import com.github.snowdream.android.widget.SmartImageView;

import java.util.List;

/**
 * Created by LAP-NIDIA on 27/05/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.DatosViewHolder> {

    CustomItemClickListener listener;

    public static class DatosViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;
        public SmartImageView personPhoto;

        public DatosViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.NomProd);
            personAge = (TextView)itemView.findViewById(R.id.PrecioProd);
            personPhoto = (SmartImageView)itemView.findViewById(R.id.imgproducto);
        }
    }

    List<Datos> persons;

    public MyAdapter(List<Datos> persons, CustomItemClickListener listener){
        this.persons = persons;
        this.listener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DatosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catalogo_item, viewGroup, false);
        final DatosViewHolder pvh = new DatosViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, pvh.getPosition());
            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(DatosViewHolder personViewHolder, int i) {
        String urlFinal="http://artecinnovaciones.com/uploads/"+persons.get(i).photoId;
        Rect rect=new Rect(personViewHolder.personPhoto.getLeft(),personViewHolder.personPhoto.getTop(),personViewHolder.personPhoto.getRight(),personViewHolder.personPhoto.getBottom());

        personViewHolder.personPhoto.setImageUrl(urlFinal, rect);

        personViewHolder.personName.setText(persons.get(i).name);
        personViewHolder.personAge.setText(persons.get(i).age);
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
