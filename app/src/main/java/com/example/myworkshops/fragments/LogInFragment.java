package com.example.myworkshops.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myworkshops.R;
import com.example.myworkshops.databases.database_helpers.UsersDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class LogInFragment extends Fragment implements View.OnClickListener {

    private EditText mEmailId;
    private EditText mPassword;
    private Button mLogin;
    private TextView goToSignUp;
    private View view;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log_in, container, false);
        mEmailId = view.findViewById(R.id.signin_email_id);
        mPassword = view.findViewById(R.id.signin_password);
        mLogin = view.findViewById(R.id.login_button);
        goToSignUp = view.findViewById(R.id.login_to_signup);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mLogin.setOnClickListener(this);
        goToSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                login();
                break;
            case R.id.login_to_signup:
                navController.navigate(R.id.nav_sign_up);
                break;
        }
    }

    private void login() {
        String emailId = mEmailId.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (!checkAllFields(emailId, password)) {
            return;
        }
        UsersDatabaseHelper helper = new UsersDatabaseHelper(getContext());
        if (helper.authenticated(emailId, password)) {
            setLoginStateId(emailId);
            makeSnackBar("Login successful");
            navController.navigate(R.id.nav_dashboard);
        } else {
            if (helper.checkAlredyExists(emailId)) {
                makeSnackBar("Incorrect Password");
            } else {
                makeSnackBar("Email Id is Not Registered");
            }
        }
    }

    private void setLoginStateId(String emailId) {
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences(getString(R.string.shared_preference_name),
                        Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            Snackbar.make(view, "Error!", Snackbar.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.login_state_key), emailId);
        editor.apply();
    }

    private boolean checkAllFields(String emailId, String password) {
        if (TextUtils.isEmpty(emailId)) {
            makeSnackBar("Please Enter Email Id");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            makeSnackBar("Please Enter your Password");
            return false;
        }
        return true;
    }

    private void makeSnackBar(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}