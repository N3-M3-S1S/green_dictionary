package com.rektapps.greendictionary.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.MainViewBinding;
import com.rektapps.greendictionary.model.language.LanguageType;
import com.rektapps.greendictionary.view.adapter.DictionaryListFragmentPagerAdapter;
import com.rektapps.greendictionary.viewmodel.MainViewViewModel;
import com.rektapps.greendictionary.viewmodel.shared.ListCountViewModel;
import com.rektapps.greendictionary.viewmodel.shared.SelectedListItemsCountViewModel;
import com.rektapps.greendictionary.viewmodel.shared.eventviewmodel.LanguageSelectionEventViewModel;
import com.rektapps.greendictionary.viewmodel.shared.eventviewmodel.MultipleSelectionEventViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    ViewModelProvider.Factory factory;
    private ActionMode.Callback actionModeCallback;
    private ActionMode actionMode;
    private MainViewViewModel mainViewModel;
    private MainViewBinding mainViewBinding;
    private DictionaryListFragmentPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new DictionaryListFragmentPagerAdapter(getSupportFragmentManager());
        mainViewBinding = DataBindingUtil.setContentView(this, R.layout.main_view);
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewViewModel.class);
        mainViewModel.setMultipleSelectionEventModel(ViewModelProviders.of(this).get(MultipleSelectionEventViewModel.class));

        mainViewBinding.setLifecycleOwner(this);
        mainViewBinding.setViewmodel(mainViewModel);
        getLifecycle().addObserver(mainViewModel);

        initMultipleSelectionActionMode();
        initViewPager();

        setSupportActionBar(mainViewBinding.mainToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String LANGUAGE_DIALOG_TAG = "lng";

        mainViewBinding.languageFromTv.setOnClickListener(view -> LanguageSelectDialog.getInstance(LanguageType.FROM).show(getSupportFragmentManager(), LANGUAGE_DIALOG_TAG));

        mainViewBinding.languageDestTv.setOnClickListener(view -> LanguageSelectDialog.getInstance(LanguageType.DEST).show(getSupportFragmentManager(), LANGUAGE_DIALOG_TAG));

        mainViewModel.getApiErrorMessage().observe(this, errorMessage -> Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show());

        ListCountViewModel countViewModel = ViewModelProviders.of(this).get(ListCountViewModel.class);

        for (ListType listType : ListType.values()) {
            countViewModel.getCount(listType).observe(this, count -> {
                mainViewModel.onListCountChanged(listType, count);
                setCountToTab(listType, count);
                //prevent a situation when the search panel is hidden and list items count changed to zero so the list cannot be scrolled and the panel's translation can't be restored.
                if (count == 0 && mainViewBinding.searchPanel.getTranslationY() != 0)
                    mainViewBinding.searchPanel.setTranslationY(0);

            });
        }


        LanguageSelectionEventViewModel languageSelectionEventViewModel = ViewModelProviders.of(this).get(LanguageSelectionEventViewModel.class);

        languageSelectionEventViewModel.getLanguageSelectionEvent().observe(this, languageSelectionEvent -> mainViewModel.onLanguageSelected(languageSelectionEvent));


        mainViewModel.isMultipleSelectionActive().observe(this, isMultipleSelectionActive -> {
            if (isMultipleSelectionActive) {
                showSelectionActionBar();
                setPageChangingEnabled(false);
            } else {
                if (actionMode != null)
                    actionMode.finish();
                setPageChangingEnabled(true);
            }
        });


        SelectedListItemsCountViewModel selectedCountViewModel = ViewModelProviders.of(this).get(SelectedListItemsCountViewModel.class);

        selectedCountViewModel.getSelectedCount().observe(this, selectedCount -> {
            if (actionMode != null) {
                actionMode.setTitle(String.valueOf(selectedCount));
                setMenuButtonEnabled(actionMode.getMenu().findItem(R.id.delete_selected), selectedCount != 0);
            }
        });


        mainViewModel.getEntryScreenItemLiveData().observe(this, entryWithData -> {
            String fragmentTag = "entry_dialog";
            if (getSupportFragmentManager().findFragmentByTag(fragmentTag) == null)
                EntryScreen.getInstance(entryWithData).show(getSupportFragmentManager(), fragmentTag);
        });

        mainViewBinding.swapLanguagesButton.setOnClickListener(view -> {
            Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.swap_button_rotate);
            mainViewBinding.swapLanguagesButton.startAnimation(rotateAnimation);
            mainViewModel.swapFromDestLanguages();
        });


        mainViewBinding.phraseInput.setOnEditorActionListener((textView, keyCode, keyEvent) -> {
            boolean isHardwareEnterPressed = keyCode == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN;
            boolean isSoftwareSearchPressed = keyCode == EditorInfo.IME_ACTION_SEARCH;
            boolean isTextNotEmpty = !textView.getText().toString().trim().isEmpty();

            if ((isSoftwareSearchPressed || isHardwareEnterPressed) && isTextNotEmpty) {
                mainViewModel.search();
                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);//close keyboard

                textView.clearFocus();
                return false;
            }
            return true;

        });

    }


    private void setCountToTab(ListType listType, int count){
        int tabPosition = pagerAdapter.getListPositionForType(listType);
        if(count > 0)
            mainViewBinding.tabs.setBadgeText(tabPosition, Integer.toString(count));
        else
            mainViewBinding.tabs.setBadgeText(tabPosition, null);

    }

    private void initViewPager() {
        mainViewBinding.viewpager.setAdapter(pagerAdapter);
        initTabs();
    }

    private void initTabs() {
        mainViewBinding.tabs.setupWithViewPager(mainViewBinding.viewpager);
        for (int pagePosition = 0; pagePosition < pagerAdapter.getCount(); pagePosition++) {
            int iconId = pagerAdapter.getListType(pagePosition) == ListType.HISTORY ? R.drawable.ic_history_black_24dp : R.drawable.ic_favorite_black_24dp;
            mainViewBinding.tabs.setIcon(pagePosition, iconId);
        }
    }

    @Override
    protected void onResume() {
        ViewPager.OnPageChangeListener listener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ListType activeListType = pagerAdapter.getListType(position);
                mainViewModel.onActiveListChanged(activeListType);
            }
        };
        mainViewBinding.viewpager.addOnPageChangeListener(listener);
        listener.onPageSelected(mainViewBinding.viewpager.getCurrentItem());
        super.onResume();
    }


    private void initMultipleSelectionActionMode() {
        actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.selection_menu, menu);
                setMenuButtonEnabled(menu.getItem(0), false);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                mainViewModel.onMenuItemClicked(item.getItemId());
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mainViewModel.onMultipleSelectionCanceled();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem multipleSelectionMenuItem = menu.findItem(R.id.multi_select);
        mainViewModel.getIsMultipleSelectionEnabledLiveData().observe(this, isSelectionEnabled -> setMenuButtonEnabled(multipleSelectionMenuItem, isSelectionEnabled));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mainViewModel.onMenuItemClicked(item.getItemId());
        return true;
    }


    private void setMenuButtonEnabled(MenuItem item, boolean isEnabled) {
        item.setEnabled(isEnabled);
        int alpha = isEnabled ? 255 : 110;
        item.getIcon().setAlpha(alpha);
    }

    private void showSelectionActionBar() {
        actionMode = startSupportActionMode(actionModeCallback);
    }

    private void setPageChangingEnabled(boolean isPageChangingEnabled) {
        mainViewBinding.viewpager.setPageChangingEnabled(isPageChangingEnabled);
        LinearLayout tabStrip = ((LinearLayout) mainViewBinding.tabs.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> !isPageChangingEnabled || v.onTouchEvent(event));
        }
    }


}
