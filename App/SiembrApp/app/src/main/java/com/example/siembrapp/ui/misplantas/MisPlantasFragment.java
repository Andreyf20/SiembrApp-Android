package com.example.siembrapp.ui.misplantas;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembrapp.Adapters.PlantasCardAdapter;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.MainActivity;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.ui.login.LoginActivity;
import com.example.siembrapp.ui.register.RegisterActivity_Step_3;
import com.example.siembrapp.ui.userinfo.UserInfo;

import org.json.JSONObject;

import java.util.Objects;

public class MisPlantasFragment extends Fragment {

    RecyclerView plantasrv;
    PlantasCardAdapter adapter;
    View root;

    private boolean onResume = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_misplantas, container, false);

        plantasrv = root.findViewById(R.id.plantasrv);

        plantasrv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PlantasCardAdapter(God.getLoggedUser().getPlantas(), true);

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



    @Override
    public void onResume() {
        super.onResume();

        if(this.onResume){
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Actualizando información...");
            pDialog.show();
            God.getPlantasDeUsuario(root.getContext(), new VolleyCallBack() {

                @Override
                public void onSuccess(JSONObject object) {
                    //Toast.makeText(root.getContext(), "Se cargaron los datos de nuevo", Toast.LENGTH_SHORT).show();

                    adapter = new PlantasCardAdapter(God.getLoggedUser().getPlantas(), true);

                    plantasrv.setAdapter(adapter);

                    Objects.requireNonNull(plantasrv.getAdapter()).notifyDataSetChanged();

                    pDialog.dismiss();
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
            });
        }else
            this.onResume = true;
    }

}