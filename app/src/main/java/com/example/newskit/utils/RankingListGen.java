package com.example.newskit.utils;

import com.example.newskit.pojo.TopNews;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingListGen {
    static public List<TopNews> getList(String type) throws IOException, JSONException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //mytoken1 ---> vbe3PPQr5GYl2KZi
        // token2 ----> y2IaFJXakiYuQmiC
        Method method = RequestData.class.getMethod("getData1", String.class);

        List<TopNews> list = NewsGenerator.getNewsList("https://v2.alapi.cn/api/tophub/get?type=" + type + "&token=vbe3PPQr5GYl2KZi",
                method);

        System.out.print("list-------->" + list);
        return list;

    }
}
