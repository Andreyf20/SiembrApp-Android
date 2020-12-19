package com.example.siembrapp.ui.misplantas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembrapp.R;

public class MisPlantasFragment extends Fragment {

    private MisPlantasViewModel misPlantasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        misPlantasViewModel =
                new ViewModelProvider(this).get(MisPlantasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_misplantas, container, false);

        RecyclerView plantasrv = root.findViewById(R.id.plantasrv);

        plantasrv.setLayoutManager(new LinearLayoutManager(getContext()));

        //PlantasCardAdapter adapter = new PlantasCardAdapter(Session.LoggedUser.getLoggedUser().getPlantasUsuario());

        //plantasrv.setAdapter(adapter);

        return root;
    }



}