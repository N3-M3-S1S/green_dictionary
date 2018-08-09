package com.rektapps.greendictionary.view.adapter.viewholder.factory.impl;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.TranslationWithMeaningsBinding;
import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.impl.TranslationWithMeaningsViewHolder;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import javax.inject.Inject;

public class TranslationWithMeaningsViewHolderFactory implements ViewModelViewHolderFactory<EntryScreenViewModel, Translation> {

    @Inject
    TranslationWithMeaningsViewHolderFactory() {

    }

    @Override
    public BaseViewModelViewHolder<EntryScreenViewModel, ? extends ViewDataBinding, Translation> build(EntryScreenViewModel viewModel, ViewGroup parent) {
        TranslationWithMeaningsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.translation_with_meanings, parent, false);
        return new TranslationWithMeaningsViewHolder(binding, viewModel);
    }
}
