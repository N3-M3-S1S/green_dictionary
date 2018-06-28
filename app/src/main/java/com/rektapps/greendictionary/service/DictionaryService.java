package com.rektapps.greendictionary.service;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.rektapps.greendictionary.model.ApiResponse;
import com.rektapps.greendictionary.view.ListType;
import com.rektapps.greendictionary.view.displayitem.DisplayEntryItem;
import com.rektapps.greendictionary.view.displayitem.EntryScreenItem;
import com.rektapps.greendictionary.view.displayitem.ListItem;

import java.util.Collection;

public interface DictionaryService {
    void saveToHistory(ApiResponse apiResponse);

    void loadEntryScreenItem(DisplayEntryItem displayEntryItem);

    void addToFavorites(DisplayEntryItem displayEntryItem);

    void removeItemFromList(ListType type, DisplayEntryItem displayEntryItem);

    void removeItemsFromList(ListType type, Collection<? extends DisplayEntryItem> displayEntryItems);

    LiveData<PagedList<ListItem>> getListItems(ListType listType);

    LiveData<EntryScreenItem> getEntryScreenItemLiveData();

}
