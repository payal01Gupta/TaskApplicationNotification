package com.example.taskapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.model.ServersModel;

import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {

    private List<ServersModel> list;

    public ServerAdapter(List<ServersModel> list) {
        this.list = list;
    }

    public void updateList(List<ServersModel> newList) {
        list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_server, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServersModel server = list.get(position);

        holder.tvName.setText(server.getServerName());
        holder.tvUsers.setText("Users : " + server.getOnlineUser());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvUsers;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvServerName);
            tvUsers = itemView.findViewById(R.id.tvOnlineUsers);
        }
    }
}
