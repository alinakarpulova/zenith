package com.example.zenith.components;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.widget.SearchView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.DialogRowAdapter;
import com.example.zenith.activities.adapters.DialogRowAdapterMulti;

import java.util.List;

public class DialogWithSearch<T extends SelectableItem> {
    protected final List<T> itemList;
    protected final Dialog dialog;
    protected final Context context;
    protected SearchView searchView;
    RecyclerView recyclerView;
    private final DialogRowAdapter<T> adapter;


    public DialogWithSearch(List<T> itemList, Context context) {
        dialog = new Dialog(context);
        this.itemList = itemList;
        this.context = context;
        adapter = new DialogRowAdapter<>(itemList);
    }


    public void showDialog() {
        dialogSetup();

        recyclerView = dialog.findViewById(R.id.dialog_list_view);
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
        dialog.show();
    }

    private void dialogSetup() {
        dialog.setContentView(R.layout.dialog_with_list_and_search);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_round));
        dialog.setCancelable(true);
    }

    public Dialog getDialog() {
        return dialog;
    }
}
