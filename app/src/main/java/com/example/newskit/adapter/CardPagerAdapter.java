package com.example.newskit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.newskit.NewsDetailActivity;
import com.example.newskit.R;
import com.example.newskit.pojo.CardItem;
import com.thefinestartist.finestwebview.FinestWebView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }
    public CardPagerAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.top_news_layout, container, false);

        container.addView(view);
        bind(mData.get(position), view, position);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view, int position) {
        ImageView head = view.findViewById(R.id.head);
        Glide.with(context).load(item.getResourceId()).into(head);

        if(item.getNews().get(0).getTitle() == null ) {
            ViewGroup.LayoutParams layoutParams = head.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            head.setLayoutParams(layoutParams);
            LinearLayout linearLayout = view.findViewById(R.id.linearLayout);
            linearLayout.setVisibility(View.GONE);

            return;
        }

        Button detail = view.findViewById(R.id.detail_btn);
        detail.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("cardItem", (Serializable)mData.get(position));
            context.startActivity(intent);
        });


        TextView firstTitle = view.findViewById(R.id.first_title);
        TextView secondTitle = view.findViewById(R.id.second_title);
        TextView thirdTitle = view.findViewById(R.id.third_title);

        ImageView firstImage = view.findViewById(R.id.first_image);
        ImageView secondImage = view.findViewById(R.id.second_image);
        ImageView thirdImage = view.findViewById(R.id.third_image);





        firstTitle.setText(item.getNews().get(0).getTitle());
        secondTitle.setText(item.getNews().get(1).getTitle());
        thirdTitle.setText(item.getNews().get(2).getTitle());

        List<TextView> titles = Arrays.asList(firstTitle, secondTitle, thirdTitle);
        for(int i = 0; i < 3; i++) {
            int finalI = i;
            String url = item.getNews().get(finalI).getUrl();

            titles.get(i).setOnClickListener(v -> {
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


        List<ImageView> images = Arrays.asList(firstImage, secondImage, thirdImage);

        for(int i = 0; i < 3; i++) {
            if(item.getNews().get(0).getThumbnail() != null && !item.getNews().get(0).getThumbnail().equals("")) {
                Glide.with(context).load(item.getNews().get(i).getThumbnail())
                        .transform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL))
                        .into(images.get(i));
            }
            else {
                images.get(i).setVisibility(View.GONE);
                /*
                    for(int j = 0; j < 3; j++) {
                        // titles.get(j).setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                        titles.get(j).setWidth(dip2px(context, 315));
                    }
                 */
            }
        }

    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
