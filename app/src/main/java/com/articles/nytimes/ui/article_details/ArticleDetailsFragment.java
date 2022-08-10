package com.articles.nytimes.ui.article_details;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import com.articles.nytimes.R;
import com.articles.nytimes.databinding.FragmentArticleDetailsBinding;
import com.articles.nytimes.model.Result;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

public class ArticleDetailsFragment extends Fragment {

    private FragmentArticleDetailsBinding mBinding;
    private Context mContext;
    Transition sharedElementEnterTransition;
    private Result itemData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedElementEnterTransition = TransitionInflater.from(mContext).inflateTransition(R.transition.move_local);
        setSharedElementEnterTransition(sharedElementEnterTransition);

        if(getArguments() != null){
            itemData = new Gson().fromJson(getArguments().getString("data"), Result.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        postponeEnterTransition();

        mBinding = FragmentArticleDetailsBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        mBinding.imgArticle.setTransitionName(itemData.getId()+"");

        mBinding.tvTitle.setText(itemData.getTitle());
        mBinding.tvByLine.setText(itemData.getByline());
        mBinding.tvPublishDate.setText(itemData.getPublishedDate());

        if(itemData.getMedia() != null && !itemData.getMedia().isEmpty()){
            if(itemData.getMedia().get(0).getMediaMetadata() != null && !itemData.getMedia().get(0).getMediaMetadata().isEmpty() && itemData.getMedia().get(0).getMediaMetadata().get(2) != null){
                String url = itemData.getMedia().get(0).getMediaMetadata().get(1).getUrl();
                Glide.with(mContext)
                        .load(url)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                startPostponedEnterTransition();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                startPostponedEnterTransition();
                                mBinding.imgArticle.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                return false;
                            }
                        })
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mBinding.imgArticle);
            }
        }

        return view;
    }
}
