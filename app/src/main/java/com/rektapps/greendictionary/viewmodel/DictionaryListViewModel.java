package com.rektapps.greendictionary.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.rektapps.greendictionary.event.MultipleSelectionEvent;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.view.ListType;
import com.rektapps.greendictionary.view.displayitem.ListItem;
import com.rektapps.greendictionary.viewmodel.util.SingleLiveEvent;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;


public class DictionaryListViewModel extends ViewModel {
    private DictionaryService service;
    private ListType listType;

    private MutableLiveData<Boolean> isMultipleSelectionActiveLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedCountChangedEventLiveData = new SingleLiveEvent<>();

    private Set<ListItem> selectedListItems = new HashSet<>();


    @Inject
    DictionaryListViewModel(DictionaryService service) {
        this.service = service;
    }

    public LiveData<PagedList<ListItem>> getList(ListType listType) {
        this.listType = listType;
        return service.getListItems(listType);
    }

    public MutableLiveData<Boolean> getIsMultipleSelectionActiveLiveData() {
        return isMultipleSelectionActiveLiveData;
    }


    public void onMultipleSelectionEvent(MultipleSelectionEvent event){
        switch (event) {
            case SELECTION_ACTIVE:
                isMultipleSelectionActiveLiveData.setValue(true);
                selectedCountChangedEventLiveData.setValue(0);
                break;
            case SELECTION_CANCELED:
                if (selectedListItems.size() > 0) {
                    for (ListItem selectedListItem : selectedListItems)
                        selectedListItem.setIsSelected(false);

                    selectedListItems.clear();
                }
                isMultipleSelectionActiveLiveData.setValue(false);
                break;
            case DELETE_SELECTED:
                service.removeItemsFromList(listType, selectedListItems);
                selectedCountChangedEventLiveData.setValue(0);
        }
    }

    public void listItemClicked(ListItem listItem) {
        boolean isMultipleSelectionActive = isMultipleSelectionActiveLiveData.getValue()
                != null && isMultipleSelectionActiveLiveData.getValue();
        if (isMultipleSelectionActive) {
            if (listItem.isSelected())
                selectedListItems.remove(listItem);
            else
                selectedListItems.add(listItem);
            listItem.setIsSelected(!listItem.isSelected());

            selectedCountChangedEventLiveData.setValue(selectedListItems.size());
        } else {
            service.loadEntryScreenItem(listItem);
        }
    }

    public LiveData<Integer> getSelectedCountChangedEventLiveData() {
        return selectedCountChangedEventLiveData;
    }


    public void onDeleteItemClicked(ListItem entry) {
        service.removeItemFromList(listType, entry);
    }


}



