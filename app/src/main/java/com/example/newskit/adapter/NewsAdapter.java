package com.example.newskit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newskit.NewsSiteActivity;
import com.example.newskit.R;
import com.example.newskit.pojo.TopNews;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<TopNews> list;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        private LinearLayout wrapper;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            wrapper = itemView.findViewById(R.id.wrapper);
        }
    }

    public NewsAdapter(List<TopNews> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        context = parent.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        TopNews topNews = list.get(position);
        holder.title.setText(topNews.getTitle());
        if(topNews.getThumbnail() != null && !topNews.getThumbnail().equals("")) {
            Glide.with(context).load(topNews.getThumbnail())
                    .transform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL))
                    .into(holder.image);
        }
        else {
            holder.image.setVisibility(View.GONE);
        }

        holder.wrapper.setOnClickListener(v -> {
            String url = topNews.getUrl();
            FinestWebView webView = new FinestWebView();
            if(url.contains("zhihu") || url.contains("bilibili")) {
               // url = url.replaceFirst("zhihu://", "https://www.zhihu.com/");
                new FinestWebView.Builder(context).webViewSupportZoom(true)
                        .statusBarColorRes(R.color.red)
                        .toolbarColorRes(R.color.red)
                        .titleColorRes(R.color.white)
                        // .webViewJavaScriptEnabled(false)
                        .webViewUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0").show(url);
            }
            else {
                new FinestWebView.Builder(context)
                        .statusBarColorRes(R.color.red)
                        .toolbarColorRes(R.color.red)
                        .titleColorRes(R.color.white)
                        .show(url);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
