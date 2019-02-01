package com.example.p90jzw.memodemo.main;

import android.content.Context;

import com.example.p90jzw.memodemo.data.MemoData;
import com.example.p90jzw.memodemo.listener.OnItemClickListener;

public class MainPresenter implements MainContract.Presenter, OnItemClickListener {

    private MainContract.View view;
    private MainAdapterContract.View adapterView;
    private MainAdapterContract.Model adapterModel;

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void loadItems(Context context) {

    }

    @Override
    public void setMainAdapterModel(MainAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void setMainAdapterView(MainAdapterContract.View adapterView) {
        this.adapterView = adapterView;
        this.adapterView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        MemoData memoData = adapterModel.getItem(position);
        view.showMemo(memoData.getIndex());
    }
}
