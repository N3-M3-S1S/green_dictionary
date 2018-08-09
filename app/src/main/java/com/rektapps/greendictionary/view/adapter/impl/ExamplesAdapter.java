package com.rektapps.greendictionary.view.adapter.impl;

import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.view.adapter.BaseViewModelRecyclerAdapter;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import javax.inject.Inject;


public class ExamplesAdapter extends BaseViewModelRecyclerAdapter<Example, EntryScreenViewModel> {

    @Inject
    ExamplesAdapter(ViewModelViewHolderFactory<EntryScreenViewModel, Example> factory) {
        super(factory);
    }
}
