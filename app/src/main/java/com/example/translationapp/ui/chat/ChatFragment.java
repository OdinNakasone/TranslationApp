package com.example.translationapp.ui.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.translationapp.databinding.FragmentChatBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ChatFragment extends Fragment {

    public static final String IP_ADDRESS = "10.0.2.2";

    private FragmentChatBinding binding;
    private TextView chatResponses, btnSendMessage;
    private TextInputEditText message;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        chatResponses = binding.tvResponses;

        btnSendMessage = binding.btnSendMessage;
        message = binding.textInputSendMessage;

        chatResponses.setText("");

        btnSendMessage.setOnClickListener(view -> {

            sendMessage();

        });

        return root;

    }

    private void sendMessage(){
        Bundle bundle = this.getArguments();
        //String username = Objects.requireNonNull(bundle).getString("USER_NAME_TEXT");

        String messageText = Objects.requireNonNull(message.getText()).toString();
        chatResponses.append("\nuser - " + messageText);

        String URL = String.format("http://%s:5000/api/talk", IP_ADDRESS);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JSONObject jBody = new JSONObject();
        try{
            jBody.put("user_input", messageText);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, URL, jBody, response -> {
            try {
                String botResponse = (String) response.get("msg");
                chatResponses.append("\n(Bot) - " + botResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> Log.e("error: ", error.toString())

        );

        queue.add(jr);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}