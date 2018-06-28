package com.rektapps.greendictionary.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = @ForeignKey(entity = DictionaryEntry.class, parentColumns = "id", childColumns = "entryId", onDelete = ForeignKey.CASCADE), indices = @Index("entryId"))

public class Example implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "e_id")
    private long id;

    private long entryId;

    private String firstExample;

    private String secondExample;

    @Ignore
    public Example(String firstExample, String secondExample) {
        this.firstExample = firstExample;
        this.secondExample = secondExample;
    }

    public Example() {
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

    public String getFirstExample() {
        return firstExample;
    }

    public void setFirstExample(String firstExample) {
        this.firstExample = firstExample;
    }

    public String getSecondExample() {
        return secondExample;
    }

    public void setSecondExample(String secondExample) {
        this.secondExample = secondExample;
    }

    @Override
    public String toString() {
        return "Example{" +
                "id=" + id +
                ", entryId=" + entryId +
                ", firstExample='" + firstExample + '\'' +
                ", secondExample='" + secondExample + '\'' +
                '}';
    }
}
