package com.example.siembrapp.ui.detalleplanta;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.siembrapp.R;

public class DetallesPlantaFragment extends Fragment {

    private DetallesPlantaViewModel mViewModel;

    public static DetallesPlantaFragment newInstance() {
        return new DetallesPlantaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detalles_plantas_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetallesPlantaViewModel.class);
        // TODO: Use the ViewModel
    }

}