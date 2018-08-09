package com.rektapps.greendictionary.view.adapter.viewholder.factory;

import android.arch.lifecycle.ViewModel;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;

public interface ViewModelViewHolderFactory<VM extends ViewModel, T> {
    BaseViewModelViewHolder<VM, ? extends ViewDataBinding, T> build(VM viewModel, ViewGroup parent);
}
