package com.qwy.library.recyclerview;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener {

    int lastItem, totalItem;

    boolean isLoading;

    private LinearLayoutManager mLinearLayoutManager;


    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisiableItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        lastItem = lastVisiableItemPosition+1;
        totalItem = mLinearLayoutManager.getItemCount();
    }
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if(totalItem == lastItem && newState == 0){
            if(isLoading == false){
                isLoading = true;
                Log.i("info9","加载 ...");
                onLoadMore();
            }
        }
    }

    public abstract void onLoadMore();

}