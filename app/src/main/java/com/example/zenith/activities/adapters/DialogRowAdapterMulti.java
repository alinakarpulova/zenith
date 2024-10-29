package com.example.zenith.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zenith.R;
import com.example.zenith.components.SelectableItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DialogRowAdapterMulti<T extends SelectableItem> extends RecyclerView.Adapter<DialogRowAdapterMulti.ViewHolder> {
    private List<T> items;
    private Filter filter;
    private List<T> filteredItems;
    private HashMap<Integer, Boolean> checkedStates; // Track checked state for each item

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView category;
        private final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.dialog_item_name);
            category = view.findViewById(R.id.dialog_item_subheading);
            checkBox = view.findViewById(R.id.dialog_item_checkbox);
        }

        public TextView getName() {
            return name;
        }

        public TextView getCategory() {
            return category;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }

    public DialogRowAdapterMulti(List<T> items) {
        this.items = items;
        this.filteredItems = new ArrayList<>(items);
        this.checkedStates = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.checked_item_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getName().setText(filteredItems.get(position).getHeading());
        viewHolder.getCategory().setText(filteredItems.get(position).getSubheading());

        // Set the checked state based on the tracked value
        viewHolder.getCheckBox().setChecked(checkedStates.getOrDefault(position, false));

        // Handle the checkbox click event
        viewHolder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkedStates.put(position, isChecked); // Update the checked state
        });

        // Handle item click to toggle the checkbox
        viewHolder.itemView.setOnClickListener(v -> {
            boolean isChecked = !checkedStates.getOrDefault(position, false);
            checkedStates.put(position, isChecked); // Update the checked state
            viewHolder.getCheckBox().setChecked(isChecked); // Set the checkbox state
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
            checkedStates.put(index, checked);
            notifyItemChanged(index); // Notify the adapter of the change
        }
    }

    public List<T> getCheckedItems() {
        List<T> checkedItems = new ArrayList<>();
        for (int i = 0; i < filteredItems.size(); i++) {
            if (checkedStates.getOrDefault(i, false)) {
                checkedItems.add(filteredItems.get(i));
            }
        }
        return checkedItems;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterPattern = constraint != null ? constraint.toString().toLowerCase().trim() : "";

            FilterResults results = new FilterResults();
            List<T> filteredList = new ArrayList<>();

            if (filterPattern.isEmpty()) {
                // If no filter is applied, return the original items
                filteredList.addAll(items);
            } else {
                // Filter by name and category
                for (T item : items) {
                    if (item.getHeading().toLowerCase().contains(filterPattern) ||
                            item.getSubheading().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems.clear();
            filteredItems.addAll((Collection<? extends T>) results.values);
            notifyDataSetChanged(); // Notify adapter about data change
        }
    }

}
