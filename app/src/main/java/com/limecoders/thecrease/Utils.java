package com.limecoders.thecrease;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Patterns;
import android.webkit.MimeTypeMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class Utils {

    private static ProgressDialog pDialog;

    public static boolean isEmailVerified(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getExtension(Context context, final Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public static void initpDialog(Context context, String message) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
    }

    public static void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    public static void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    public static void  showAlertDialog(Context context){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Thank you!")
                .setContentText(context.getString(R.string.thankyou))
                .setConfirmText("Okay")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }).show();
    }

}
