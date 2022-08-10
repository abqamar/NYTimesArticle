package com.articles.nytimes.interfaces;

import android.view.View;

import com.articles.nytimes.model.Result;

public interface OnArticleClickListener {
    public void onArticleClick(View view, Result result);
}
