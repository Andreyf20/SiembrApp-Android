package com.example.siembrapp.Permissions.Checker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import java.util.HashMap;
import java.util.Map;

public class PermChecker implements IPermChecker {
    private static PermChecker singletonInstance = null;
    private static HashMap<String, String> perms;

    private PermChecker(){
        perms = new HashMap<>();
        perms.put("internet", Manifest.permission.INTERNET);
        perms.put("gps", Manifest.permission.ACCESS_FINE_LOCATION);
        /*perms.put("networkstate", Manifest.permission.ACCESS_NETWORK_STATE);*/
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
