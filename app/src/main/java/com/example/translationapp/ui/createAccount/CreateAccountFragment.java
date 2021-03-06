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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.translationapp.R;
import com.example.translationapp.databinding.FragmentCreateAccountBinding;
import com.example.translationapp.encoders.PasswordEncoder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;


public class CreateAccountFragment extends Fragment {

    public static final String IP_ADDRESS = "10.0.2.2";

    private FragmentCreateAccountBinding binding;
    private TextInputEditText createUsername, createPassword, createReEnterPassword, createEmail, displayBirthday;
    private TextView confirmAccount;
    private DatePickerDialog datePickerDialog;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        createUsername = binding.textInputCreateUsernameEt;
        createPassword = binding.textInputCreatePasswordEt;
        createReEnterPassword = binding.textInputCreateReEnterPasswordEt;
        createEmail = binding.textInputCreateEmailEt;

        displayBirthday = binding.textInputCreateBirthdayEt;

        TextInputLayout birthdayAction = binding.textInputLayoutCreateBirthday;
        birthdayAction.setStartIconOnClickListener(view -> {
            initDatePicker();
            datePickerDialog.show();
        });


        displayBirthday.setText(getTodayDate());

        confirmAccount = binding.btnConfirmAccount;
        confirmAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                alert.setMessage("Are you sure?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        validateCreateUser();
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
                displayBirthday.setText(date);
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

    private void validateCreateUser(){
        String usernameText = Objects.requireNonNull(createUsername.getText()).toString();
        String passwordText = Objects.requireNonNull(createPassword.getText()).toString();
        String reEnterPasswordText = Objects.requireNonNull(createReEnterPassword.getText()).toString();
        String emailText = Objects.requireNonNull(createEmail.getText()).toString();
        String birthdayText = Objects.requireNonNull(displayBirthday.getText()).toString();

        if(!usernameText.isEmpty() && !passwordText.isEmpty() && !reEnterPasswordText.isEmpty() && !emailText.isEmpty() && !birthdayText.isEmpty()){
            if(reEnterPasswordText.equals(passwordText)){
                if(emailText.contains("@") && emailText.contains(".com")){
                    createUser(usernameText, passwordText, emailText, birthdayText);
                }else{
                    Toast.makeText(getContext(), "Email not formatted correctly.", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getContext(), "Passwords don't Match", Toast.LENGTH_LONG).show();
            }


        } else{
            Toast.makeText(getContext(), "Not all fields are filled", Toast.LENGTH_LONG).show();
        }
    }

    private void createUser(String username, String password, String email, String birthday){
        final String URL = String.format("http://%s:8080/api/users/create", IP_ADDRESS);

        JSONObject parent = new JSONObject();

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JSONObject jBody = new JSONObject();
        try{
            jBody.put("username", username);
            jBody.put("password", PasswordEncoder.encodePassword(password));
            jBody.put("email", email);
            jBody.put("birthday", birthday);
            parent.accumulate("data", jBody);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, URL, jBody, response -> {
            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.nav_createAccount_to_nav_login);

        }, error -> {
            Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.data == null || response.data.length == 0) {
                    return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                } else {

                    return super.parseNetworkResponse(response);
                }

            }
        };

        queue.add(jr);
//
//        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams(){
//                Map<String, String> params = new HashMap<>();
//
//
//                return params;
//            }
//        };


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}