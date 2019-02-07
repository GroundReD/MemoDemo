package com.example.p90jzw.memodemo.main;

import android.content.Context;
import android.util.Log;

import com.example.p90jzw.memodemo.data.MemoData;
import com.example.p90jzw.memodemo.listener.OnItemClickListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainPresenter implements MainContract.Presenter, OnItemClickListener {

    private MainContract.View view;
    private MainAdapterContract.View adapterView;
    private MainAdapterContract.Model adapterModel;
    Realm realm;

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void detachView() {
        view = null;
        realm.close();
    }


    @Override
    public void loadItems() {
        ArrayList<MemoData> memoDataList = new ArrayList<>();
        try {
            RealmResults<MemoData> results = realm.where(MemoData.class).findAll();
            memoDataList.addAll(realm.copyFromRealm(results));
        } catch (Exception e) {
            Log.e("load item", e.getMessage());
        }

        adapterModel.updateMemo(memoDataList);
        adapterView.notifyAdapter();
        view.showItemCount(memoDataList.size());

    }

    @Override
    public void showItemCheckBox(boolean showCheckBox) {
        ArrayList<MemoData> memoDataList = new ArrayList<>();
        try {
            RealmResults<MemoData> results = realm.where(MemoData.class).findAll();
            memoDataList.addAll(realm.copyFromRealm(results));

        } catch (Exception e) {
            Log.e("show check box item", e.getMessage());
        }

        for (MemoData memoData : memoDataList) {
            memoData.setShowCheckBox(showCheckBox);
        }

        adapterModel.updateMemo(memoDataList);
        adapterView.notifyAdapter();

    }

    @Override
    public void deleteCheckedItem(Context context) {

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
