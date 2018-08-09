package com.rektapps.greendictionary.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.service.ClipboardService;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.view.ListType;
import com.rektapps.greendictionary.view.displayitem.EntryScreenItem;
import com.rektapps.greendictionary.viewmodel.util.SingleLiveEvent;

import javax.inject.Inject;

public class EntryScreenViewModel extends ViewModel implements LifecycleObserver {
    private DictionaryService service;
    private MutableLiveData<Boolean> isEntryFavoriteLiveData = new MutableLiveData<>();
    private MutableLiveData<EntryScreenItem> entryScreenItemLiveData = new MutableLiveData<>();
    private SingleLiveEvent copiedToClipboardEvent = new SingleLiveEvent();
    private ClipboardService clipboardService;
    private boolean initialFavorite;


    @Inject
    EntryScreenViewModel(DictionaryService service, ClipboardService clipboardService) {
        this.service = service;
        this.clipboardService = clipboardService;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        boolean isFavorite = isEntryFavoriteLiveData.getValue();
        //do something with entry only if its favorite status changed
        if (isFavorite != initialFavorite) {
            EntryScreenItem entryScreenItem = entryScreenItemLiveData.getValue();
            if (isFavorite)
                service.addToFavorites(entryScreenItem);
            else
                service.removeItemFromList(ListType.FAVORITES, entryScreenItem);
        }
    }

    public void setEntryScreenItem(EntryScreenItem entryScreenItem) {
        this.entryScreenItemLiveData.setValue(entryScreenItem);
        initialFavorite = entryScreenItem.getEntry().isFavorite();
        isEntryFavoriteLiveData.setValue(initialFavorite);
    }


    public void onMenuItemClicked(int itemId) {
        if (itemId == R.id.entry_favorite)
            toggleEntryFavorite();

    }

    private void toggleEntryFavorite() {
        boolean isFavorite = !isEntryFavoriteLiveData.getValue();
        isEntryFavoriteLiveData.setValue(isFavorite);
    }


    public boolean copyTextToClipboardOnLongClick(String text) {
        clipboardService.copyToClipboard(text);
        copiedToClipboardEvent.call();
        return true;
    }


    public LiveData<Boolean> getIsEntryFavoriteLiveData() {
        return isEntryFavoriteLiveData;
    }

    public LiveData getCopiedToClipboardEvent() {
        return copiedToClipboardEvent;
    }
}
