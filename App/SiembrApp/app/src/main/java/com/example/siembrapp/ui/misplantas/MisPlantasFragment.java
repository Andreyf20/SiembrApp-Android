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
import com.example.siembrapp.data.model.LoggedInUser;
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

        //PlantasCardAdapter adapter = new PlantasCardAdapter(LoggedInUser.LoggedUser.getLoggedUser().getPlantasUsuario());

        //plantasrv.setAdapter(adapter);

        return root;
    }



}