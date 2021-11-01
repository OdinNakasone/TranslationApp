package com.example.translationapp.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentGalleryBinding;
import com.example.translationapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {


    private FragmentLoginBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView displayLoginTitle = root.findViewById(R.id.tvLoginTitle);
        displayLoginTitle.setText("Login");
        displayLoginTitle.setTextSize(24);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}