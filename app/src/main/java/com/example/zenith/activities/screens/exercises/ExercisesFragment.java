package com.example.zenith.activities.screens.exercises;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.ExerciseRowAdapter;
import com.example.zenith.controllers.DatabaseHelper;
import com.example.zenith.controllers.ExerciseController;
import com.example.zenith.models.Exercise;

public class ExercisesFragment extends Fragment {
    private ExerciseController exerciseController;
    private RecyclerView recyclerView;
    private ExerciseRowAdapter adapter;

    public ExercisesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        exerciseController = new ExerciseController(new DatabaseHelper(getContext()));
        View view = inflater.inflate(R.layout.exercises_fragment, container, false);
        Toolbar toolbar = view.findViewById(R.id.static_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Exercises");
        recyclerView = view.findViewById(R.id.exercises_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        updateList();

        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.exercise_menu, menu);

                // Set the icon tint based on the theme
                int iconTint = ContextCompat.getColor(requireContext(), R.color.iconTint);
                menu.getItem(0).getIcon().setTint(iconTint);

                // Set up the SearchView
                MenuItem searchMenuItem = menu.findItem(R.id.exercise_search);
                SearchView searchView = (SearchView) searchMenuItem.getActionView();
                searchView.setQueryHint("Search...");

                // Set listener for search query text changes
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // Handle search query submission
                        return false; // Return true if the query is handled
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false; // Return true if the query is handled
                    }
                });
            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                int menu_id = menuItem.getItemId();
                if (menu_id == R.id.exercise_create) {
                    Intent intent = ExerciseNew.makeIntent(getContext());
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }

        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

    }


    private void updateList() {
        adapter = new ExerciseRowAdapter(exerciseController.getExerciseList().toArray(new Exercise[0]), getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }
}
