package com.rektapps.greendictionary.view.adapter.viewholder.factory.impl;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.DictionaryListItemBinding;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.impl.ListItemViewHolder;
import com.rektapps.greendictionary.view.displayitem.ListItem;
import com.rektapps.greendictionary.viewmodel.DictionaryListViewModel;

import javax.inject.Inject;

public class ListItemViewHolderFactory implements ViewModelViewHolderFactory<DictionaryListViewModel,ListItem> {

    @Inject
    public ListItemViewHolderFactory() {
    }

    @Override
    public ListItemViewHolder build(DictionaryListViewModel viewModel, ViewGroup parent) {
        DictionaryListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.dictionary_list_item, parent, false);
        return new ListItemViewHolder(viewModel, binding);
    }
}
