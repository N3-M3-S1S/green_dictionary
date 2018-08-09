package com.rektapps.greendictionary.view.adapter.viewholder.factory.impl;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.ExampleBinding;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.impl.ExampleViewHolder;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import javax.inject.Inject;

public class ExamplesViewHolderFactory implements ViewModelViewHolderFactory<EntryScreenViewModel, Example> {
    private boolean isBackgroundGray = false;

    @Inject
    public ExamplesViewHolderFactory() {

    }

    @Override
    public BaseViewModelViewHolder<EntryScreenViewModel, ? extends ViewDataBinding, Example> build(EntryScreenViewModel viewModel, ViewGroup parent) {
        ExampleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.example, parent, false);
        ExampleViewHolder vh = new ExampleViewHolder(binding, viewModel);
        if (isBackgroundGray)
            vh.setBackgroundColorGray();
        isBackgroundGray = !isBackgroundGray;
        return new ExampleViewHolder(binding, viewModel);
    }
}
