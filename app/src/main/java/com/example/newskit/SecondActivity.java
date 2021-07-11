package com.example.newskit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.newskit.adapter.CardPagerAdapter;
import com.example.newskit.pojo.CardItem;
import com.example.newskit.pojo.TopNews;
import com.example.newskit.utils.CateNewsGen;
import com.example.newskit.utils.MyDataBaseHelper;
import com.example.newskit.utils.NetworkUtil;
import com.example.newskit.utils.RankingListGen;
import com.example.newskit.utils.ShadowTransformer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class SecondActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    private MyDataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private int[] pics = {R.drawable.toutiao2, R.drawable.quewen, R.drawable.caijing, R.drawable.tiyu, R.drawable.yule,
            R.drawable.junshi, R.drawable.jiaoyu, R.drawable.keji2, R.drawable.nba, R.drawable.gupiao, R.drawable.xingzuo,
            R.drawable.nvxing, R.drawable.jiankang, R.drawable.yuer};
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        FloatingActionButton btnNext = findViewById(R.id.btn_next);

        if(!NetworkUtil.isNetworkConnected(this)) {
            Toast.makeText(this, "网络未连接，正在使用本地缓存！", Toast.LENGTH_LONG).show();
        }
        Log.e("network status", "------->" + NetworkUtil.isNetworkConnected(this));

        btnNext.setOnClickListener(v -> {
            mViewPager.arrowScroll(View.FOCUS_RIGHT);
        });

        SmoothBottomBar smtBar = findViewById(R.id.btm_bar);
        // smtBar.setOnItemSelectedListener()
        smtBar.setItemActiveIndex(1);

        smtBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                // Toast.makeText(SecondActivity.this, "You clicked " + i + "!", Toast.LENGTH_LONG).show();
                switch (i) {
                    case 0:
                        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                        startActivity(intent);
                        smtBar.setItemActiveIndex(1);
                        break;
                    case 1:
                        break;
                }
                return false;
            }
        });




        mCardAdapter = new CardPagerAdapter(SecondActivity.this);

        dataBaseHelper = new MyDataBaseHelper(this, "news.db", null, 3);
        db = dataBaseHelper.getWritableDatabase();

        loadData(new String[] {"头条", "新闻", "财经", "体育", "娱乐", "军事", "教育", "科技", "NBA", "股票", "星座", "女性", "健康", "育儿"});

        mCardAdapter.addCardItem(
                new CardItem(R.drawable.catenews, null,
                        Arrays.asList(new TopNews(null, null, null, null, null, null, null, null))));



        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }
    public void loadData(String[] type) {

        for(int i = 18; i <= 17 + type.length; i++) {
            int finalI = i;
            new Thread(() -> {
                int count = 0;
                String lastUpdate = "1970-01-01 01:01:01";
                Cursor cursor2 = db.rawQuery("select local_update from category where id = " + finalI, null);
                if(cursor2.moveToFirst()) {
                    lastUpdate = cursor2.getString(0);
                }
                Timestamp now = new Timestamp(System.currentTimeMillis());


                long hours = (now.getTime() - Timestamp.valueOf(lastUpdate).getTime()) / 1000 / 60 / 60;
                cursor2.close();

                Log.e("Time =========== ", hours + " page--> " + finalI + " <--");

                Cursor cursor = db.rawQuery("select * from news_item where cid = " + finalI, null);
                count = cursor.getCount();
                cursor.close();

// && (new Timestamp(System.currentTimeMillis()).getTime() - Timestamp.valueOf(lastUpdate).getTime() / 1000 * 60 * 60 < 4)
                if(count != 0 && hours < 3) {
                    loadLocalData(finalI, type[finalI - 18]);
                }
                else {
                    loadInternetData(finalI, type[finalI - 18]);
                }
            }).start();
        }

    }

    public void loadLocalData(int cid, String header) {
        Cursor cursor = db.rawQuery("select * from news_item where cid = " + cid, null);
        List<TopNews> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            list.add(new TopNews(
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
            ));
        }
        cursor.close();
        runOnUiThread(() -> {
            if(list.size() > 0) {
                Cursor cs =  db.rawQuery("select title from category where id = " +  cid, null);
                String title = "详情";
                if(cs.moveToFirst()) {
                    title = cs.getString(0);
                }
                mCardAdapter.addCardItem(new CardItem(pics[cid - 18], title, list));
                mCardAdapter.notifyDataSetChanged();
            }
        });

    }

    public void loadInternetData(int cid, String type) {
        try {
            List<TopNews> list = CateNewsGen.getList(type);
            if(list != null) {
                runOnUiThread(() -> {
                    Cursor cs =  db.rawQuery("select title from category where id = " +  cid, null);
                    String title = "详情";
                    if(cs.moveToFirst()) {
                        title = cs.getString(0);
                    }
                    mCardAdapter.addCardItem(new CardItem(pics[cid - 18], title, list));
                    mCardAdapter.notifyDataSetChanged();
                });

                db.execSQL("delete from news_item where cid = " + cid);
                Log.e("delete done", "page --> " + cid);

                for (TopNews topNews:list) {
                    db.execSQL("insert into news_item(cid, title, description, thumbnail, url, domain, site_name, logo, views) " +
                                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                            new Object[] {cid, topNews.getTitle(), topNews.getDescription(),
                                    topNews.getThumbnail(), topNews.getUrl(), topNews.getDomain(),
                                    topNews.getSiteName(), topNews.getLogo(), topNews.getViews()
                            });
                }

                db.execSQL("update category set local_update = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        + "' where id = " + cid);
            }
            else {
                loadLocalData(cid, type);
            }
        } catch (IOException | JSONException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}