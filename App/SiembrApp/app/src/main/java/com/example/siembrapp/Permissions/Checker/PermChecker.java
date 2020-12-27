package com.example.siembrapp.Permissions.Checker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import java.util.HashMap;

public class PermChecker implements IPermChecker {
    private static PermChecker singletonInstance = null;
    private HashMap<String, String> perms;

    private PermChecker(){
        this.perms = new HashMap<>();
        this.perms.put("internet", Manifest.permission.INTERNET);
        this.perms.put("gps", Manifest.permission.ACCESS_FINE_LOCATION);
        this.perms.put("networkstate", Manifest.permission.ACCESS_NETWORK_STATE);
    }

    public static PermChecker getSingletonInstance(){
        if(singletonInstance == null)
            singletonInstance = new PermChecker();
        return singletonInstance;
    }

    @Override
    public boolean isPermissionActive(String permName, Context context) throws Exception {
        String permission = perms.get(permName);

        if(permission == null){
            throw new Exception("Permission not found in list of possible permissions!");
        }

        return ContextCompat.checkSelfPermission(
                context, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
