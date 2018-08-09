package com.rektapps.greendictionary.view.adapter.viewholder;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

public abstract class EntryScreenTextViewHolder<DB extends ViewDataBinding, T> extends BaseViewModelViewHolder<EntryScreenViewModel, DB, T> {

    protected EntryScreenTextViewHolder(EntryScreenViewModel viewModel, DB dataBinding) {
        super(viewModel, dataBinding);
    }

    protected void copyTextToClipboardOnLongClick(View longClickView, String textToCopy) {
        longClickView.setOnLongClickListener(view -> viewModel.copyTextToClipboardOnLongClick(textToCopy));
    }

}
