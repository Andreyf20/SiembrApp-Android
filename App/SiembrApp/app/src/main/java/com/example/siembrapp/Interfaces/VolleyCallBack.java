package com.example.siembrapp.Interfaces;

import org.json.JSONObject;

public interface VolleyCallBack {
    void onSuccess(JSONObject object);
    void onFailure();
    void noConnection();
    void timedOut();
}

