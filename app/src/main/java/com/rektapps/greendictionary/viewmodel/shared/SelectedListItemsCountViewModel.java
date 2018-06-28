package com.rektapps.greendictionary.viewmodel.shared;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


public class SelectedListItemsCountViewModel extends ViewModel {
    private MutableLiveData<Integer> selectedListItemsCountLiveData = new MutableLiveData<>();

    public LiveData<Integer> getSelectedCount() {
        return selectedListItemsCountLiveData;
    }

    public void setSelectedCount(int count) {
        selectedListItemsCountLiveData.setValue(count);
    }
}
