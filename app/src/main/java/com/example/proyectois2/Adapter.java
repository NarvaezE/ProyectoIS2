package com.example.proyectois2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter<Meds> {
    Context context;
    List<Meds> arregloMeds;
    public Adapter(@NonNull Context context, List<Meds> arregloMeds) {
        super(context, R.layout.list_medicamentos,arregloMeds);
        this.context =context;
        this.arregloMeds =arregloMeds;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_medicamentos,null,true);

        TextView tvID = view.findViewById(R.id.txt_id);
        TextView tvName = view.findViewById(R.id.nameMed);
//        TextView tvCant =view.findViewById(R.id.cantMed);


        tvID.setText(arregloMeds.get(position).getId()+"");
        tvName.setText(arregloMeds.get(position).getNombre()+"");
//        tvCant.setText(arregloMeds.get(position).getCantidad());

        return view;
    }
}
