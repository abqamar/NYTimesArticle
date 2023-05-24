package com.articles.nytimes.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.transition.TransitionInflater;

import com.articles.nytimes.R;
import com.articles.nytimes.adapters.ArticleAdapter;
import com.articles.nytimes.databinding.FragmentHomeBinding;
import com.articles.nytimes.interfaces.OnArticleClickListener;
import com.articles.nytimes.model.Result;
import com.articles.nytimes.utility.AppUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnArticleClickListener {

    private FragmentHomeBinding mBinding;
    private HomeViewModel homeViewModel;
    private View homeView;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        if(homeView == null){
            homeView = mBinding.getRoot();
            homeViewModel.setDays(1);
            wait(true);
            getArticles("1");
        }
        return homeView;
    }

    private void wait(boolean isLoading) {
        if (isLoading) {
            mBinding.loader.setVisibility(View.VISIBLE);
            mBinding.rvArticles.setVisibility(View.GONE);
        } else {
            mBinding.loader.setVisibility(View.GONE);
            mBinding.rvArticles.setVisibility(View.VISIBLE);
        }
    }

    private void getArticles(String days) {
        if(!AppUtils.hasConnection(getContext())){
            AppUtils.showSnackBar(mBinding.getRoot(), mContext, getResources().getString(R.string.no_internet));
            wait(false);
            return;
        }
        homeViewModel.getArticlesLiveData(days).observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                wait(false);
                setDataIntoAdapter(articles.getResults());
            } else {
                wait(false);
            }
        });
    }

    private void setDataIntoAdapter(ArrayList<Result> list) {
        mBinding.rvArticles.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvArticles.setAdapter(new ArticleAdapter(list, this));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

        if(homeViewModel.getDays() == 1){
            menu.findItem(R.id.action_one_day).setChecked(true);
        }else if(homeViewModel.getDays() == 7){
            menu.findItem(R.id.action_seven_days).setChecked(true);
        }else{
            menu.findItem(R.id.action_thirty_days).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_one_day && !item.isChecked()){
            item.setChecked(true);
            wait(true);
            getArticles("1");
        }else if(item.getItemId() == R.id.action_seven_days && !item.isChecked()){
            item.setChecked(true);
            wait(true);
            getArticles("7");
        }else if(item.getItemId() == R.id.action_thirty_days && !item.isChecked()){
            item.setChecked(true);
            wait(true);
            getArticles("30");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onArticleClick(View view, Result item) {
        Bundle bundle = new Bundle();
        bundle.putString("data", new Gson().toJson(item));
        FragmentNavigator.Extras.Builder navigator = new FragmentNavigator.Extras.Builder();
        navigator.addSharedElement(view, view.getTransitionName());
        setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.move_local));
        setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.fade));

        NavController c = NavHostFragment.findNavController(this);
        c.navigate(R.id.nav_article_details, bundle, null,navigator.build() );
    }
}