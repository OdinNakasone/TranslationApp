package com.example.translationapp.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.translationapp.R;
import com.example.translationapp.assets.LoadingBar;
import com.example.translationapp.assets.LoadingFragments;
import com.example.translationapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private ImageButton btnLogin, btnCreateAccount, btnGoToTranslate, btnGoToChat;
    private LoadingBar loadingBar;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnLogin = binding.imgBtnLogin;
        btnCreateAccount = binding.imgBtnCreateAccount;
        btnGoToTranslate = binding.imgBtnGoToTranslate;
        btnGoToChat = binding.imgBtnGotToChat;

        btnLogin.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_home_to_nav_login, requireActivity());

        });

        btnCreateAccount.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_home_to_nav_createAccount, requireActivity());
        });

        btnGoToTranslate.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_home_to_nav_translation, requireActivity());
        });

        btnGoToChat.setOnClickListener(view -> {
            LoadingFragments.transitionBetweenFragments(view, R.id.action_nav_home_to_nav_chat, requireActivity());
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