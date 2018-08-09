package com.rektapps.greendictionary.view.adapter.viewholder.factory.impl;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.LanguageItemBinding;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.impl.LanguageViewHolder;
import com.rektapps.greendictionary.viewmodel.LanguagesSelectViewModel;

import javax.inject.Inject;

public class LanguagesViewHolderFactory implements ViewModelViewHolderFactory<LanguagesSelectViewModel, Language> {

    @Inject
    public LanguagesViewHolderFactory() {
    }

    @Override
    public BaseViewModelViewHolder<LanguagesSelectViewModel, ? extends ViewDataBinding, Language> build(LanguagesSelectViewModel viewModel, ViewGroup parent) {
        LanguageItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.language_item, parent,false);
        return new LanguageViewHolder(viewModel, binding);
    }
}
