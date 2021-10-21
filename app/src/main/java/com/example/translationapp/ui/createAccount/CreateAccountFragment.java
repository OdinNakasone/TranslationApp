package com.example.translationapp.ui.createAccount;

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
import com.example.translationapp.databinding.FragmentCreateAccountBinding;
import com.example.translationapp.databinding.FragmentSlideshowBinding;

public class CreateAccountFragment extends Fragment {

    private FragmentCreateAccountBinding binding;


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCreateAccount;
        textView.setText("This is a Create Account Fragment");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}