package com.example.translationapp.ui.translation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentTranslationBinding;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;
import java.io.InputStream;

public class TranslationFragment extends Fragment {


    private FragmentTranslationBinding binding;

    private TextView displayTranslatedText;
    private Button translateText;
    private EditText enterText;

    private boolean connected;
    private String originalText;
    private String translatedText;
    private Translate translate;

    
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTranslationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        displayTranslatedText = binding.tvDisplayTranslatedText;
        translateText = binding.btnTranslate;
        enterText = binding.etTranslateOriginal;

        translateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInternetConnection()){
                    getTranslateService();
                    translateInput();
                }else{
                    displayTranslatedText.setText(getResources().getString(R.string.no_connection));
                }
            }
        });

        return root;
    }

    public void getTranslateService(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try(InputStream is = getResources().openRawResource(R.raw.credentials)){
            final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);

            TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
            translate = translateOptions.getService();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void translateInput(){
        originalText = enterText.getText().toString();
        Translation translation = translate.translate(originalText, Translate.TranslateOption.targetLanguage("es"), Translate.TranslateOption.model("base"));
        translatedText = translation.getTranslatedText();

        displayTranslatedText.setText(translatedText);
    }

    public boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connected = connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE ||
                connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

        return connected;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}