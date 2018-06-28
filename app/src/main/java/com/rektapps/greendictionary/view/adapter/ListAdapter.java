package com.rektapps.greendictionary.view.adapter;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.view.adapter.viewholder.ListItemViewHolder;
import com.rektapps.greendictionary.view.displayitem.ListItem;
import com.rektapps.greendictionary.viewmodel.DictionaryListViewModel;


public class ListAdapter extends PagedListAdapter<ListItem, ListItemViewHolder> {
    private DictionaryListViewModel viewModel;
    private boolean isMultipleSelectionActive;

    public ListAdapter() {
        super(new DiffCallback());
    }


    public void setIsMultipleSelectionActive(boolean isMultipleSelectionActive){
        this.isMultipleSelectionActive = isMultipleSelectionActive;
        notifyItemRangeChanged(0, getItemCount());
    }



    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.dictionary_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        ListItem listItem = getItem(position);
        holder.bind(listItem, viewModel);
        holder.setDeleteButtonVisible(isMultipleSelectionActive);
    }

    public void setViewModel(DictionaryListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<ListItem> {

        @Override
        public boolean areItemsTheSame(ListItem oldItem, ListItem newItem) {
            return oldItem.getEntry().getId() == newItem.getEntry().getId();
        }

        @Override
        public boolean areContentsTheSame(ListItem oldItem, ListItem newItem) {
            return (oldItem.getEntry().getId() == newItem.getEntry().getId()) && (oldItem.getEntry().isFavorite()
                    == newItem.getEntry().isFavorite()) && (oldItem.getEntry().isHistory() == newItem.getEntry().isHistory());
        }
    }

}
