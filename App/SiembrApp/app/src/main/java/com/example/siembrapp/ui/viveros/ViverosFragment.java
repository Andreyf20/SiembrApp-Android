package com.example.siembrapp.ui.viveros;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembrapp.Adapters.ViverosCardAdapter;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.Vivero;
import com.example.siembrapp.data.model.Vivero.ViveroBuilder;

import java.util.ArrayList;

public class ViverosFragment extends Fragment {


    //Ref: https://gist.github.com/takeshiyako2/ac4b901ec26658058603 MainActivity.java
    //Ref: https://stackoverflow.com/a/31368367
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        final View root = inflater.inflate(R.layout.fragment_viveros, container, false);

        /* Test
        ArrayList<Vivero> viveros = new ArrayList<>();

        ViveroBuilder builder = new ViveroBuilder();

        builder.setNombre("A").setDireccion("Moravia");
        viveros.add(builder.build());

        ViveroBuilder builder2 = new ViveroBuilder();
        builder2.setNombre("B").setDireccion("Coronado");
        viveros.add(builder2.build());*/

        ViverosCardAdapter adapter = new ViverosCardAdapter(God.getViveros());
        RecyclerView viverosRV = root.findViewById(R.id.viverosRV);

        viverosRV.setLayoutManager(new LinearLayoutManager(getContext()));
        viverosRV.setAdapter(adapter);

        return root;
    }


    /*God.getHorarios(getContext(), "FUNDAZOO", new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject object) {
                Log.d("XD",object.toString());
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
        });*/

    /*God.getTelefonos(container.getContext(),"FUNDAZOO", new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject object) {
                Log.d("XD",object.toString());
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
        });*/
}