package com.example.translationapp.ui.createAccount;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentCreateAccountBinding;
import com.example.translationapp.encoders.PasswordEncoder;

import java.util.Calendar;


public class CreateAccountFragment extends Fragment {

    private FragmentCreateAccountBinding binding;
    private EditText createUsername, createPassword, createReEnterPassword, createEmail;
    private Button confirmAccount, displayAge;
    private TextView createAccountTitle;
    private DatePickerDialog datePickerDialog;


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createAccountTitle = binding.textCreateAccount;

        createUsername = binding.etCreateUsername;
        createPassword = binding.etCreatePassword;
        createReEnterPassword = binding.etCreateReEnterPassword;
        createEmail = binding.etCreateEmail;

        displayAge = binding.btnDisplayAge;
        displayAge.setText(getTodayDate());
        displayAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatePicker();
                datePickerDialog.show();
            }
        });
        createAccountTitle.setText("This is a Create Account Fragment");

        confirmAccount = binding.btnConfirmAccount;
        confirmAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                alert.setMessage("Are you sure?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                        PasswordEncoder.encodePassword(createPassword.getText().toString());
                        Navigation.findNavController(view).navigate(R.id.nav_createAccount_to_nav_login);
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });


                alert.show();


            }
        });

        return root;
    }

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;

        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                displayAge.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(requireContext(), dateSetListener, year, month ,day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";


        }

        return "JAN";
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}