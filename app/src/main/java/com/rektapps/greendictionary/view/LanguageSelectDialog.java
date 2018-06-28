package com.rektapps.greendictionary.view;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.LanguageListBinding;
import com.rektapps.greendictionary.model.language.LanguageType;
import com.rektapps.greendictionary.view.adapter.LanguagesAdapter;
import com.rektapps.greendictionary.viewmodel.LanguagesSelectViewModel;
import com.rektapps.greendictionary.viewmodel.shared.eventviewmodel.LanguageSelectionEventViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatDialogFragment;

public class LanguageSelectDialog extends DaggerAppCompatDialogFragment {
    private static String LANGUAGE_TYPE_KEY = "lng";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LanguageType languageType;
    private LanguagesSelectViewModel viewModel;
    private LanguagesAdapter adapter;

    public static LanguageSelectDialog getInstance(LanguageType languageType) {
        LanguageSelectDialog languageSelectDialog = new LanguageSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LANGUAGE_TYPE_KEY, languageType);
        languageSelectDialog.setArguments(bundle);
        return languageSelectDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        languageType = (LanguageType) getArguments().getSerializable(LANGUAGE_TYPE_KEY);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LanguagesSelectViewModel.class);
        viewModel.getLanguagesLiveData().observe(this, languages -> adapter.setLanguages(languages));

        adapter = new LanguagesAdapter();
        adapter.setViewModel(viewModel);

        LanguageSelectionEventViewModel languageSelectionEventViewModel = ViewModelProviders.of(getActivity()).get(LanguageSelectionEventViewModel.class);
        viewModel.getLanguageSelectedEvent().observe(this, selectedLanguage -> {
            languageSelectionEventViewModel.onLanguageSelected(languageType, selectedLanguage);
            dismiss();
        });

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return d;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LanguageListBinding languageDialogBinding = DataBindingUtil.inflate(inflater, R.layout.language_list, container, false);
        languageDialogBinding.setLanguageType(languageType);
        languageDialogBinding.languagesToolbar.inflateMenu(R.menu.language_dialog_menu);
        languageDialogBinding.languagesRv.setAdapter(adapter);
        SearchView searchView = (SearchView) languageDialogBinding.languagesToolbar.getMenu().findItem(R.id.language_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.filterLanguages(newText);
                return true;
            }
        });
        return languageDialogBinding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}


