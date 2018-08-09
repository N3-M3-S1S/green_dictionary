package com.rektapps.greendictionary.view.adapter;

import android.arch.lifecycle.ViewModel;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;

import java.util.List;

public class BaseViewModelRecyclerAdapter<T, VM extends ViewModel> extends RecyclerView.Adapter<BaseViewModelViewHolder<VM, ? extends ViewDataBinding, T>> {
    protected VM viewModel;
    protected List<T> items;
    private ViewModelViewHolderFactory<VM, T> factory;


    protected BaseViewModelRecyclerAdapter(ViewModelViewHolderFactory<VM, T> factory) {
        this.factory = factory;
    }

    protected BaseViewModelRecyclerAdapter(){

    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public BaseViewModelViewHolder<VM, ? extends ViewDataBinding, T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return factory.build(viewModel, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewModelViewHolder<VM, ? extends ViewDataBinding, T> holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
