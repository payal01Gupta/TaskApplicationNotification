package com.example.taskapplication.ui.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.adapter.CategoriesAdapter;
import com.example.taskapplication.ui.model.CategoriesModel;
import com.example.taskapplication.ui.viewModel.LiveDataViewModel;
import com.example.taskapplication.ui.viewModel.ServerViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LiveDataActivity extends AppCompatActivity {

    LiveDataViewModel liveDataViewModel;
    RecyclerView categoriesRecyclerView;
    CategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoriesAdapter = new CategoriesAdapter(new ArrayList<>(), this);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        liveDataViewModel = new ViewModelProvider(LiveDataActivity.this).get(LiveDataViewModel.class);
        initViewModel();
    }

    private void initViewModel(){
        liveDataViewModel.getCategoriesMapList().observe(this,stringListMap ->{
                    Set<String> keyList = stringListMap.keySet();
                    List<String> list = new ArrayList<>(keyList);

                    Collection<List<CategoriesModel>> valueList = stringListMap.values();
                    ArrayList<List<CategoriesModel>> list3 = new ArrayList<>(valueList);

                    categoriesAdapter.updateList(list,list3);
                }
           );
    }
}