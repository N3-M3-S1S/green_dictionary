package com.rektapps.greendictionary.view.adapter.viewholder;

import android.support.v4.content.ContextCompat;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.ExampleBinding;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

public class ExampleViewHolder extends DataBindingViewHolder<ExampleBinding> {
    public ExampleViewHolder(ExampleBinding dataBinding) {
        super(dataBinding);
    }

    public void bind(Example example, EntryScreenViewModel viewModel) {
        dataBinding.setExample(example);
        dataBinding.setViewmodel(viewModel);
    }

    public void setBackgroundColorGray() {
        dataBinding.getRoot().setBackgroundColor(ContextCompat.getColor(dataBinding.getRoot().getContext(), R.color.exampleGrayColor));
    }


}
