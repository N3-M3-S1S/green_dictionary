package com.rektapps.greendictionary.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.DictionaryListBinding;
import com.rektapps.greendictionary.view.adapter.impl.ListAdapter;
import com.rektapps.greendictionary.viewmodel.DictionaryListViewModel;
import com.rektapps.greendictionary.viewmodel.shared.ListCountViewModel;
import com.rektapps.greendictionary.viewmodel.shared.SelectedListItemsCountViewModel;
import com.rektapps.greendictionary.viewmodel.shared.eventviewmodel.MultipleSelectionEventViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class DictionaryListFragment extends DaggerFragment {
    private static String LIST_TYPE_KEY = "type";
    private ListType listType;
    private DictionaryListBinding itemsListBinding;
    private DictionaryListViewModel viewModel;
    private ListCountViewModel countViewModel;
    private SelectedListItemsCountViewModel selectedCountViewModel;

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    ListAdapter listAdapter;

    public static DictionaryListFragment getInstance(ListType listType) {
        DictionaryListFragment fragment = new DictionaryListFragment();
        Bundle args = new Bundle();
        args.putSerializable(LIST_TYPE_KEY, listType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listType = (ListType) getArguments().getSerializable(LIST_TYPE_KEY);
        viewModel = ViewModelProviders.of(this, factory).get(DictionaryListViewModel.class);
        selectedCountViewModel = ViewModelProviders.of(getActivity()).get(SelectedListItemsCountViewModel.class);
        countViewModel = ViewModelProviders.of(getActivity()).get(ListCountViewModel.class);

        MultipleSelectionEventViewModel multipleSelectionEventViewModel = ViewModelProviders.of(getActivity()).get(MultipleSelectionEventViewModel.class);

        multipleSelectionEventViewModel.getMultipleSelectionEventLiveData(listType).observe(this, event -> viewModel.onMultipleSelectionEvent(event));

        listAdapter.setViewModel(viewModel);

        viewModel.getList(listType).observe(this, listItems -> {
            listAdapter.submitList(listItems);
            itemsListBinding.setIsListVisible(listItems.size() != 0);
            countViewModel.setCount(listType, listItems.size());
        });

        viewModel.getIsMultipleSelectionActiveLiveData().observe(this, isMultipleSelectionActive -> listAdapter.setIsMultipleSelectionActive(isMultipleSelectionActive));

        viewModel.getSelectedCountChangedEventLiveData().observe(this, selectedCount -> selectedCountViewModel.setSelectedCount(selectedCount));

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemsListBinding = DataBindingUtil.inflate(inflater, R.layout.dictionary_list, container, false);
        itemsListBinding.setIsListVisible(true);
        ((SimpleItemAnimator) itemsListBinding.entriesRv.getItemAnimator()).setSupportsChangeAnimations(false); //remove blinks when items change
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemsListBinding.entriesRv.addItemDecoration(divider);
        itemsListBinding.entriesRv.setAdapter(listAdapter);
        return itemsListBinding.getRoot();
    }


}
