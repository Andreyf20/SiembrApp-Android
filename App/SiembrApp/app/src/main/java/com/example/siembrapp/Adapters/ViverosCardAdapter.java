package com.example.siembrapp.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembrapp.R;
import com.example.siembrapp.data.model.Vivero;
import com.example.siembrapp.ui.viveros.DetallesViveroActivity;

import java.util.List;

public class ViverosCardAdapter extends RecyclerView.Adapter<ViverosCardAdapter.ViewHolder>{

    private List<Vivero> viveros;

    public ViverosCardAdapter() {

    }

    public ViverosCardAdapter(List<Vivero> viveros) {
        this.viveros = viveros;
    }

    public void setViveros(List<Vivero> nuevoVivero){
        viveros = nuevoVivero;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView nombreViveroTV, direccionViveroTV;
        public CardView card;

        public ViewHolder(View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.fotoViveroIV);
            nombreViveroTV = itemView.findViewById(R.id.nombreViveroTV);
            direccionViveroTV = itemView.findViewById(R.id.direccionViveroTV);
            card = itemView.findViewById(R.id.viveroCard);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View viveroView = inflater.inflate(R.layout.vivero_info_basica_item, parent, false);

        // Return a new holder instance
        return new ViewHolder(viveroView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Vivero vivero = viveros.get(position);

        TextView nombreViveroTV = holder.nombreViveroTV;
        nombreViveroTV.setText(vivero.getNombre());

        TextView direccionViveroTV = holder.direccionViveroTV;
        direccionViveroTV.setText(vivero.getDireccion());

        // Foto de icono vivero temporalmente
        ImageView img = holder.img;
        img.setImageResource(R.drawable.ic_nursery_garden);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Dialog dialog = new Dialog(v.getContext());
                final ProgressDialog pDialog = new ProgressDialog(dialog.getContext());
                pDialog.setMessage("Cargando vivero...");
                pDialog.show();
                //Intent
                Intent detallesViveroIntent = new Intent(v.getContext(), DetallesViveroActivity.class);

                detallesViveroIntent.putExtra("vivero",vivero);

                v.getContext().startActivity(detallesViveroIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return viveros.size();
    }
}
