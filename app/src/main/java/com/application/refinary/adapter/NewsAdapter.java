package com.application.refinary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.refinary.R;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.news.Article;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {
    private List<Article> articles;
    private Context mContext;
    private GlobalClass.AdapterClickListner onclicklistner;


  /*  public NewsAdapter(List<Article> articles, Context mContext) {
        this.articles = articles;
        this.mContext = mContext;
    }*/

    public NewsAdapter(List<Article> articles, Context mContext, GlobalClass.AdapterClickListner onclicklistner) {
        this.articles = articles;
        this.mContext = mContext;
        this.onclicklistner = onclicklistner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_recycler_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_header.setText(articles.get(position).getArticleTitle());
        holder.news_descr.setText(articles.get(position).getArticleDescription());
        Glide.with(mContext).load(articles.get(position).getArticleCoverImage()).into(holder.news_img);
        try {
            holder.tv_date.setText(GlobalClass.outputdateformat.format(GlobalClass.inputdateformat.parse(articles.get(position).getArticlePublishedAt())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.container.setOnClickListener(v->{
            onclicklistner.onItemClickListener(position);
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, tv_header,news_descr;
        private ImageView news_img;
        private RelativeLayout container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.tv_date);
            tv_header = itemView.findViewById(R.id.tv_header);
            news_img= itemView.findViewById(R.id.news_img);
            news_descr= itemView.findViewById(R.id.news_descr);
            container= itemView.findViewById(R.id.container);
        }

    }
}
