package com.example.translationapp.encoders;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.widget.TextView;

import com.example.translationapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TranslationService {

    private Translate translate;

    public void getTranslateService(Resources resources){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try(InputStream is = resources.openRawResource(R.raw.credentials)){
            final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);

            TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
            translate = translateOptions.getService();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void translateInput(String language, TextInputEditText enterText, TextView displayTranslatedText){
        String originalText = Objects.requireNonNull(enterText.getText()).toString();
        Translation translation = translate.translate(originalText, Translate.TranslateOption.targetLanguage(language), Translate.TranslateOption.model("base"));
        String translatedText = translation.getTranslatedText();
        displayTranslatedText.setText(translatedText);

    }

    public void translateInput(String language, String enterText, TextView displayTranslatedText){
        Translation translation = translate.translate(enterText, Translate.TranslateOption.targetLanguage(language), Translate.TranslateOption.model("base"));
        String translatedText = translation.getTranslatedText();
        displayTranslatedText.setText(translatedText);

    }

    public String translateInput(String language, String enterText){
        Translation translation = translate.translate(enterText, Translate.TranslateOption.targetLanguage(language), Translate.TranslateOption.model("base"));
        return translation.getTranslatedText();

    }

    public boolean checkInternetConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE ||
                connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

}
