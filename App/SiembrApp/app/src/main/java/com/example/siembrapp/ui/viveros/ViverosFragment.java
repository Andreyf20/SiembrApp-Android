package com.example.siembrapp.ui.viveros;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;

import org.json.JSONObject;

public class ViverosFragment extends Fragment {

    private ViverosViewModel viverosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viverosViewModel =
                new ViewModelProvider(this).get(ViverosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_viveros, container, false);

        God.listarViveros(container.getContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject object) {

                Log.d("XD",object.toString());

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

        return root;
    }
}