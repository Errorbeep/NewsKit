package com.example.newskit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.newskit.adapter.NewsAdapter;
import com.example.newskit.pojo.CardItem;
import com.example.newskit.pojo.TopNews;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ImageView head = findViewById(R.id.head);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // FloatingActionButton floatingActionButton = findViewById(R.id.floating_button);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        List<TopNews> list;

        CardItem cardItem = (CardItem)getIntent().getSerializableExtra("cardItem");
        collapsingToolbarLayout.setTitle(cardItem.getHeader());
        list = cardItem.getNews();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        NewsAdapter newsAdapter = new NewsAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        Glide.with(this).load(cardItem.getResourceId())
                .transform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(head);

        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}