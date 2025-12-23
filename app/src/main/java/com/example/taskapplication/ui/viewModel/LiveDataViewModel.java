package com.example.taskapplication.ui.viewModel;

import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    JSONObject jsonObject;
    {
        try {
                jsonObject = new JSONObject(response);
               JSONObject jsonObject1= jsonObject.getJSONObject("categories");
               for(int i=0;i<jsonObject1.length();i++){

               }
            //   JSONArray jsonArray=jsonObject1.getJSONArray("Sports");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
