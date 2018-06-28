package com.rektapps.greendictionary.view.adapter.viewholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public abstract class DataBindingViewHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    protected DB dataBinding;

    DataBindingViewHolder(DB dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
    }


}
