package com.articles.nytimes.repository;

import com.articles.nytimes.model.Article;
import com.articles.nytimes.retrofit.ApiClient;
import com.articles.nytimes.retrofit.Service;
import com.articles.nytimes.utility.AppUtils;

import io.reactivex.Single;

public class ArticleRepository {

    Service mService;

    public ArticleRepository(){
        this.mService = ApiClient.getClient();
    }

    public Single<Article> getArticles(String days){
        return mService.getArticlesByDays(days, AppUtils.APIKEY);
    }
}
