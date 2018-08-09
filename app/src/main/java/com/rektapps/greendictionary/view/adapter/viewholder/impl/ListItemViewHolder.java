package com.rektapps.greendictionary.view.adapter.viewholder.impl;

import com.rektapps.greendictionary.greendictionary.databinding.DictionaryListItemBinding;
import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;
import com.rektapps.greendictionary.view.displayitem.ListItem;
import com.rektapps.greendictionary.viewmodel.DictionaryListViewModel;

public class ListItemViewHolder extends BaseViewModelViewHolder<DictionaryListViewModel,DictionaryListItemBinding, ListItem> {

    public ListItemViewHolder(DictionaryListViewModel viewModel, DictionaryListItemBinding dataBinding) {
        super(viewModel, dataBinding);
    }

    public void setDeleteButtonVisible(boolean isVisible){
        dataBinding.setIsDeleteButtonVisible(isVisible);
    }

    @Override
    public void bind(ListItem listItem) {
        dataBinding.setListItem(listItem);
        dataBinding.setViewmodel(viewModel);
    }
}
