package com.rektapps.greendictionary.view.adapter.viewholder.factory;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface ViewHolderFactory<VH extends RecyclerView.ViewHolder> {
    VH createViewHolder(ViewGroup parent);
}
