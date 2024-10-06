package com.example.zenith.components;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.ExerciseDialogRowAdapter;
import com.example.zenith.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class DialogWithSearchMulti {
    private final List<Exercise> itemList;
    private final List<Exercise> selectedItems;
    private final Dialog dialog;
    private final Context context;
    private SearchView searchView;

    public DialogWithSearchMulti(List<Exercise> itemList, Context context) {
        dialog = new Dialog(context);
        this.itemList = itemList;
        this.context = context;
        selectedItems = new ArrayList<>();
    }

    public void showDialog(List<Exercise> checkedItems) {
        dialog.setContentView(R.layout.dialog_with_list_and_search);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_round));
        dialog.setCancelable(true);
        RecyclerView recyclerView = dialog.findViewById(R.id.dialog_list_view);
        ExerciseDialogRowAdapter adapter = new ExerciseDialogRowAdapter(itemList.toArray(new Exercise[0]));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        searchView = dialog.findViewById(R.id.dialog_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });


        for (Exercise item : checkedItems) {
            adapter.setItemChecked(item, true);
            selectedItems.add(itemList.get(itemList.indexOf(item)));
        }

        Button dialogConfirm = dialog.findViewById(R.id.dialog_confirm_button);

        dialogConfirm.setActivated(false);

        dialog.show();

        dialogConfirm.setOnClickListener((view) -> {
            selectedItems.clear(); // Clear previous selections
            selectedItems.addAll(adapter.getCheckedExercises());
            dialog.dismiss();
        });
    }

    public Dialog getDialog() {
        return dialog;
    }

    public List<Exercise> getSelectedItems() {
        return selectedItems;
    }
}
