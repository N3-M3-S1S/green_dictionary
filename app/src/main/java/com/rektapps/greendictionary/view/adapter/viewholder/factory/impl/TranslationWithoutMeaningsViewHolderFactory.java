package com.rektapps.greendictionary.view.adapter.viewholder.factory.impl;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.TranslationBinding;
import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.impl.TranslationWithoutMeaningViewHolder;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import javax.inject.Inject;

public class TranslationWithoutMeaningsViewHolderFactory implements ViewModelViewHolderFactory<EntryScreenViewModel, Translation> {

    @Inject
    TranslationWithoutMeaningsViewHolderFactory() {
    }


    @Override
    public BaseViewModelViewHolder<EntryScreenViewModel, ? extends ViewDataBinding, Translation> build(EntryScreenViewModel viewModel, ViewGroup parent) {
        TranslationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.translation, parent, false);
        return new TranslationWithoutMeaningViewHolder(binding, viewModel);
    }
}
