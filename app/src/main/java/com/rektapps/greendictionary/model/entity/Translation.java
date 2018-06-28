package com.rektapps.greendictionary.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity(foreignKeys = @ForeignKey(entity = DictionaryEntry.class, parentColumns = "id", childColumns = "entryId", onDelete = ForeignKey.CASCADE), indices = @Index("entryId"))
public class Translation implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "t_id")
    private long id;

    private long entryId;

    private String text;

    private List<String> meanings;

    @Ignore
    public Translation(String text, List<String> meanings) {
        this.text = text;
        this.meanings = meanings;
    }

    @Ignore
    public Translation(String text) {
        this.text = text;
    }

    public Translation() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEntryId() {
        return entryId;
    }

    public void setEntryId(long entryId) {
        this.entryId = entryId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getMeanings() {
        if (meanings == null)
            return Collections.emptyList();
        else
            return meanings;
    }

    public void setMeanings(List<String> meanings) {
        this.meanings = meanings;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", entryId=" + entryId +
                ", text='" + text + '\'' +
                ", meanings=" + meanings +
                '}';
    }
}
