package com.example.translationapp.ui.translation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentTranslationBinding;

import com.example.translationapp.encoders.TranslationService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;

//import com.google.cloud.storage.Bucket;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;
//import com.google.cloud.texttospeech.v1.TextToSpeechClient;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.protobuf.ByteString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TranslationFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    private FragmentTranslationBinding binding;
    private TranslationService translationService;

    private TextView displayTranslatedText, translateText;
    private TextInputEditText enterText;


    private Spinner languageOptions;

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        binding = FragmentTranslationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        displayTranslatedText = binding.tvDisplayTranslatedText;
        translateText = binding.btnTranslate;
        enterText = binding.textInputTranslateEt;
        languageOptions = binding.spnLanguageOptions;
        translationService = new TranslationService();

        languageOptions.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, languages);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        languageOptions.setAdapter(adapter);


        translateText.setOnClickListener(view -> {
            try{
                if(translationService.checkInternetConnection(requireContext())){
                    translationService.getTranslateService(getResources());

                    translationService.translateInput(getLanguage, enterText, displayTranslatedText);

                }else{
                    displayTranslatedText.setText(getResources().getString(R.string.no_connection));
                }
            }catch (Exception e){
                displayTranslatedText.setTextSize(10);
                enterText.setText(e.toString());
            }

        });

        return root;
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