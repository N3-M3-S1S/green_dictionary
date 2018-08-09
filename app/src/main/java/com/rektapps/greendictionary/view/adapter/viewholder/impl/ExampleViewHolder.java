package com.rektapps.greendictionary.view.adapter.viewholder.impl;

import android.support.v4.content.ContextCompat;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.ExampleBinding;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.view.adapter.viewholder.EntryScreenTextViewHolder;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

public class ExampleViewHolder extends EntryScreenTextViewHolder<ExampleBinding, Example> {


    public ExampleViewHolder(ExampleBinding dataBinding, EntryScreenViewModel entryScreenViewModel) {
        super(entryScreenViewModel, dataBinding);
    }

    @Override
    public void bind(Example example) {
        dataBinding.setExample(example);
        copyTextToClipboardOnLongClick(dataBinding.firstExample, example.getFirstExample());
        copyTextToClipboardOnLongClick(dataBinding.secondExample, example.getSecondExample());

    }

    public void setBackgroundColorGray() {
        dataBinding.getRoot().setBackgroundColor(ContextCompat.getColor(dataBinding.getRoot().getContext(), R.color.exampleGrayColor));
    }


}
