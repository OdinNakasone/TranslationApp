package com.example.translationapp.assets;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import androidx.navigation.Navigation;

public class LoadingFragments {

    public static void transitionBetweenFragments(View view, int action, Activity activity){
        LoadingBar loadingBar = new LoadingBar(activity);
        loadingBar.showDialog();
        Handler handler = new Handler();

        handler.postDelayed(() -> {
                    loadingBar.dismissBar();
                    Navigation.findNavController(view).navigate(action);
                },
                1000);
    }
}
