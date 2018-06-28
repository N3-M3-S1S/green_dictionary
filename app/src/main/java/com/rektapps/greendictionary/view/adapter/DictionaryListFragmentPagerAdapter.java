package com.rektapps.greendictionary.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rektapps.greendictionary.view.DictionaryListFragment;
import com.rektapps.greendictionary.view.ListType;

public class DictionaryListFragmentPagerAdapter extends FragmentPagerAdapter {

    public DictionaryListFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DictionaryListFragment.getInstance(getListType(position));
    }

    @Override
    public int getCount() {
        return ListType.values().length;
    }

    public ListType getListType(int position) {
        return ListType.values()[position];
    }

    public int getListPositionForType(ListType listType){
        ListType[] listTypes = ListType.values();
        for(int position = 0; position < listTypes.length; position++)
            if (listTypes[position] == listType)
                return position;

        return -1;
    }


}
