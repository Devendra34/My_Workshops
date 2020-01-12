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

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private EditText mName;
    private EditText mEmailId;
    private EditText mPassword;
    private EditText mConfirmPassowrd;
    private TextView goToLoginPage;
    private Button mSignUp;
    private View view;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mName = view.findViewById(R.id.signup_name);
        mEmailId = view.findViewById(R.id.signup_email_id);
        mPassword = view.findViewById(R.id.signup_password);
        mConfirmPassowrd = view.findViewById(R.id.signup_confirm_password);
        goToLoginPage = view.findViewById(R.id.signup_to_login_screen);
        mSignUp = view.findViewById(R.id.signup_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        goToLoginPage.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.signup_button:
                signup();
                break;
            case R.id.signup_to_login_screen:
                navController.navigate(R.id.nav_log_in);
                break;
        }
    }

    private void signup() {
        String name = mName.getText().toString().trim();
        String emailId = mEmailId.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassowrd.getText().toString().trim();
        if (!allDetailsEntered(name, emailId, password, confirmPassword)) {
            return;
        }
        UsersDatabaseHelper helper = new UsersDatabaseHelper(getContext());
        if (helper.checkAlredyExists(emailId)) {
            makeSnackBar("Email Id already used! Try another one");
        } else {
            helper.putUser(name, emailId, password);
            setLoginStateId(emailId);
            makeSnackBar("SignUp Successful");
            navController.navigate(R.id.nav_dashboard);
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

    private boolean allDetailsEntered(String name, String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(name)) {
            makeSnackBar("Please Enter Name");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            makeSnackBar("Please Enter Email Id");
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            makeSnackBar("Please set password with minimum length of 6");
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {
            makeSnackBar("Please confirm with same password");
            return false;
        }
        return true;
    }

    private void makeSnackBar(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
