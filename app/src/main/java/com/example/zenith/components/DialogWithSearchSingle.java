package com.example.zenith.components;

import android.content.Context;
import android.widget.Button;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.DialogRowAdapter;

import java.util.List;

public class DialogWithSearchSingle<T extends SelectableItem> extends DialogWithSearch<T> {
    private T selectedItem;

    public DialogWithSearchSingle(List<T> itemList, Context context) {
        super(itemList, context);
    }

    public void showDialog() {
        super.showDialog();
        // Handle checked items
        DialogRowAdapter<T> adapter = (DialogRowAdapter<T>) recyclerView.getAdapter();

        Button dialogConfirm = dialog.findViewById(R.id.dialog_confirm_button);
        dialogConfirm.setActivated(false);

        dialogConfirm.setOnClickListener((view) -> {
            selectedItem = adapter.getCheckedItem();
            dialog.dismiss();
        });

        dialog.show();
    }

    public T getSelectedItem() {
        return selectedItem;
    }
}
