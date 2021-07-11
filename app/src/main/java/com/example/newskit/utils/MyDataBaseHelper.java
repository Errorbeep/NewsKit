package com.example.newskit.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newskit.R;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private String categoryCreate = "create table category (" +
            "id integer primary key autoincrement," +
            "title text default 'Title' ," +
            "local_update timestamp default current_timestamp ," +
            "remote_update timestamp default current_timestamp," +
            "image text" +
            ")";
    private String newsItemCreate = "create table news_item (" +
            "id integer primary key autoincrement," +
            "cid integer," +
            "title text, " +
            "description text, " +
            "thumbnail text, " +
            "url text, " +
            "domain text, " +
            "site_name text, " +
            "logo text, " +
            "views text" +
            ")";


    public MyDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(categoryCreate);
        db.execSQL(newsItemCreate);
        db.execSQL("insert into category(title, image) values('知乎热榜'," + R.drawable.zhihu + ")");
        db.execSQL("insert into category(title, image) values('微博热搜'," + R.drawable.weibo + ")");
        db.execSQL("insert into category(title, image) values('微信 ‧ 24h热文榜'," + R.drawable.weixin + ")");
        db.execSQL("insert into category(title, image) values('百度 ‧ 实时热点'," + R.drawable.baidu + ")");
        db.execSQL("insert into category(title, image) values('今日头条'," + R.drawable.toutiao + ")");
        db.execSQL("insert into category(title, image) values('网易新闻'," + R.drawable.netease2 + ")");
        db.execSQL("insert into category(title, image) values('新浪网 ‧ 热词排行榜'," + R.drawable.weibo + ")");   ///
        db.execSQL("insert into category(title, image) values('36氪 ‧ 24小时热榜'," + R.drawable.ke36 + ")");
        db.execSQL("insert into category(title, image) values('历史上的今天'," + R.drawable.history + ")");
        db.execSQL("insert into category(title, image) values('少数派'," + R.drawable.sspai + ")");    //
        db.execSQL("insert into category(title, image) values('CSDN今日推荐'," + R.drawable.csdn + ")");
        db.execSQL("insert into category(title, image) values('掘金热榜'," + R.drawable.juejin + ")");   //
        db.execSQL("insert into category(title, image) values('哔哩哔哩热榜'," + R.drawable.bilibili + ")");
        db.execSQL("insert into category(title, image) values('抖音视频榜'," + R.drawable.tiktok + ")");
        db.execSQL("insert into category(title, image) values('吾爱破解热榜'," + R.drawable.pojie + ")");
        db.execSQL("insert into category(title, image) values('V2e热帖'," + R.drawable.pic + ")");       //
        db.execSQL("insert into category(title, image) values('全球主机论坛热帖'," + R.drawable.pic + ")"); //

        db.execSQL("insert into category(title, image) values('头条'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('趣闻'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('财经'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('体育'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('娱乐'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('军事'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('教育'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('科技'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('NBA'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('股票'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('星座'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('女性'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('健康'," + R.drawable.pic + ")");
        db.execSQL("insert into category(title, image) values('育儿'," + R.drawable.pic + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
