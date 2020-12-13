package com.example.siembrapp.ui.misplantas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MisPlantasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MisPlantasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}