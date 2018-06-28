package com.rektapps.greendictionary.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.view.adapter.viewholder.ExampleViewHolder;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import java.util.List;

public class ExamplesAdapter extends RecyclerView.Adapter<ExampleViewHolder> {
    private List<Example> examples;
    private EntryScreenViewModel viewModel;

    public ExamplesAdapter(List<Example> examples, EntryScreenViewModel viewModel) {
        this.examples = examples;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExampleViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.example, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        if (position % 2 != 0)
            holder.setBackgroundColorGray();
        holder.bind(examples.get(position), viewModel);
    }

    @Override
    public int getItemCount() {
        return examples.size();
    }


}
