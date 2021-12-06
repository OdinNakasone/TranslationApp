package com.example.translationapp.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentChatBinding;
import com.example.translationapp.encoders.TranslationService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ChatFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String IP_ADDRESS = "10.0.2.2";

    private FragmentChatBinding binding;
    private TextView chatResponses, btnSendMessage;
    private TextInputEditText message;
    private TranslationService translationService;

    private Spinner languageOptions2;
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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        chatResponses = binding.tvResponses;
        btnSendMessage = binding.btnSendMessage;
        message = binding.textInputSendMessage;
        translationService = new TranslationService();
        languageOptions2 = binding.spnLanguageOptions2;

        languageOptions2.setOnItemSelectedListener(this);

        chatResponses.setText("");

        btnSendMessage.setOnClickListener(view -> {

            sendMessage();

        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, languages);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        languageOptions2.setAdapter(adapter);


        return root;

    }

    private void sendMessage() {

        Bundle bundle = this.getArguments();
        //String username = Objects.requireNonNull(bundle).getString("USER_NAME_TEXT");

        try {
            if (translationService.checkInternetConnection(requireContext())) {
                translationService.getTranslateService(getResources());
                String messageText = Objects.requireNonNull(message.getText()).toString();
                String translatedMessageText = translationService.translateInput(getLanguage, messageText);
                chatResponses.append("\nuser - " + translatedMessageText);

                String URL = String.format("http://%s:5000/api/talk", IP_ADDRESS);
                RequestQueue queue = Volley.newRequestQueue(requireContext());
                JSONObject jBody = new JSONObject();
                try {
                    jBody.put("user_input", messageText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, URL, jBody, response -> {
                    try {
                        String botResponse = (String) response.get("msg");
                        String translatedBotResponse = translationService.translateInput(getLanguage, botResponse);
                        chatResponses.append("\n(Bot) - " + translatedBotResponse + "\n");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("error: ", error.toString())

                );

                queue.add(jr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        getCurrentLanguage(languages[i]);

        Toast.makeText(requireContext(), languages[i], Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getCurrentLanguage(String language) {
        switch (language) {
            case "English":
                getLanguage = "en";
                break;

            case "Spanish":
                getLanguage = "es";
                break;

            case "French":
                getLanguage = "fr";
                break;

            case "Hawaiian":
                getLanguage = "haw";
                break;

            case "German":
                getLanguage = "de";
                break;

            case "Hindi":
                getLanguage = "hi";
                break;

            case "Japanese":
                break;

            case "Korean":
                getLanguage = "ko";
                break;

            case "Russian":
                getLanguage = "ru";
                break;

            default:
                message.setText(R.string.language_selection_error);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}