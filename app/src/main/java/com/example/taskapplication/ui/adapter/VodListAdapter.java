package com.example.taskapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.webServices.VodStreamsCallback;

import java.util.List;

public class VodListAdapter extends RecyclerView.Adapter<VodListAdapter.VodViewHolder>{
    List<VodStreamsCallback> list;
    public VodListAdapter(List<VodStreamsCallback> list) {
        this.list = list;
    }
    public void updateList(List<VodStreamsCallback> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public VodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vod, parent, false);
        return new VodViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VodViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VodViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public VodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.vodName);
        }
    }

}
