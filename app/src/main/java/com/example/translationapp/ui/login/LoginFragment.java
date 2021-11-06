package com.example.translationapp.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentGalleryBinding;
import com.example.translationapp.databinding.FragmentLoginBinding;
import com.example.translationapp.encoders.PasswordEncoder;
import com.example.translationapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    private EditText loginUsername, loginPassword;
    private Button login;
    private TextView displayLoginTitle, testOuput;

    private List<User> users;
    private List<String> usernames;

    private FragmentLoginBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        users = new ArrayList<>();
        usernames = new ArrayList<>();

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



        return root;
    }

    private void loginUser(){
        String usernameText = loginUsername.getText().toString();
        String passwordText = loginPassword.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String URL = "http://10.0.0.234:8080/api/users/login";


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject obj = response.getJSONObject(i);
                        User user = new User(obj.getString("username"), obj.getString("password"));
                        users.add(user);
                    }

                    for(User u : users){
                        if(u.getUsername().equals(usernameText) && PasswordEncoder.decodePassword(u.getPassword()).equals(passwordText)){
                            Navigation.findNavController(requireView()).navigate(R.id.action_nav_login_to_nav_translation);
                            Toast.makeText(getContext(), "User Authorized", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
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