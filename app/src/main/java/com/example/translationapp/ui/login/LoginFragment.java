package com.example.translationapp.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
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
import com.example.translationapp.assets.LoadingFragments;
import com.example.translationapp.databinding.FragmentLoginBinding;
import com.example.translationapp.encoders.PasswordEncoder;
import com.example.translationapp.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginFragment extends Fragment {

    public static final String IP_ADDRESS = "10.0.2.2";


    public ImageView profilePic;
    private LoadingBar loadingBar;

    private TextInputEditText textInputEditTextUsername, textInputEditTextPassword;

    private List<User> users;

    private FragmentLoginBinding binding;


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        binding = FragmentLoginBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        users = new ArrayList<>();

//        loginUsername = binding.etLoginUsername;
//        loginPassword = binding.etLoginPassword;
//
//        testOuput = binding.tvTestOutput;
//        displayLoginTitle = binding.tvLoginTitle;
//        displayLoginTitle.setText("Login");
//        displayLoginTitle.setTextSize(24);


        TextView login = binding.btnLogin;
        login.setOnClickListener(view -> loginUser());

        TextView notRegistered = binding.tvGoToCreateAccount;
        notRegistered.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_login_to_nav_createAccount, requireActivity());
        });

        TextView forgotPassword = binding.tvForgotPassword;
        forgotPassword.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_login_to_nav_forgot_password, requireActivity());
        });

        loadingBar = new LoadingBar(requireActivity());


        textInputEditTextUsername = binding.textInputUsernameEt;
        textInputEditTextPassword = binding.textInputPasswordEt;

        return root;
    }

    private void loginUser(){

        String usernameText = Objects.requireNonNull(textInputEditTextUsername.getText()).toString();
        String passwordText = Objects.requireNonNull(textInputEditTextPassword.getText()).toString();

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
                            emailText = u.getEmail();
                            i.putExtra("USER_NAME", usernameText);
                            i.putExtra("USER_EMAIL", emailText);

                            Fragment fragment = new Fragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("USER_NAME_TEXT", usernameText);
                            fragment.setArguments(bundle);

                            loadingBar.showDialog();
                            Handler handler = new Handler();

                            handler.postDelayed(() -> {
                                loadingBar.dismissBar();
                                requireActivity().startActivity(i);
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