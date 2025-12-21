package com.example.taskapplication.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskapplication.ui.model.ServersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerViewModel extends ViewModel {

    String group;

    private String response =
            "{\n" +
            "  \"servers\": [\n" +
            "    {\n" +
            "      \"serverName\": \"India Server 1\",\n" +
            "      \"onlineUser\": 1200,\n" +
            "      \"server_connection\": \"active\",\n" +
            "      \"server_port\": 8080,\n" +
            "      \"server_group\": \"India\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"India Server 2\",\n" +
            "      \"onlineUser\": 980,\n" +
            "      \"server_connection\": \"active\",\n" +
            "      \"server_port\": 8081,\n" +
            "      \"server_group\": \"India\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"India Server 3\",\n" +
            "      \"onlineUser\": 650,\n" +
            "      \"server_connection\": \"inactive\",\n" +
            "      \"server_port\": 8082,\n" +
            "      \"server_group\": \"India\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"Global Server 2\",\n" +
            "      \"onlineUser\": 210,\n" +
            "      \"server_connection\": \"inactive\",\n" +
            "      \"server_port\": 7071,\n" +
            "      \"server_group\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"Global Server 3\",\n" +
            "      \"onlineUser\": 95,\n" +
            "      \"server_connection\": \"active\",\n" +
            "      \"server_port\": 7072,\n" +
            "      \"server_group\": null\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"England Server 1\",\n" +
            "      \"onlineUser\": 1100,\n" +
            "      \"server_connection\": \"active\",\n" +
            "      \"server_port\": 9090,\n" +
            "      \"server_group\": \"England\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"England Server 2\",\n" +
            "      \"onlineUser\": 870,\n" +
            "      \"server_connection\": \"active\",\n" +
            "      \"server_port\": 9091,\n" +
            "      \"server_group\": \"England\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"England Server 3\",\n" +
            "      \"onlineUser\": 540,\n" +
            "      \"server_connection\": \"inactive\",\n" +
            "      \"server_port\": 9092,\n" +
            "      \"server_group\": \"England\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"England Server 4\",\n" +
            "      \"onlineUser\": 320,\n" +
            "      \"server_connection\": \"active\",\n" +
            "      \"server_port\": 9093,\n" +
            "      \"server_group\": \"England\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"serverName\": \"Global Server 1\",\n" +
            "      \"onlineUser\": 400,\n" +
            "      \"server_connection\": \"active\",\n" +
            "      \"server_port\": 7070,\n" +
            "      \"server_group\": null\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    private MutableLiveData<List<ServersModel>> _serverList = new MutableLiveData<>();
    public LiveData<List<ServersModel>> getServerList(){
        return _serverList;
    }

    private MutableLiveData<Map<String, List<ServersModel>>> _groupServerList = new MutableLiveData<>();

    public  LiveData<Map<String, List<ServersModel>>> getGroupServerList(){
        return _groupServerList;
    }

    public void loadServers()  {

        List<ServersModel> list = new ArrayList<>();
        Map<String,List<ServersModel>> map = new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("servers");

            for (int i=0; i<jsonArray.length(); i++){
               JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                if(!jsonObject1.has("server_group") || jsonObject1.isNull("server_group")){
                    group = "Individual";
                } else{
                    group = jsonObject1.getString("server_group");
                }

                String serverName = jsonObject1.getString("serverName");
                int onlineUsers = jsonObject1.getInt("onlineUser");
                String serverConnection = jsonObject1.getString("server_connection");
                int serverPort = jsonObject1.getInt("server_port");

                list.add(new ServersModel(serverName,onlineUsers,serverConnection,serverPort,group));

                if(!map.containsKey(group)){
                    map.put(group,new ArrayList<>());
                }
                map.get(group).add(new ServersModel(serverName,onlineUsers,serverConnection,serverPort,group));
            }

            _serverList.postValue(list);
            _groupServerList.postValue(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
