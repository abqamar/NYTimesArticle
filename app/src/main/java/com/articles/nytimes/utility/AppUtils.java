package com.articles.nytimes.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.articles.nytimes.R;
import com.google.android.material.snackbar.Snackbar;

public class AppUtils {

    public final static String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";
    public final static String APIKEY = "gSpcwGyNQghwNbG0X6opq87hhxIBokXW";

    public static boolean hasConnection(Context c) {

        try {
            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {
                return true;
            }

            NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            }

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return false;
    }

    public static void showSnackBar(View parentLayout, Context context, String message){
        Snackbar snack = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        snack.setTextColor(context.getResources().getColor(R.color.white));
        snack.setBackgroundTint(context.getResources().getColor(R.color.teal_200));
        View view = snack.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }
}
