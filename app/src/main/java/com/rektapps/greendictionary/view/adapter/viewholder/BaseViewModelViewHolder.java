package com.rektapps.greendictionary.view.adapter.viewholder;

import android.arch.lifecycle.ViewModel;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public abstract class BaseViewModelViewHolder<VM extends ViewModel, DB extends ViewDataBinding, T> extends RecyclerView.ViewHolder {
    protected VM viewModel;
    protected DB dataBinding;

    protected BaseViewModelViewHolder(VM viewModel, DB dataBinding) {
        super(dataBinding.getRoot());
        this.viewModel = viewModel;
        this.dataBinding = dataBinding;
    }

    public abstract void bind(T t);
}
