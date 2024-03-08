package com.gns.androidcourse.recyclerviewcommands;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gns.androidcourse.databinding.ActivityRecyclerViewCommandsBinding;
import com.gns.androidcourse.databinding.RecyclerviewRowBinding;

import java.util.Collections;
import java.util.List;

public class ListRecycler extends RecyclerView.Adapter<ListRecycler.ListHolder> {
    private static final String TAG = "ListRecycler";
    private final List<ListModal> list;
    private final ActivityRecyclerViewCommandsBinding activityMainBinding;

    public ListRecycler(List<ListModal> list, ActivityRecyclerViewCommandsBinding activityMainBinding) {
        this.list = list;
        this.activityMainBinding = activityMainBinding;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: viewtype:"+viewType);
        return new ListHolder(RecyclerviewRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+list.get(position).getName()+" : "+position);
        holder.binding.textView.setText(list.get(position).getName());
        holder.binding.imageView2.setImageResource(android.R.drawable.ic_delete);
        holder.binding.getRoot().setOnClickListener(view -> {
            String text = activityMainBinding.editTextTextPersonName.getText().toString();
            if (text.isEmpty() && position != 0){
                Collections.swap(list, position, 0);
                notifyItemMoved(position, 0);
                this.notifyItemRangeChanged(0,getItemCount());
                activityMainBinding.recyclerView.scrollToPosition(0);
            }else {
                list.get(position).setName(text);
                this.notifyItemChanged(position);
                this.notifyItemRangeChanged(position,getItemCount());
            }
        });
        holder.binding.getRoot().setOnLongClickListener(view -> {
            list.remove(position);
            this.notifyItemRemoved(position);
            this.notifyItemRangeChanged(position,getItemCount());
            return true;
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+list.size());
        return list.size();
    }


    public static class ListHolder extends RecyclerView.ViewHolder{
        RecyclerviewRowBinding binding;
        public ListHolder(@NonNull RecyclerviewRowBinding itemView) {
            super(itemView.getRoot());
            Log.d(TAG, "ListHolder: "+itemView.textView.getText().toString());
            this.binding = itemView;
        }
    }


}
