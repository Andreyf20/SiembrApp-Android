package com.example.siembrapp.Permissions.Checker;

import android.content.Context;

public interface IPermChecker {
    public boolean isPermissionActive(String permName, Context context) throws Exception;
}
