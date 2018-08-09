package com.rektapps.greendictionary.view;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.EntryScreenBinding;
import com.rektapps.greendictionary.view.adapter.impl.ExamplesAdapter;
import com.rektapps.greendictionary.view.adapter.impl.TranslationsAdapter;
import com.rektapps.greendictionary.view.displayitem.EntryScreenItem;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class EntryScreen extends BottomSheetDialogFragment {
    private static final String ENTRY_KEY = "entry";
    private EntryScreenBinding entryScreenBinding;
    private EntryScreenViewModel viewModel;
    private EntryScreenItem entryScreenItem;
    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    ExamplesAdapter examplesAdapter;
    @Inject
    TranslationsAdapter translationsAdapter;

    public static DialogFragment getInstance(EntryScreenItem entryScreenItem) {
        DialogFragment fragment = new EntryScreen();
        Bundle args = new Bundle();
        args.putSerializable(ENTRY_KEY, entryScreenItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        entryScreenItem = ((EntryScreenItem) getArguments().getSerializable(ENTRY_KEY));
        viewModel = ViewModelProviders.of(this, factory).get(EntryScreenViewModel.class);
        viewModel.setEntryScreenItem(entryScreenItem);
        getLifecycle().addObserver(viewModel);
        viewModel.getIsEntryFavoriteLiveData().observe(this, this::setFavoriteIcon);
        viewModel.getCopiedToClipboardEvent().observe(this, event -> Toast.makeText(getActivity(), R.string.copiedToClipboard, Toast.LENGTH_SHORT).show());
        initializeAdapters();
    }


    private void initializeAdapters(){
        examplesAdapter.setViewModel(viewModel);
        translationsAdapter.setViewModel(viewModel);
        examplesAdapter.setItems(entryScreenItem.getExamples());
        translationsAdapter.setItems(entryScreenItem.getTranslations());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        entryScreenBinding = DataBindingUtil.inflate(inflater, R.layout.entry_screen, container, false);
        entryScreenBinding.setEntryScreenItem(entryScreenItem);
        entryScreenBinding.examplesRv.setNestedScrollingEnabled(false);
        entryScreenBinding.translationsRv.setNestedScrollingEnabled(false);
        entryScreenBinding.translationsRv.setAdapter(translationsAdapter);
        entryScreenBinding.examplesRv.setAdapter(examplesAdapter);
        entryScreenBinding.entryScreenClose.setOnClickListener(v -> dismiss());
        entryScreenBinding.entryScreenToolbar.inflateMenu(R.menu.entry_menu);
        entryScreenBinding.entryScreenToolbar.setOnMenuItemClickListener(item -> {
            viewModel.onMenuItemClicked(item.getItemId());
            return true;
        });
        return entryScreenBinding.getRoot();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //set bottom sheet expanded by default
        dialog.setOnShowListener(v -> {
            BottomSheetDialog d = (BottomSheetDialog) v;
            View bottomSheetInternal = d.findViewById(android.support.design.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return dialog;
    }

    private void setFavoriteIcon(boolean isFavorite) {
        MenuItem favorite = entryScreenBinding.entryScreenToolbar.getMenu().findItem(R.id.entry_favorite);
        int iconId = isFavorite ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp;
        favorite.setIcon(iconId);
        //if not call mutate then icon in favorite tab also changes its color
        Drawable icon = favorite.getIcon().mutate();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable compat = DrawableCompat.wrap(icon);
            DrawableCompat.setTint(compat, ContextCompat.getColor(getContext(), R.color.colorAccent));
        } else {
            icon.setTint(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }
    }

}
