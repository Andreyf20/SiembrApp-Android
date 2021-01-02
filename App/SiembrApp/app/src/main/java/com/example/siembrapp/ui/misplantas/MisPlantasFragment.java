package com.example.siembrapp.ui.misplantas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembrapp.Adapters.PlantasCardAdapter;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.ui.register.RegisterActivity_Step_3;
import com.example.siembrapp.ui.userinfo.UserInfo;

public class MisPlantasFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_misplantas, container, false);

        RecyclerView plantasrv = root.findViewById(R.id.plantasrv);

        plantasrv.setLayoutManager(new LinearLayoutManager(getContext()));

        PlantasCardAdapter adapter = new PlantasCardAdapter(God.getLoggedUser().getPlantas());

        plantasrv.setAdapter(adapter);

        Button user_info = root.findViewById(R.id.user_info_goto_button);
        user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userInfoActivity = new Intent(root.getContext(), UserInfo.class);
                startActivity(userInfoActivity);
            }
        });

        return root;
    }

}