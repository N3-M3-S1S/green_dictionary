package com.rektapps.greendictionary.viewmodel.shared.eventviewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.rektapps.greendictionary.event.MultipleSelectionEvent;
import com.rektapps.greendictionary.view.ListType;
import com.rektapps.greendictionary.viewmodel.util.SingleLiveEvent;

import java.util.EnumMap;
import java.util.Map;

public class MultipleSelectionEventViewModel extends ViewModel {
    private Map<ListType, MutableLiveData<MultipleSelectionEvent>> listTypeMutableLiveDataMap = new EnumMap<>(ListType.class);

    public MultipleSelectionEventViewModel(){
        for(ListType listType : ListType.values())
            listTypeMutableLiveDataMap.put(listType, new SingleLiveEvent<>());
    }

    public void postEvent(ListType postTo, MultipleSelectionEvent event){
        listTypeMutableLiveDataMap.get(postTo).setValue(event);
    }

    public LiveData<MultipleSelectionEvent> getMultipleSelectionEventLiveData(ListType listType){
        return listTypeMutableLiveDataMap.get(listType);
    }

}
