package com.example.taskapplication.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskapplication.ui.model.CategoriesModel;
import com.google.gson.JsonArray;

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

    String response1 = "{\n" +
            "  \"status\": true,\n" +
            "  \"currency\": \"INR\",\n" +
            "  \"products\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"Laptop\",\n" +
            "      \"price\": 75000,\n" +
            "      \"discount\": 10,\n" +
            "      \"isAvailable\": true,\n" +
            "      \"specs\": {\n" +
            "        \"brand\": \"Dell\",\n" +
            "        \"ram\": \"16GB\",\n" +
            "        \"storage\": \"512GB SSD\",\n" +
            "        \"features\": [\"Backlit Keyboard\", \"Fingerprint Sensor\"]\n" +
            "      },\n" +
            "      \"sellers\": [\n" +
            "        {\n" +
            "          \"sellerId\": \"S101\",\n" +
            "          \"name\": \"TechStore\",\n" +
            "          \"rating\": 4.5\n" +
            "        },\n" +
            "        {\n" +
            "          \"sellerId\": \"S102\",\n" +
            "          \"name\": \"ElectroMart\",\n" +
            "          \"rating\": 4.2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"name\": \"Laptop\",\n" +
            "      \"price\": 68000,\n" +
            "      \"discount\": null,\n" +
            "      \"isAvailable\": false,\n" +
            "      \"specs\": {\n" +
            "        \"brand\": \"HP\",\n" +
            "        \"ram\": \"8GB\",\n" +
            "        \"storage\": \"512GB SSD\",\n" +
            "        \"features\": []\n" +
            "      },\n" +
            "      \"sellers\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"name\": \"Laptop\",\n" +
            "      \"price\": 82000,\n" +
            "      \"discount\": 5,\n" +
            "      \"isAvailable\": true,\n" +
            "      \"specs\": {\n" +
            "        \"brand\": \"Lenovo\",\n" +
            "        \"ram\": \"16GB\",\n" +
            "        \"storage\": \"1TB SSD\",\n" +
            "        \"features\": [\"Touchscreen\"]\n" +
            "      },\n" +
            "      \"warrantyYears\": 2\n" +
            "    }\n" +
            "  ]\n" +
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

    public void parseJSON(){
        JSONObject jsonObject;
        {
            try {
                Map<String,List<CategoriesModel>> mapList = new HashMap<>();
                jsonObject = new JSONObject(response);
                JSONObject categoriesObject = jsonObject.getJSONObject("categories");
                Iterator<String> keys = categoriesObject.keys();
                while(keys.hasNext()){
                    String key =  keys.next();
                    JSONArray jsonArray = (JSONArray) categoriesObject.get(key);
                    List<CategoriesModel> valueList = new ArrayList<>();
                    for(int i=0;i< jsonArray.length(); i++){
                       JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                       int channelID = (int) jsonObject1.get("channel_id");
                       String channelName = jsonObject1.get("channel_name").toString();
                       Boolean isHd = (Boolean) jsonObject1.get("is_hd");
                       CategoriesModel categoriesModel =new CategoriesModel(channelID,channelName,isHd);
                       valueList.add(categoriesModel);
                    }
                    mapList.put(key,valueList);
                }
                _categoriesMapList.postValue(mapList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void parsingJson(){
        try {
            JSONObject jsonObject = new JSONObject(response1);
            JSONArray jsonArray = jsonObject.getJSONArray("products");

            for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
            int pId = (int) jsonObject1.get("id");
            Boolean pIsAvailable = (Boolean) jsonObject1.get("isAvailable");
            String pName = (String) jsonObject1.get("name");
            int pPrice = (int) jsonObject1.get("price");

            JSONArray jsonArray1 = jsonObject1.getJSONArray("sellers");


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void parseJSON(){
//        JSONObject jsonObject;
//        {
//            try {
//                jsonObject = new JSONObject(response);
//                JSONObject jsonObject1 = jsonObject.getJSONObject("categories");
//                JSONArray sportsJsonArray = jsonObject1.getJSONArray("Sports");
//                JSONArray moviesJsonArray = jsonObject1.getJSONArray("Movies");
//                JSONArray newsJsonArray = jsonObject1.getJSONArray("News");
//
//                Iterator<String> keys = jsonObject1.keys();
//                while (keys.hasNext()){
//                    String categoryName = keys.next();
//                    keyList.add(categoryName);
//                }
//
//                for(int i=0 ; i<sportsJsonArray.length(); i++){
//                    JSONObject arrayJSONObject = sportsJsonArray.getJSONObject(i);
//
//                    int channelId = arrayJSONObject.getInt("channel_id");
//                    String channelName = arrayJSONObject.getString("channel_name");
//                    Boolean isHD = arrayJSONObject.getBoolean("is_hd");
//
//                    listSports.add(new CategoriesModel(channelId,channelName,isHD));
//                }
//
//                map.put(keyList.get(0), listSports);
//
//                for(int i=0; i<moviesJsonArray.length(); i++){
//                    JSONObject moviesJsonObject = moviesJsonArray.getJSONObject(i);
//
//                    int channelId = moviesJsonObject.getInt("channel_id");
//                    String channelName = moviesJsonObject.getString("channel_name");
//                    Boolean isHD = moviesJsonObject.getBoolean("is_hd");
//
//                    listMovies.add(new CategoriesModel(channelId,channelName,isHD));
//                }
//                map.put(keyList.get(1),listMovies);
//
//                for(int i=0; i<newsJsonArray.length(); i++){
//                    JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);
//
//                    int channelId = newsJsonObject.getInt("channel_id");
//                    String channelName = newsJsonObject.getString("channel_name");
//                    Boolean isHD = newsJsonObject.getBoolean("is_hd");
//
//                    listNews.add(new CategoriesModel(channelId,channelName,isHD));
//                }
//                map.put(keyList.get(2),listNews);
//
//                _categoriesMapList.postValue(map);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
