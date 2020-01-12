package com.example.myworkshops.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkshops.R;
import com.example.myworkshops.adapters.BrowseWorkshopScreenAdapter;
import com.example.myworkshops.databases.database_helpers.RegisteredWorkshopsDatabaseHelper;
import com.example.myworkshops.databases.database_helpers.WorkshopsDatabaseHelper;
import com.example.myworkshops.model.Workshop;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BrowseWorkshopsFragment extends Fragment implements BrowseWorkshopScreenAdapter.OnAppyListener {

    private RecyclerView recyclerView;
    private View view;
    private NavController navController;

    private List<Workshop> workshops;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_browse_workshops, container, false);
        recyclerView = view.findViewById(R.id.all_workshops_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter();
    }

    @Override
    public void setOnApplyListener(int id) {
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences(getResources().getString(R.string.shared_preference_name),
                        Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            Snackbar.make(view, "Error!", Snackbar.LENGTH_SHORT).show();
            return;
        }
        String emailId = sharedPreferences.getString(getString(R.string.login_state_key), null);
        if (emailId == null) {
            Snackbar.make(view, "Please Login First", Snackbar.LENGTH_SHORT).show();
            navController.navigate(R.id.nav_log_in);
        } else {
            RegisteredWorkshopsDatabaseHelper helper =
                    new RegisteredWorkshopsDatabaseHelper(getContext());

            if (helper.alreadyRegisterd(emailId, id)) {
                makeSnackbar("Already applied to this workshop");
            } else {
                helper.registerWorkshop(emailId, id);
                makeSnackbar("Applying Successful");
            }
        }
    }

    private void setAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WorkshopsDatabaseHelper helper = new WorkshopsDatabaseHelper(getContext());
                workshops = helper.getWorkshopsList();
                recyclerView.setAdapter(new BrowseWorkshopScreenAdapter(BrowseWorkshopsFragment.this, workshops));
            }
        }).start();
    }

    private void makeSnackbar(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
