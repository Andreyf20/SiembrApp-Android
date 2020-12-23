package com.example.siembrapp.ui.misplantas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembrapp.Adapters.PlantasCardAdapter;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;

public class MisPlantasFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_misplantas, container, false);

        RecyclerView plantasrv = root.findViewById(R.id.plantasrv);

        plantasrv.setLayoutManager(new LinearLayoutManager(getContext()));

        PlantasCardAdapter adapter = new PlantasCardAdapter(God.getLoggedUser().getPlantas());

        plantasrv.setAdapter(adapter);

        return root;
    }

}