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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestData {
    public static List<TopNews> getData1(String url) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request;
        request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        String jsonString = readStream(Objects.requireNonNull(response.body()).byteStream());

        // String jsonString = readStream(new URL(url).openStream());
        JSONObject jsonObject = null;
        TopNews news = null;
        String code = new JSONObject(jsonString).optString("code");
        if (!code.equals("200")) {
            return null;
        }

        JSONObject object = new JSONObject(jsonString).getJSONObject("data"); // "data"
        JSONArray jsonArray = object.getJSONArray("list");    // "items" or "list"

        return getList(jsonArray, new String[]{"title", null, null, "link", null, null, null, "other"});
        // title;  description;thumbnail; url,   domain; siteName; logo;  views;


    }

    public static List<TopNews> getData2(String url) throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();
        Request request;
        request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        String jsonString = readStream(Objects.requireNonNull(response.body()).byteStream());

        JSONObject jsonObject = null;
        TopNews news = null;
        String code = new JSONObject(jsonString).optString("code");
        if (!code.equals("10000")) {
            return null;
        }

        JSONObject object = new JSONObject(jsonString).getJSONObject("result").getJSONObject("result"); // "data"
        JSONArray jsonArray = object.getJSONArray("list");    // "items" or "list"

        return getList(jsonArray, new String[]{"title", "content", "pic", "url", null, "src", null, null});
        // title;  description;thumbnail; url, domain; siteName; logo; views;


    }

    private static String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8"); //字节流转化为字符流
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<TopNews> getList(JSONArray jsonArray, String[] property) throws JSONException {
        List<TopNews> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            TopNews news = new TopNews(
                    jsonObject.optString(property[0]),
                    jsonObject.optString(property[1]),
                    jsonObject.optString(property[2]),
                    jsonObject.optString(property[3]),
                    jsonObject.optString(property[4]),
                    jsonObject.optString(property[5]),
                    jsonObject.optString(property[6]),
                    jsonObject.optString(property[7])
            );
            list.add(news);
        }
        return list;
    }
}
