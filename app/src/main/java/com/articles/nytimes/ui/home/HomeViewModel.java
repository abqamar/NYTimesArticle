package com.articles.nytimes.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.articles.nytimes.model.Article;
import com.articles.nytimes.repository.ArticleRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<Article> articlesByDays = new MutableLiveData<>();
    private MutableLiveData<Integer> mDays = new MutableLiveData<>();

    private ArticleRepository articleRepository = new ArticleRepository();

    public LiveData<Article> getArticlesLiveData(String days) {
        disposables.clear();
        setDays(Integer.parseInt(days));
        disposables.add(articleRepository.getArticles(days)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> articlesByDays.setValue(result),
                        throwable -> articlesByDays.setValue(null)));
        return articlesByDays;
    }

    public void setDays(int days){
        mDays.setValue(days);
    }

    public Integer getDays(){
        return mDays.getValue();
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}