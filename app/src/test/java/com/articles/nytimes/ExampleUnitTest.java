package com.articles.nytimes;

import org.junit.Test;

import static org.junit.Assert.*;

import com.articles.nytimes.model.Article;
import com.articles.nytimes.retrofit.ApiClient;
import com.articles.nytimes.retrofit.Service;
import com.articles.nytimes.utility.AppUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void fetchArticle() {

        Service apiEndpoints = ApiClient.getClient();

        Call<Article> call = apiEndpoints.getArticlesByDaysTest("7", AppUtils.APIKEY);

        try {
            Response<Article> response = call.execute();
            Article articleResponse = response.body();

            assertTrue(response.isSuccessful() && !articleResponse.getResults().isEmpty());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}