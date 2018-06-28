package com.rektapps.greendictionary.storage.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<String> stringToList(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        if (list.size() > 0)
            return new Gson().toJson(list);
        else
            return null;
    }


}
