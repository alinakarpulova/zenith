package com.example.zenith.components;

import android.app.Dialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.zenith.R;

import java.util.List;

public class GenericDialog<T> {
    private final String dialogTitle;
    private final List<T> itemList;
    private T selectedItem;
    private final Dialog dialog;
    private final Context context;

    public GenericDialog(String dialogTitle, List<T> itemList, Context context) {
        dialog = new Dialog(context);
        this.dialogTitle = dialogTitle;
        this.itemList = itemList;
        this.context = context;
    }

    public void showDialog(T checkedItem) {
        dialog.setContentView(R.layout.dialog_with_list);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_round));
        dialog.setCancelable(true);


        TextView title = dialog.findViewById(R.id.dialog_title);
        title.setText(this.dialogTitle);

        ListView listView = dialog.findViewById(R.id.dialog_list_view);

        ArrayAdapter<T> adapter = new ArrayAdapter<>(
                context,
                R.layout.checked_list_item,
                itemList
        );

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if (checkedItem != null) {
            listView.setItemChecked(itemList.indexOf(checkedItem), true);
            selectedItem = itemList.get(itemList.indexOf(checkedItem));
        }
        Button dialogConfirm = dialog.findViewById(R.id.dialog_confirm_button);

        dialogConfirm.setActivated(false);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedItem = itemList.get(position);
            System.out.println(selectedItem);
            dialogConfirm.setActivated(true);
        });

        dialog.show();

        dialogConfirm.setOnClickListener((view) -> {
            dialog.dismiss();
        });
    }

    public Dialog getDialog() {
        return dialog;
    }

    public T getSelectedItem() {
        return selectedItem;
    }
}
