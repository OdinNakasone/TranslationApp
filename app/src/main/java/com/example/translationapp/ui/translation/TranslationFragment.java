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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranslationFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    private FragmentTranslationBinding binding;

    private TextView displayTranslatedText;
    private Button translateText;
    private EditText enterText;

    private Spinner languageOptions;

    private Translate translate;
    private String getLanguage;

     String[] languages = new String[]{
            "English",
            "Spanish",
             "French",
             "Hawaiian",
             "German",
             "Hindi",
             "Japanese",
             "Korean",
             "Russian"
    };



    
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTranslationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        displayTranslatedText = binding.tvDisplayTranslatedText;
        translateText = binding.btnTranslate;
        enterText = binding.etTranslateOriginal;
        languageOptions = binding.spnLanguageOptions;

        languageOptions.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, languages);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        languageOptions.setAdapter(adapter);


        translateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInternetConnection()){
                    getTranslateService();

                    translateInput(getLanguage);

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

    public void translateInput(String language){
        String originalText = enterText.getText().toString();
        Translation translation = translate.translate(originalText, Translate.TranslateOption.targetLanguage(language), Translate.TranslateOption.model("base"));
        String translatedText = translation.getTranslatedText();
        displayTranslatedText.setText(translatedText);

    }

    public boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE ||
                connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        getCurrentLanguage(languages[i]);

        Toast.makeText(requireContext(), languages[i], Toast.LENGTH_LONG).show();

    }

    private void getCurrentLanguage(String language){
        switch (language){
            case "English":
                getLanguage = "en";
                displayTranslatedText.setText("");
                break;

            case "Spanish":
                getLanguage = "es";
                displayTranslatedText.setText("");
                break;

            case "French":
                getLanguage = "fr";
                displayTranslatedText.setText("");
                break;

            case "Hawaiian":
                getLanguage = "haw";
                displayTranslatedText.setText("");
                break;

            case "German":
                getLanguage = "de";
                displayTranslatedText.setText("");
                break;

            case "Hindi":
                getLanguage = "hi";
                displayTranslatedText.setText("");
                break;

            case "Japanese":
                getLanguage = "ja";
                displayTranslatedText.setText("");
                break;

            case "Korean":
                getLanguage = "ko";
                displayTranslatedText.setText("");
                break;

            case "Russian":
                getLanguage = "ru";
                displayTranslatedText.setText("");
                break;

            default:
                displayTranslatedText.setText(R.string.language_selection_error);

        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}