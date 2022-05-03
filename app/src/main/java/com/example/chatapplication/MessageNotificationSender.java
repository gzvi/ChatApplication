package com.example.chatapplication;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MessageNotificationSender {
    ChatMessage cm;
    Context mContext;
    Activity mActivity;

    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String key = "AAAAo2LUkg4:APA91bEGdy_1SYhMyj_GZj3FdVDnZPI8TEY-7g6ZwuhMR2mbhJNnUp8VUr8i8PdqYPzlcjAZc4xpm7FidNYyVdFbINxcilWUB-7fyrCawQs9gzFf5Y_4zOlxVVnsz0U90g9Xo58QhJ9q";
    public MessageNotificationSender(ChatMessage cm, Context mContext, Activity mActivity) {
        this.cm = cm;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void sendNotification() {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", "/topics/" + "all");
            JSONObject notiObject = new JSONObject();
            notiObject.put("title", cm.getSenderName());
            notiObject.put("body", cm.getMessage());
            mainObj.put("notification", notiObject);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, response -> {
            }, (Response.ErrorListener) error -> {
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + key);
                    return header;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}