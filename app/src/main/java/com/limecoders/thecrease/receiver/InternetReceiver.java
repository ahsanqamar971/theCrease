package com.limecoders.thecrease.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.limecoders.thecrease.activities.NoInternetActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InternetReceiver extends BroadcastReceiver {

    public InternetReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo!=null && netInfo.isConnected()) {
            // Do something
            Log.d("Network Available ", "Flag No 1");
        }else {
            new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("No Internet Connection")
                    .setContentText("Please connect to internet and restart the app").show();
        }
    }
}
