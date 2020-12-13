package com.example.siembrapp.ui.misplantas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembrapp.Adapters.PlantasCardAdapter;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.Planta;

import java.util.ArrayList;

public class MisPlantasFragment extends Fragment {

    private MisPlantasViewModel misPlantasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        misPlantasViewModel =
                new ViewModelProvider(this).get(MisPlantasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_misplantas, container, false);

        RecyclerView plantasrv = root.findViewById(R.id.plantasrv);

        plantasrv.setLayoutManager(new LinearLayoutManager(getContext()));

        PlantasCardAdapter adapter = new PlantasCardAdapter(getTests());

        plantasrv.setAdapter(adapter);

        return root;
    }

    public ArrayList<Planta> getTests(){
        Planta.PlantaBuilder builder = new Planta.PlantaBuilder();
        String asdf = "asdf";
        String asdf2 = "asdf2";
        ArrayList<String> usos = new ArrayList<>();
        usos.add("xd");
        usos.add("xd2");
        double f = 1.02;

        //Testing
        builder.setAgentePolinizador(asdf).setFamilia(asdf).setFenologia(asdf).setFlor(asdf).setHabito(asdf).setFruto(asdf).setNombreCientifico(asdf).setNombreComun(asdf).setOrigen(asdf).setTexturaFruto(asdf).setPaisajeRecomendado(asdf).setRequerimientosDeLuz(asdf);
        builder.setMinRangoAltitudinal(f).setMaxRangoAltitudinal(f).setMetros(f);
        builder.setUsosConocidos(usos);
        Planta p1 = builder.build();

        Planta.PlantaBuilder builder2 = new Planta.PlantaBuilder();
        builder2.setAgentePolinizador(asdf2).setFamilia(asdf2).setFenologia(asdf2).setFlor(asdf2).setHabito(asdf2).setFruto(asdf2).setNombreCientifico(asdf2).setNombreComun(asdf2).setOrigen(asdf2).setTexturaFruto(asdf2).setPaisajeRecomendado(asdf2).setRequerimientosDeLuz(asdf2);
        builder2.setMinRangoAltitudinal(f).setMaxRangoAltitudinal(f).setMetros(f);
        builder2.setUsosConocidos(usos);
        Planta p2 = builder2.build();

        ArrayList<Planta> ps = new ArrayList<>();
        ps.add(p1);
        ps.add(p2);
        ps.add(p1);
        ps.add(p2);
        ps.add(p1);
        ps.add(p2);
        ps.add(p1);
        ps.add(p2);
        ps.add(p1);
        ps.add(p2);
        ps.add(p1);
        ps.add(p2);
        return ps;
    }


}