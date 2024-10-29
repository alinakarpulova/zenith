package com.example.zenith.components;

import android.content.Context;
import android.widget.Button;

import com.example.zenith.R;
import com.example.zenith.activities.adapters.DialogRowAdapterMulti;
import com.example.zenith.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class DialogWithSearchMulti<T extends SelectableItem> extends DialogWithSearch<T> {
    private final List<T> selectedItems;

    public DialogWithSearchMulti(List<T> itemList, Context context) {
        super(itemList, context);
        selectedItems = new ArrayList<>();
    }

    public void showDialog(List<T> checkedItems) {
        super.showDialog();
        // Handle checked items
        DialogRowAdapterMulti<T> adapter = (DialogRowAdapterMulti<T>) recyclerView.getAdapter();
        if (adapter != null) {
            for (T item : checkedItems) {
                adapter.setItemChecked(item, true);
                selectedItems.add(itemList.get(itemList.indexOf(item)));
            }
        }

        Button dialogConfirm = dialog.findViewById(R.id.dialog_confirm_button);
        dialogConfirm.setActivated(false);

        dialogConfirm.setOnClickListener((view) -> {
            selectedItems.clear(); // Clear previous selections
            selectedItems.addAll(adapter.getCheckedItems());
            dialog.dismiss();
        });

        dialog.show();
    }

    public List<T> getSelectedItems() {
        return selectedItems;
    }
}
