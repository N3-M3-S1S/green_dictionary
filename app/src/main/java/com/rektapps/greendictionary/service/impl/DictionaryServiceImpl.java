package com.rektapps.greendictionary.service.impl;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.rektapps.greendictionary.model.ApiResponse;
import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.storage.db.DictionaryEntryDao;
import com.rektapps.greendictionary.storage.db.ExampleDao;
import com.rektapps.greendictionary.storage.db.TranslationDao;
import com.rektapps.greendictionary.view.ListType;
import com.rektapps.greendictionary.view.displayitem.DisplayEntryItem;
import com.rektapps.greendictionary.view.displayitem.EntryScreenItem;
import com.rektapps.greendictionary.view.displayitem.ListItem;
import com.rektapps.greendictionary.viewmodel.util.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class DictionaryServiceImpl implements DictionaryService {
    private DictionaryEntryDao entryDao;
    private ExampleDao exampleDao;
    private TranslationDao translationDao;
    private SingleLiveEvent<EntryScreenItem> screenItemMutableLiveData = new SingleLiveEvent<>();
    private Map<ListType, LiveData<PagedList<ListItem>>> listItemsMap = new EnumMap<>(ListType.class);
    private ExecutorService executorService;

    @Inject
    DictionaryServiceImpl(DictionaryEntryDao entryDao, ExampleDao examplesDao, TranslationDao translationDao, ExecutorService executorService) {
        this.entryDao = entryDao;
        this.exampleDao = examplesDao;
        this.translationDao = translationDao;
        this.executorService = executorService;
    }

    @Override
    public void saveToHistory(ApiResponse apiResponse) {
        long entryId = entryDao.insert(apiResponse.getEntry());
        apiResponse.getEntry().setId(entryId);

        for (Translation translation : apiResponse.getTranslations())
            translation.setEntryId(entryId);

        for (Example example : apiResponse.getExamples())
            example.setEntryId(entryId);

        translationDao.insert(apiResponse.getTranslations());
        exampleDao.insert(apiResponse.getExamples());
        screenItemMutableLiveData.postValue(new EntryScreenItem(apiResponse.getEntry(), apiResponse.getTranslations(), apiResponse.getExamples()));
    }

    @Override
    public void loadEntryScreenItem(DisplayEntryItem displayEntryItem) {
        executorService.submit(() -> {
            DictionaryEntry entry = displayEntryItem.getEntry();
            List<Translation> translations = translationDao.getTranslationsByEntryId(entry.getId());
            List<Example> examples = exampleDao.getExamplesByEntryId(entry.getId());
            screenItemMutableLiveData.postValue(new EntryScreenItem(entry, translations, examples));
        });
    }

    @Override
    public void addToFavorites(DisplayEntryItem listItem) {
        executorService.submit(() -> {
            DictionaryEntry entry = listItem.getEntry();
            entry.setFavorite(true);
            entry.setAddedToFavoriteDate(System.currentTimeMillis());
            entryDao.update(entry);
        });
    }


    @Override
    public void removeItemFromList(ListType type, DisplayEntryItem displayEntryItem) {
        executorService.submit(() -> {
            DictionaryEntry entry = displayEntryItem.getEntry();
            switch (type) {
                case HISTORY:
                    if (entry.isFavorite()) {
                        entry.setHistory(false);
                        entryDao.update(entry);
                    } else
                        entryDao.delete(entry);
                    break;
                case FAVORITES:
                    if (entry.isHistory()) {
                        entry.setFavorite(false);
                        entryDao.update(entry);
                    } else
                        entryDao.delete(entry);
            }
        });
    }

    @Override
    public void removeItemsFromList(ListType type, Collection<? extends DisplayEntryItem> itemsToRemove) {
        executorService.submit(() -> {
            List<DictionaryEntry> toUpdate = new ArrayList<>();
            List<DictionaryEntry> toDelete = new ArrayList<>();

            for (DisplayEntryItem itemToRemove : itemsToRemove) {
                DictionaryEntry entry = itemToRemove.getEntry();
                //delete item if it's not in other list
                if (type == ListType.HISTORY && !entry.isFavorite() || type == ListType.FAVORITES && !entry.isHistory())
                    toDelete.add(entry);
                else {
                    switch (type) {
                        case HISTORY:
                            entry.setHistory(false);
                            break;
                        case FAVORITES:
                            entry.setFavorite(false);
                    }
                    toUpdate.add(entry);
                }
            }

            entryDao.update(toUpdate);
            entryDao.delete(toDelete);

            itemsToRemove.clear();

        });
    }


    @Override
    public LiveData<PagedList<ListItem>> getListItems(ListType listType) {
        LiveData<PagedList<ListItem>> listLiveData;
        if (!listItemsMap.containsKey(listType)) {
            DataSource.Factory<Integer, ListItem> factory = null;
            switch (listType) {
                case HISTORY:
                    factory = entryDao.getHistory();
                    break;
                case FAVORITES:
                    factory = entryDao.getFavorite();
            }
            listLiveData = new LivePagedListBuilder<>(factory, 10).build();
            listItemsMap.put(listType, listLiveData);
        } else {
            listLiveData = listItemsMap.get(listType);
        }
        return listLiveData;
    }

    @Override
    public LiveData<EntryScreenItem> getEntryScreenItemLiveData() {
        return screenItemMutableLiveData;
    }


}
