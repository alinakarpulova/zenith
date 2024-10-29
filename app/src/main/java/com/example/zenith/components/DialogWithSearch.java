package com.example.zenith.components;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.SearchView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.DialogRowAdapter;
import com.example.zenith.activities.adapters.DialogRowAdapterMulti;

import java.util.ArrayList;
import java.util.List;

public class DialogWithSearch<T extends SelectableItem> {
    private final List<T> itemList;
    private final Object selected;
    private final Dialog dialog;
    private final Context context;
    private SearchView searchView;
    private final boolean isMulti;
    Button dialogConfirm;
    RecyclerView recyclerView;


    public DialogWithSearch(List<T> itemList, Context context, boolean multi) {
        dialog = new Dialog(context);
        this.itemList = itemList;
        this.context = context;
        this.isMulti = multi;
        if (multi) {
            selected = new ArrayList<T>();
        } else {
            selected = null;
        }

    }


    public void showDialog(T checkedItem) {
        dialogSetup();
        DialogRowAdapter adapter = new DialogRowAdapter<>(itemList);

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
    }

    public void showDialog(List<T> checkedItems) {
        dialogSetup();

        DialogRowAdapterMulti adapter = new DialogRowAdapterMulti<>(itemList);

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

        for (T item : checkedItems) {
            adapter.setItemChecked(item, true);
            ((List<T>) selected).add(itemList.get(itemList.indexOf(item)));
        }

        dialogConfirm.setActivated(false);

        dialog.show();

        dialogConfirm.setOnClickListener((view) -> {
            ((List<T>) selected).clear(); // Clear previous selections
            ((List<T>) selected).addAll(adapter.getCheckedItems());
            dialog.dismiss();
        });

    }

    private void dialogSetup() {
        dialog.setContentView(R.layout.dialog_with_list_and_search);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_round));
        dialog.setCancelable(true);
        recyclerView = dialog.findViewById(R.id.dialog_list_view);
        dialogConfirm = dialog.findViewById(R.id.dialog_confirm_button);
    }


}
