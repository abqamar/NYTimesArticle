package com.articles.nytimes.adapters;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.articles.nytimes.databinding.ItemArticleBinding;
import com.articles.nytimes.interfaces.OnArticleClickListener;
import com.articles.nytimes.model.Result;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private ArrayList<Result> articleArr;
    private ItemArticleBinding mBinding;
    private OnArticleClickListener listener;

    public ArticleAdapter(ArrayList<Result> arr, OnArticleClickListener l){
        articleArr = arr;
        listener = l;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = ItemArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(articleArr.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return articleArr.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder{

        ItemArticleBinding itemArticleBinding;

        public ArticleViewHolder(@NonNull ItemArticleBinding itemView) {
            super(itemView.getRoot());
            itemArticleBinding = itemView;
        }

        public void bind(final Result item, OnArticleClickListener listener) {
            itemArticleBinding.tvTitle.setText(item.getTitle());
            itemArticleBinding.tvByLine.setText(item.getByline());
            itemArticleBinding.tvPublishDate.setText(item.getPublishedDate());
            itemArticleBinding.imgArticle.setTransitionName(String.valueOf(item.getId()));
            if(item.getMedia() != null && !item.getMedia().isEmpty()){
                if(item.getMedia().get(0).getMediaMetadata() != null && !item.getMedia().get(0).getMediaMetadata().isEmpty() && item.getMedia().get(0).getMediaMetadata().get(1) != null){
                    String url = item.getMedia().get(0).getMediaMetadata().get(1).getUrl();
                    Glide.with(itemArticleBinding.getRoot()).load(Uri.parse(url)).into(itemArticleBinding.imgArticle);
                }
            }

            itemArticleBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onArticleClick(itemArticleBinding.imgArticle, item);
                }
            });
        }
    }

}
