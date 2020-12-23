package com.example.siembrapp.ui.viveros;

import androidx.lifecycle.ViewModel;

import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.Vivero;

import java.util.ArrayList;

public class ViverosViewModel extends ViewModel {

    private ArrayList<Vivero> viveros;

    public ViverosViewModel() {
    }

    public ArrayList<Vivero> getViveros() {
        return viveros;
    }

    public void updateViveros(){
        viveros = God.getViveros();
    }
}