package com.example.newskit.utils;

import com.example.newskit.pojo.TopNews;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CateNewsGen {
    static public List<TopNews> getList(String channel) throws IOException, JSONException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //mytoken1 ---> vbe3PPQr5GYl2KZi
        // token2 ----> y2IaFJXakiYuQmiC
        Method method = RequestData.class.getMethod("getData2", String.class);


        List<TopNews> list = NewsGenerator.getNewsList("https://way.jd.com/jisuapi/get?channel=" + channel
                        + "&num=40&start=0&appkey=e95db1a8ebab09572c05007272400720",
                method);
                            /*
                               data;   items;  title;  description;       thumbnail; url, domain; siteName; logo; views;
                             */
        //System.out.print("list-------->" + list);
        return list;

    }
}
