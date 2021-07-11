package com.example.newskit.utils;

import android.util.Log;

import com.example.newskit.pojo.TopNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsGenerator {

    public static List<TopNews> getNewsList(String url, Method method) throws IOException, JSONException, InvocationTargetException, IllegalAccessException {
        return (List<TopNews>) method.invoke(null, url);
    }
}
