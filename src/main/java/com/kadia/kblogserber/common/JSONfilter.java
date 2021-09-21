package com.kadia.kblogserber.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kadia.kblogserber.entity.Blog;

import java.util.List;

public class JSONfilter {
    public static<T> JSONArray ListObjectToJSONArrayFilter(List<T> list, String[] useless){
        JSONArray data = new JSONArray();
        for (T object : list) {
            JSONObject tmp = (JSONObject) JSON.toJSON(object);
            for (String uselessItem : useless){
                tmp.remove(uselessItem);
                tmp.remove(uselessItem);
            }
            data.add(tmp);
        }
        return data;
    }

    public static<T> JSONObject ObjectToJSONObjectFilter(T object, String[] useless){
        if(object == null)
            return null;
        JSONObject data = (JSONObject) JSON.toJSON(object);
        for (String uselessItem : useless){
            data.remove(uselessItem);
            data.remove(uselessItem);
        }
        return data;
    }
}
