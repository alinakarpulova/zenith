package com.example.zenith.activities.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.components.SelectableItem;

import java.util.ArrayList;
import java.util.List;

public class DialogRowAdapter<T extends SelectableItem> extends RecyclerView.Adapter<DialogRowAdapter.ViewHolder> {
    private final List<T> items;
    private Filter filter;
    private final List<T> filteredItems;
    private Integer checkedItem;

    public DialogRowAdapter(List<T> items) {
        this.items = items;
        this.filteredItems = new ArrayList<>(items);
        this.checkedItem = null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView category;
        private final RadioButton radioButton;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.dialog_item_name);
            category = view.findViewById(R.id.dialog_item_subheading);
            radioButton = view.findViewById(R.id.dialog_radio_button);
        }

        public TextView getName() {
            return name;
        }

        public TextView getCategory() {
            return category;
        }

        public RadioButton getRadioBtn() {
            return radioButton;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        T item = filteredItems.get(position);
        viewHolder.getName().setText(item.getHeading());
        viewHolder.getCategory().setText(item.getSubheading());

        viewHolder.getRadioBtn().setChecked(checkedItem != null && position == checkedItem);

        viewHolder.getRadioBtn().setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                // Uncheck the previously selected item
                if (checkedItem != null && checkedItem != position) {
                    int previousChecked = checkedItem;
                    checkedItem = position;
                    notifyItemChanged(previousChecked);
                } else {
                    checkedItem = position;
                }
            } else if (checkedItem != null && checkedItem == position) {
                checkedItem = null; // Clear the selection if unchecked
            }
        });
    }


    @Override
    public long getItemId(int i) {
        return filteredItems.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new ItemFilter();
        }
        return filter;
    }

    public void setItemChecked(T itemChecked, Boolean checked) {
        int index = filteredItems.indexOf(itemChecked);
        if (index != -1) {
            checkedItem = index;
            notifyItemChanged(index);
        }
    }

    public T getCheckedItem() {
        if (checkedItem != null) {
            return filteredItems.get(checkedItem);
        }
        return null;
    }

    private class ItemFilter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterPattern = constraint != null ? constraint.toString().toLowerCase().trim() : "";

            List<T> filteredList = new ArrayList<>();

            if (filterPattern.isEmpty()) {
                // If no filter is applied, return the original items
                filteredList.addAll(items);
            } else {
                // Filter by name and category
                for (T item : items) {
                    if (item.getHeading().toLowerCase().contains(filterPattern.toLowerCase()) ||
                            item.getSubheading().toLowerCase().contains(filterPattern.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,@NonNull FilterResults results) {
            if (results.values != null) {
                List<T> newFilteredList = (List<T>) results.values;

                // Clear and update the filteredItems list
                filteredItems.clear();
                filteredItems.addAll(newFilteredList);

                // Reset checkedItem if it no longer exists in filteredItems
                if (checkedItem != null && (checkedItem >= filteredItems.size() || !filteredItems.contains(items.get(checkedItem)))) {
                    checkedItem = null;
                }

                // Notify adapter about data change
                notifyDataSetChanged();
            } else {
                Log.w("DialogRowAdapter", "Filter results returned null");
            }
        }
    }

}
