package com.example.translationapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.translationapp.assets.LoadingBar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.translationapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView currentUsername, currentEmail;
    private NavigationView navigationView;
    private LoadingBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_login, R.id.nav_translation)
                .setOpenableLayout(drawer)
                .build();

        View headerView = navigationView.getHeaderView(0);

        currentUsername = headerView.findViewById(R.id.tvCurrentUsername);
        currentEmail = headerView.findViewById(R.id.tvCurrentEmail);

        loadingBar = new LoadingBar(this);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        Intent intent = getIntent();

        String username = intent.getStringExtra("USER_NAME");
        currentUsername.setText(username);
        currentUsername.setHint("Username goes here");

        String email = intent.getStringExtra("USER_EMAIL");
        currentEmail.setText(email);
        currentEmail.setHint("Email goes here");

        MenuItem m = navigationView.getMenu().findItem(R.id.nav_login);
        String currentUsernameText = currentUsername.getText().toString();
        String currentEmailText = currentEmail.getText().toString();

        if(currentUsernameText.equals("") || currentEmailText.equals("")){
            m.setTitle("Login");

        }else{
            m.setTitle("Logout");
        }

        m.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getTitle().equals("Logout")){
                currentEmail.setText("");
                currentUsername.setText("");
                m.setTitle("Login");

                loadingBar.showDialog();
                Handler handler = new Handler();

                Intent i = new Intent(getBaseContext(), MainActivity.class);

                handler.postDelayed(() -> {
                            loadingBar.dismissBar();
                            this.startActivity(i);
                        },
                        2000);

                return true;
            }
            return false;
        });



        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}