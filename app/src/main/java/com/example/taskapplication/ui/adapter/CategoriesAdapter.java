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
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{
    List<CategoriesModel> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();

   ArrayList<List<CategoriesModel>> valueList = new ArrayList<>();
    int count;
    Context context;
    public CategoriesAdapter(List<CategoriesModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void updateList(List<String> newList, ArrayList<List<CategoriesModel>> valueList) {
        this.list1 = newList;
        this.valueList= valueList;
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
        int count = 0;
        String title = list1.get(position);
        holder.tvCategoryName.setText(title);

        if(list1.get(position).equalsIgnoreCase("Sports")){
            count = valueList.size();
        }
        if(list1.get(position).equalsIgnoreCase("Movies")){
            count = valueList.size();
        }
        if(list1.get(position).equalsIgnoreCase("News")){
            count = valueList.size();
        }
        holder.tvCategoryCount.setText(String.valueOf(count));

    }

    @Override
    public int getItemCount() {
        return list1.size();
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
