package com.rektapps.greendictionary.view.displayitem;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.databinding.ObservableField;

import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.model.entity.Translation;

public class ListItem extends DisplayEntryItem {

    @Embedded
    private Translation translation;

    @Ignore
    private ObservableField<Boolean> isSelected = new ObservableField<>();

    public ListItem(DictionaryEntry entry, Translation translation) {
        super(entry);
        this.translation = translation;
        isSelected.set(false);
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListItem listItem = (ListItem) o;
        return (getEntry() != null ? getEntry().equals(listItem.getEntry()) : listItem.getEntry() == null) && (translation != null ? translation.equals(listItem.translation) : listItem.translation == null);
    }

    @Override
    public int hashCode() {
        int result = getEntry() != null ? getEntry().hashCode() : 0;
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        return result;
    }

    public ObservableField<Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }

    public boolean isSelected() {
        return isSelected.get();
    }
}
