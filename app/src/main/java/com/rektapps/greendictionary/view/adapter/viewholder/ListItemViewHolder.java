package com.rektapps.greendictionary.view.adapter.viewholder;

import com.rektapps.greendictionary.greendictionary.databinding.DictionaryListItemBinding;
import com.rektapps.greendictionary.view.displayitem.ListItem;
import com.rektapps.greendictionary.viewmodel.DictionaryListViewModel;

public class ListItemViewHolder extends DataBindingViewHolder<DictionaryListItemBinding> {

    public ListItemViewHolder(DictionaryListItemBinding dataBinding) {
        super(dataBinding);
    }

    public void bind(ListItem listItem, DictionaryListViewModel dictionaryListViewModel) {
        dataBinding.setListItem(listItem);
        dataBinding.setViewmodel(dictionaryListViewModel);
    }

    public void setDeleteButtonVisible(boolean isVisible){
        dataBinding.setIsDeleteButtonVisible(isVisible);
    }


}
