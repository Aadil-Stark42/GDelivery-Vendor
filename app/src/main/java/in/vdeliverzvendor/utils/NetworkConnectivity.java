package in.vdeliverzvendor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectivity {

    public static Boolean checkNow(Context context)
    {
        try
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e)
        {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
        }

        return false;
    }
}
