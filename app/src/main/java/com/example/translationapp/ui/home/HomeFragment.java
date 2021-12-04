package com.example.translationapp.ui.home;

import static android.content.Context.WIFI_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.translationapp.R;
import com.example.translationapp.assets.LoadingBar;
import com.example.translationapp.assets.LoadingFragments;
import com.example.translationapp.databinding.FragmentHomeBinding;
import com.example.translationapp.ui.createAccount.CreateAccountFragment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private ImageButton btnLogin, btnCreateAccount, btnGoToTranslate;
    private LoadingBar loadingBar;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnLogin = binding.imgBtnLogin;
        btnCreateAccount = binding.imgBtnCreateAccount;
        btnGoToTranslate = binding.imgBtnGoToTranslate;

        btnLogin.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_home_to_nav_login, requireActivity());

        });

        btnCreateAccount.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_home_to_nav_createAccount, requireActivity());
        });

        btnGoToTranslate.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_login_to_nav_translation, requireActivity());
        });

        loadingBar = new LoadingBar(requireActivity());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}