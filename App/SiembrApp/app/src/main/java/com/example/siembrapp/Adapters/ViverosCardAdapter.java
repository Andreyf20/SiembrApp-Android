package com.example.siembrapp.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.Vivero;
import com.example.siembrapp.ui.viveros.DetallesViveroActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

                getInfoVivero(v,vivero, new VolleyCallBack() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        pDialog.hide();
                        //Intent
                        Intent detallesViveroIntent = new Intent(v.getContext(), DetallesViveroActivity.class);

                        //Clase Pair<> no es serializable por lo que tenemos que pasar los datos uno por uno
                        detallesViveroIntent.putExtra("nombre",vivero.getNombre());
                        detallesViveroIntent.putExtra("direccion",vivero.getDireccion());
                        detallesViveroIntent.putExtra("telefonos",vivero.getTelefonosStr());
                        detallesViveroIntent.putExtra("horarios",vivero.getHorariosStr());

                        v.getContext().startActivity(detallesViveroIntent);
                    }

                    @Override
                    public void onFailure() {
                        pDialog.hide();
                    }

                    @Override
                    public void noConnection() {
                        pDialog.hide();
                    }

                    @Override
                    public void timedOut() {
                        pDialog.hide();
                    }
                });
            }
        });
    }

    /**
     * Fetch y asignar los horarios y telefonos del vivero
     */
    private void getInfoVivero(final View v,final Vivero vivero,final VolleyCallBack callback){

        loadViveroTelefonos(v,vivero, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject object) {

                loadViveroHorarios(v,vivero, new VolleyCallBack() {
                    @Override
                    public void onSuccess(JSONObject object) {

                        callback.onSuccess(null);

                    }

                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void noConnection() {

                    }

                    @Override
                    public void timedOut() {

                    }
                });

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void noConnection() {

            }

            @Override
            public void timedOut() {

            }
        });

    }

    /**
     * Hace set de los horarios del vivero especificado
     */
    private void loadViveroHorarios(View v,final Vivero vivero, final VolleyCallBack callback){

        God.getHorarios(v.getContext(), vivero.getNombre(), new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject object) {

                try {
                    ArrayList<Pair<String,String>> horarios = new ArrayList<>();

                    JSONArray array = object.getJSONArray("horarios");

                    for (int i = 0; i < array.length(); i++){

                        JSONObject objeto = array.getJSONObject(i);
                        String horas,dias;

                        //Armamos el pair que tiene formato 8:00 - 15:30
                        horas = objeto.getString("horainicio") + " - " + objeto.getString("horafin");

                        dias = objeto.getString("dias");

                        Pair<String,String> pair = new Pair<>(horas,dias);

                        horarios.add(pair);
                    }

                    vivero.setHorarios(horarios);
                    callback.onSuccess(null);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void noConnection() {

            }

            @Override
            public void timedOut() {

            }
        });
    }

    /**
     * Hace set de los telefonos del vivero especificado
     */
    private void loadViveroTelefonos(View v,final Vivero vivero, final VolleyCallBack callBack) {

        God.getTelefonos(v.getContext(), vivero.getNombre(), new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject object) {

                try {
                    ArrayList<String> telefonos = new ArrayList<>();

                    JSONArray array = object.getJSONArray("telefonos");

                    for (int i = 0; i < array.length(); i++){

                        JSONObject objeto = array.getJSONObject(i);

                        telefonos.add(objeto.getString("telefono"));
                    }
                    vivero.setTelefonos(telefonos);
                    callBack.onSuccess(null);

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void noConnection() {

            }

            @Override
            public void timedOut() {

            }
        });

    }

    @Override
    public int getItemCount() {
        return viveros.size();
    }
}
