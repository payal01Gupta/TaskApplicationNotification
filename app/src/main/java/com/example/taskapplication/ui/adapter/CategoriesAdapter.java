package com.example.taskapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.model.CategoriesModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{
    List<CategoriesModel> list = new ArrayList<>();
    List<String> keyList = new ArrayList<>();
    Map<String, List<CategoriesModel>> mapList= new HashMap<>();

    int count;
    Context context;
    public CategoriesAdapter(List<CategoriesModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void updateList(List<String> keyList, Map<String, List<CategoriesModel>> mapList) {
        this.keyList = keyList;
        this.mapList= mapList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categories, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String title = keyList.get(position);
        holder.tvCategoryName.setText(title);

        List<CategoriesModel> valueList = mapList.get(title);
        holder.tvCategoryCount.setText(valueList.size()+"");

    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName, tvCategoryCount;
        ViewHolder(View v) {
            super(v);
            tvCategoryName = v.findViewById(R.id.tv_category_name);
            tvCategoryCount = v.findViewById(R.id.tv_category_count);
        }
    }
}
