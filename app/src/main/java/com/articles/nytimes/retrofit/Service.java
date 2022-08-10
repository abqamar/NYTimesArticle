package com.articles.nytimes.retrofit;

import com.articles.nytimes.model.Article;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("mostviewed/all-sections/{days}.json")
    Single<Article> getArticlesByDays(@Path("days") String days, @Query("api-key") String apiKey);

    @GET("mostviewed/all-sections/{days}.json")
    Call<Article> getArticlesByDaysTest(@Path("days") String days, @Query("api-key") String apiKey);

}