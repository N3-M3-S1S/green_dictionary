package com.rektapps.greendictionary.viewmodel.shared;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.rektapps.greendictionary.view.ListType;

import java.util.EnumMap;
import java.util.Map;

public class ListCountViewModel extends ViewModel {
    private Map<ListType, MutableLiveData<Integer>> listTypeMutableLiveDataMap = new EnumMap<>(ListType.class);

    public ListCountViewModel(){
        for(ListType listType : ListType.values())
            listTypeMutableLiveDataMap.put(listType, new MutableLiveData<>());
    }

    public void setCount(ListType listType, int count) {
       listTypeMutableLiveDataMap.get(listType).setValue(count);
    }

    public LiveData<Integer> getCount(ListType listType) {
        return listTypeMutableLiveDataMap.get(listType);
    }

}
