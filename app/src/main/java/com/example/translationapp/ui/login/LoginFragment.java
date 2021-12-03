package com.example.translationapp.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.translationapp.MainActivity;
import com.example.translationapp.R;
import com.example.translationapp.assets.LoadingBar;
import com.example.translationapp.databinding.FragmentLoginBinding;
import com.example.translationapp.encoders.PasswordEncoder;
import com.example.translationapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginFragment extends Fragment {

    public static final String IP_ADDRESS = "10.0.2.2";

    private EditText loginUsername, loginPassword;
    private Button login;
    private TextView displayLoginTitle, testOuput, currentEmail;
    public ImageView profilePic;
    private LoadingBar loadingBar;

    private List<User> users;

    private FragmentLoginBinding binding;
    //private ActivityMainBinding mainBinding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        binding = FragmentLoginBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        users = new ArrayList<>();

        loginUsername = binding.etLoginUsername;
        loginPassword = binding.etLoginPassword;

        testOuput = binding.tvTestOutput;
        displayLoginTitle = binding.tvLoginTitle;
        displayLoginTitle.setText("Login");
        displayLoginTitle.setTextSize(24);


        login = binding.btnLogin;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        loadingBar = new LoadingBar(requireActivity());



        return root;
    }

    private void loginUser(){
        String usernameText = loginUsername.getText().toString();
        String passwordText = loginPassword.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String URL = String.format("http://%s:8080/api/users/login", IP_ADDRESS);


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String emailText = "";
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);
                        emailText = obj.getString("email");
                        User user = new User(obj.getString("username"), obj.getString("password"), emailText);
                        users.add(user);
                    }

                    for(User u : users){

                        if(u.getUsername().equals(usernameText) && PasswordEncoder.decodePassword(u.getPassword()).equals(passwordText)){
                            Intent i = new Intent(requireActivity().getBaseContext(), MainActivity.class);
                            i.putExtra("USER_NAME", usernameText);
                            i.putExtra("USER_EMAIL", emailText);

                            loadingBar.showDialog();
                            Handler handler = new Handler();

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingBar.dismissBar();
                                    requireActivity().startActivity(i);
                                }
                            },
                                    2000);


                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
                ,
                error -> {
                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();

                });

        queue.add(jsonObjectRequest);



    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}