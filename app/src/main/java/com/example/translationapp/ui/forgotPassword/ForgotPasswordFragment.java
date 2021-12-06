package com.example.translationapp.ui.forgotPassword;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentForgotPasswordBinding;
import com.example.translationapp.databinding.FragmentLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordFragment extends Fragment {



    private FragmentForgotPasswordBinding binding;
    private EditText testInput;
    private TextView testOutput;
    private Button testConnection;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        testInput = binding.etTestInput;
        testOutput = binding.tvTestOutput;

        testConnection = binding.btnTestConnection;
        testConnection.setOnClickListener(view -> {
            testChatBot();
        });

        return root;
    }

    private void testChatBot(){
        String testInputText = testInput.getText().toString();



        //JSONObject parent = new JSONObject();




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}