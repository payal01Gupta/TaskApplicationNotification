package com.example.taskapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.activity.ServerListActivity;
import com.example.taskapplication.ui.model.GroupModel;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{

    List<GroupModel> list;
    Context context;

    public GroupAdapter(List<GroupModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void updateList(List<GroupModel> newList) {
        list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {

        GroupModel item = list.get(pos);
        h.tvName.setText(item.getGroupname());

        // arrow show only if servers exist
        h.arrow.setVisibility(item.isGroup() ? View.VISIBLE : View.GONE);

        h.itemView.setOnClickListener(v -> {
            if (item.isGroup()) {
                Intent intent = new Intent(context, ServerListActivity.class);
                intent.putExtra("group", item.getGroupname());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView arrow;

        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvGroup);
            arrow = v.findViewById(R.id.ivArrow);
        }
    }
}
