package com.example.translationapp.ui.login;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentLoginBinding;
import com.example.translationapp.encoders.PasswordEncoder;
import com.example.translationapp.models.User;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    private EditText loginUsername, loginPassword;
    private Button login;
    private TextView displayLoginTitle, testOuput, currentEmail;
    public ImageView profilePic;

    private List<User> users;

    private FragmentLoginBinding binding;

    private String schoolIP = "10.10.26.35";

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLoginBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        View v = LayoutInflater.from(requireContext()).inflate(R.layout.nav_header_main, null);

        LinearLayout linearLayout = v.findViewById(R.id.header_layout);

        View hView = linearLayout.getRootView();

        currentEmail = hView.findViewById(R.id.tvCurrentEmail);



        users = new ArrayList<>();

        //profilePic.findViewById(R.id.imageView);


        loginUsername = binding.etLoginUsername;
        loginPassword = binding.etLoginPassword;

        testOuput = binding.tvTestOutput;
        displayLoginTitle = binding.tvLoginTitle;
        displayLoginTitle.setText("Login");
        displayLoginTitle.setTextSize(24);


        currentEmail.findViewById(R.id.tvCurrentEmail);

        login = binding.btnLogin;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });



        return root;
    }

    private void loginUser(){
        String usernameText = loginUsername.getText().toString();
        String passwordText = loginPassword.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String URL = String.format("http://%s:8080/api/users/login", schoolIP);


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);
                        User user = new User(obj.getString("username"), obj.getString("password"), obj.getString("email"));
                        users.add(user);
                    }

                    for(User u : users){

                        if(u.getUsername().equals(usernameText) && PasswordEncoder.decodePassword(u.getPassword()).equals(passwordText)){
                            currentEmail.setText("Hello");
                            Navigation.findNavController(requireView()).navigate(R.id.action_nav_login_to_nav_translation);
                            break;
                        }else{
                            loginUsername.setText("");
                            loginPassword.setText("");
                            loginUsername.setHint("Username may not exist");
                            loginPassword.setHint("Password may not exist");
                            loginUsername.setHintTextColor(Color.RED);
                            loginPassword.setHintTextColor(Color.RED);
                        }

                    }
                } catch (JSONException e) {
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