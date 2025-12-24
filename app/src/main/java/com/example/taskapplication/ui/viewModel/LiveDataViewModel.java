package com.example.taskapplication.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskapplication.ui.model.CategoriesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LiveDataViewModel extends ViewModel {
    String response ="{\n" +
            "  \"categories\": {\n" +
            "    \"Sports\": [\n" +
            "      {\n" +
            "        \"channel_id\": 101,\n" +
            "        \"channel_name\": \"Star Sports\",\n" +
            "        \"is_hd\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"channel_id\": 102,\n" +
            "        \"channel_name\": \"Sony Sports\",\n" +
            "        \"is_hd\": false\n" +
            "      }\n" +
            "    ],\n" +
            "    \"Movies\": [\n" +
            "      {\n" +
            "        \"channel_id\": 201,\n" +
            "        \"channel_name\": \"HBO\",\n" +
            "        \"is_hd\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"channel_id\": 202,\n" +
            "        \"channel_name\": \"Zee Cinema\",\n" +
            "        \"is_hd\": false\n" +
            "      }\n" +
            "    ],\n" +
            "    \"News\": [\n" +
            "      {\n" +
            "        \"channel_id\": 301,\n" +
            "        \"channel_name\": \"BBC News\",\n" +
            "        \"is_hd\": true\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    List<CategoriesModel> listSports = new ArrayList<>();
    List<CategoriesModel> listMovies = new ArrayList<>();
    List<CategoriesModel> listNews = new ArrayList<>();

    List<String> keyList = new ArrayList<>();
    Map<String, List<CategoriesModel>> map = new HashMap<>();

    private MutableLiveData<Map<String,List<CategoriesModel>>> _categoriesMapList = new MutableLiveData<>();

    public LiveData<Map<String, List<CategoriesModel>>> getCategoriesMapList(){
        return _categoriesMapList;
    }

    JSONObject jsonObject;
    {
        try {
               jsonObject = new JSONObject(response);
               JSONObject jsonObject1 = jsonObject.getJSONObject("categories");
               JSONArray sportsJsonArray = jsonObject1.getJSONArray("Sports");
               JSONArray moviesJsonArray = jsonObject1.getJSONArray("Movies");
               JSONArray newsJsonArray = jsonObject1.getJSONArray("News");

            Iterator<String> keys = jsonObject1.keys();
            while (keys.hasNext()){
                String categoryName = keys.next();
                keyList.add(categoryName);
            }

               for(int i=0 ; i<sportsJsonArray.length(); i++){
                JSONObject arrayJSONObject = sportsJsonArray.getJSONObject(i);

                int channelId = arrayJSONObject.getInt("channel_id");
                String channelName = arrayJSONObject.getString("channel_name");
                Boolean isHD = arrayJSONObject.getBoolean("is_hd");

                 listSports.add(new CategoriesModel(channelId,channelName,isHD));
               }

            map.put(keyList.get(0), listSports);

            for(int i=0; i<moviesJsonArray.length(); i++){
              JSONObject moviesJsonObject = moviesJsonArray.getJSONObject(i);

              int channelId = moviesJsonObject.getInt("channel_id");
              String channelName = moviesJsonObject.getString("channel_name");
              Boolean isHD = moviesJsonObject.getBoolean("is_hd");

              listMovies.add(new CategoriesModel(channelId,channelName,isHD));
            }
            map.put(keyList.get(1),listMovies);

            for(int i=0; i<newsJsonArray.length(); i++){
                JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);

                int channelId = newsJsonObject.getInt("channel_id");
                String channelName = newsJsonObject.getString("channel_name");
                Boolean isHD = newsJsonObject.getBoolean("is_hd");

                listNews.add(new CategoriesModel(channelId,channelName,isHD));
            }
                    map.put(keyList.get(2),listNews);

            _categoriesMapList.postValue(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
