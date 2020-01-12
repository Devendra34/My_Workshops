package com.example.myworkshops.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkshops.R;
import com.example.myworkshops.adapters.DashboardScreenAdapter;
import com.example.myworkshops.databases.database_helpers.RegisteredWorkshopsDatabaseHelper;
import com.example.myworkshops.databases.database_helpers.UsersDatabaseHelper;
import com.example.myworkshops.databases.database_helpers.WorkshopsDatabaseHelper;
import com.example.myworkshops.model.Workshop;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private TextView heading;
    private RecyclerView recyclerView;
    private View view;

    private NavController navController;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        heading = view.findViewById(R.id.dashboard_heading);
        recyclerView = view.findViewById(R.id.dashboard_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setUserId();
        setHeading();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setApapter();
    }

    private void setApapter() {
        final RegisteredWorkshopsDatabaseHelper helper =
                new RegisteredWorkshopsDatabaseHelper(getContext());
        List<Integer> ids = helper.getRegisteredWorkshops(userId);
        WorkshopsDatabaseHelper databaseHelper = new
                WorkshopsDatabaseHelper(getContext());
        List<Workshop> workshops = new ArrayList<>();
        for (Integer id : ids) {
            Workshop workshop = databaseHelper.getWorkshopsListById((int) id);
            workshops.add(workshop);
        }
        recyclerView.setAdapter(new DashboardScreenAdapter(workshops));
    }

    private void setUserId() {
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences(getString(R.string.shared_preference_name),
                        Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            makeSnackBar("Error");
            return;
        }
        userId = sharedPreferences.getString(getString(R.string.login_state_key), null);
        if (userId == null) {
            navController.navigate(R.id.nav_log_in);
            makeSnackBar("Login Required");
        }
    }

    private void setHeading() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UsersDatabaseHelper helper = new UsersDatabaseHelper(getContext());
                String text = "Welcome back " + helper.getUserName(userId) + ",";
                heading.setText(text);
            }
        }).start();

    }

    private void makeSnackBar(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
