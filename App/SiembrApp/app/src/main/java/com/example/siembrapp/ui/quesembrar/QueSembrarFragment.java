package com.example.siembrapp.ui.quesembrar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.siembrapp.MainActivity;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.ui.quesembrar.steps.QueSembrar_Step_1;

public class QueSembrarFragment extends Fragment {

    private QueSembrarViewModel queSembrarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        queSembrarViewModel =
                new ViewModelProvider(this).get(QueSembrarViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_quesembrar, container, false);

        TextView queSembrar = root.findViewById(R.id.que_sembrar_textView);

        queSembrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent queSembrarStep1 = new Intent(root.getContext(), QueSembrar_Step_1.class);
                startActivity(queSembrarStep1);
            }
        });

        return root;
    }
}