package com.example.siembrapp.ui.quesembrar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QueSembrarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QueSembrarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}