package com.rektapps.greendictionary.view.displayitem;

import android.arch.persistence.room.Embedded;

import com.rektapps.greendictionary.model.entity.DictionaryEntry;

import java.io.Serializable;

public abstract class DisplayEntryItem implements Serializable {

    @Embedded
    private DictionaryEntry entry;

    protected DisplayEntryItem(DictionaryEntry entry) {
        this.entry = entry;
    }

    public DictionaryEntry getEntry() {
        return entry;
    }


}
