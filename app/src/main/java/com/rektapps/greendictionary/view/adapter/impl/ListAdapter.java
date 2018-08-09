package com.rektapps.greendictionary.view.adapter.impl;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.ViewGroup;

import com.rektapps.greendictionary.view.adapter.viewholder.factory.impl.ListItemViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.impl.ListItemViewHolder;
import com.rektapps.greendictionary.view.displayitem.ListItem;
import com.rektapps.greendictionary.viewmodel.DictionaryListViewModel;

import javax.inject.Inject;


public class ListAdapter extends PagedListAdapter<ListItem, ListItemViewHolder> {
    private DictionaryListViewModel viewModel;
    private boolean isMultipleSelectionActive;
    private ListItemViewHolderFactory factory;


    @Inject
    ListAdapter(ListItemViewHolderFactory  factory) {
        super(new DiffCallback());
        this.factory = factory;
    }


    public void setIsMultipleSelectionActive(boolean isMultipleSelectionActive){
        this.isMultipleSelectionActive = isMultipleSelectionActive;
        notifyItemRangeChanged(0, getItemCount());
    }


    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return factory.build(viewModel,parent);
    }


    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        holder.bind(getItem(position));
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
