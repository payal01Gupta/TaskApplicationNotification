package com.example.taskapplication.ui.activity;

import android.os.Bundle;
import android.util.Log;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LiveDataActivity extends AppCompatActivity {

    LiveDataViewModel liveDataViewModel;
    RecyclerView categoriesRecyclerView;
    CategoriesAdapter categoriesAdapter;

    String response3 = "{\n" +
            "\"addresses\": [\n" +
            "{ \"city\": \"New York\", \"zip\": \"10001\" },\n" +
            "{ \"city\": \"Los Angeles\", \"zip\": \"90001\" }\n" +
            "],\n" +
            "\"contacts\": [\n" +
            "{ \"type\": \"email\", \"value\": \"a@test.com\" },\n" +
            "{ \"type\": \"phone\", \"value\": \"1234567890\" }\n" +
            "],\n" +
            "\"active\": true\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

  //      parsingJson2();

        parsingJson3();

//        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
//        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        categoriesAdapter = new CategoriesAdapter(new ArrayList<>(), this);
//        categoriesRecyclerView.setAdapter(categoriesAdapter);
//
//        liveDataViewModel = new ViewModelProvider(LiveDataActivity.this).get(LiveDataViewModel.class);
//        liveDataViewModel.parseJSON();
//        initViewModel();

    }

    public void parsingJson3(){
        try {
            JSONObject jsonObject = new JSONObject(response3);
            Iterator<String> parentKey =jsonObject.keys();
            while(parentKey.hasNext()){
              String key = parentKey.next();
              if(jsonObject.get(key) instanceof JSONObject){

              }else if(jsonObject.get(key) instanceof JSONArray){
                  JSONArray jsonArray = jsonObject.getJSONArray(key);
                  for(int i=0; i<jsonArray.length();i++){
                      JSONObject addressObject = (JSONObject) jsonArray.get(i);
                      Iterator<String> addressIterate = addressObject.keys();
                      while (addressIterate.hasNext()){
                          String addressKey =   addressIterate.next();
                          String addressValue = addressObject.get(addressKey).toString();
                          Log.e("addressKey ",addressKey);
                          Log.e("addressValue ",addressValue);
                      }

                  }
              }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parsingJson2() {

        //////  Static work  //////////////
        try {
            JSONObject jsonObject = new JSONObject(response3);
            JSONArray addressesJsonArray = (JSONArray) jsonObject.get("addresses");
            for (int i = 0; i < addressesJsonArray.length(); i++) {
                JSONObject jsonObject1 = addressesJsonArray.getJSONObject(i);
                String city = jsonObject1.get("city").toString();
                String zip = jsonObject1.get("zip").toString();
                Log.e("Parsing array: addresses", "city: " + city + " \n" + "zip: " + zip);
            }

            JSONArray contactsJsonArray = jsonObject.getJSONArray("contacts");
            for (int i = 0; i < contactsJsonArray.length(); i++) {
                JSONObject jsonObject1 = contactsJsonArray.getJSONObject(i);
                String type = jsonObject1.get("type").toString();
                String value = jsonObject1.get("value").toString();
                Log.e("Parsing array: contacts", "type: " + type + "\n " +"value: "+ value);
            }

            Boolean isActive = (Boolean) jsonObject.get("active");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewModel(){
        liveDataViewModel.getCategoriesMapList().observe(this,stringListMap ->{
                    Set<String> keySet = stringListMap.keySet();
                    List<String> keyList = new ArrayList<>(keySet);

                    categoriesAdapter.updateList(keyList,stringListMap);
                }
           );
    }
}