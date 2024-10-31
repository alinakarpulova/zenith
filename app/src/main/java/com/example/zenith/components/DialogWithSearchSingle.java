package com.example.zenith.components;

import android.content.Context;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.DialogRowAdapter;
import com.example.zenith.activities.adapters.DialogRowAdapterMulti;
import com.example.zenith.models.Exercise;

import java.util.List;

public class DialogWithSearchSingle<T extends SelectableItem> extends DialogWithSearch<T> {
    private T selectedItem;

    public DialogWithSearchSingle(List<T> itemList, Context context) {
        super(itemList, context);
    }

    public void showDialog() {
        super.showDialog();
        // Handle checked items
        DialogRowAdapter adapter = new DialogRowAdapter<>(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        Button dialogConfirm = dialog.findViewById(R.id.dialog_confirm_button);
        dialogConfirm.setActivated(false);

        dialogConfirm.setOnClickListener((view) -> {
            selectedItem = (T) adapter.getCheckedItem();
            dialog.dismiss();
        });

        dialog.show();
    }

    public T getSelectedItem() {
        return selectedItem;
    }
}
