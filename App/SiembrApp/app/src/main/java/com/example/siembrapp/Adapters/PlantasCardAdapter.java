package com.example.siembrapp.Adapters;

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

import com.example.siembrapp.MainActivity;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.Planta;
import com.example.siembrapp.ui.detallesPlanta.DetallePlantaActivity;

import java.util.List;

public class PlantasCardAdapter extends RecyclerView.Adapter<PlantasCardAdapter.ViewHolder>{

    private List<Planta> plantas;

    public PlantasCardAdapter(List<Planta> plantas){
        this.plantas = plantas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView text;
        public ImageView img;
        public CardView card;

        public ViewHolder(View itemView){
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.infoTV);
            img = (ImageView) itemView.findViewById(R.id.fotoIV);
            card = (CardView) itemView.findViewById(R.id.plantaCard);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View plantasView = inflater.inflate(R.layout.planta_info_basica_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(plantasView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Planta planta = plantas.get(position);

        TextView text = holder.text;
        text.setText(planta.getInfoBasica());

        // Foto de icono eucalyptus temporalmente
        ImageView img = holder.img;
        img.setImageResource(R.drawable.ic_eucalyptus);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detallePlantaIntent = new Intent(v.getContext(), DetallePlantaActivity.class);
                v.getContext().startActivity(detallePlantaIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return plantas.size();
    }

}
