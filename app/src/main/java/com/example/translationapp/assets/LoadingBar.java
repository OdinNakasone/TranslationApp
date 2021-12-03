package com.example.translationapp.assets;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.translationapp.R;

public class LoadingBar {
    private Activity activity;
    private AlertDialog dialog;

    public LoadingBar(Activity activity){
       setActivity(activity);
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_bar2, null));
        dialog = builder.create();
        dialog.show();
    }

    public void dismissBar(){
        dialog.dismiss();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
