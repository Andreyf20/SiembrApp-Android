package com.example.siembrapp.ui.quesembrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.siembrapp.R;

public class QueSembrarFragment extends Fragment {

    private QueSembrarViewModel queSembrarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queSembrarViewModel =
                new ViewModelProvider(this).get(QueSembrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quesembrar, container, false);
        return root;
    }
}