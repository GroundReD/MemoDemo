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
    private Realm realm;

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
    public void showItemCheckBox() {
        ArrayList<MemoData> memoDataList = new ArrayList<>();
        try {
            RealmResults<MemoData> results = realm.where(MemoData.class).findAll();
            memoDataList.addAll(realm.copyFromRealm(results));

        } catch (Exception e) {
            Log.e("show check box item", e.getMessage());
        }

        for (MemoData memoData : memoDataList) {
            memoData.setShowCheckBox(true);
        }

        adapterModel.updateMemo(memoDataList);
        adapterView.notifyAdapter();

    }

    @Override
    public void deleteCheckedItems() {
        ArrayList<MemoData> memoDataList = new ArrayList<>();
        try {
            RealmResults<MemoData> removeResult = realm.where(MemoData.class).equalTo("checked",true).findAll();

            realm.beginTransaction();
            removeResult.deleteAllFromRealm();
            realm.commitTransaction();

            RealmResults<MemoData> results = realm.where(MemoData.class).findAll();
            realm.beginTransaction();
            for (MemoData memo :
                    results) {
                memo.setShowCheckBox(false);
                memo.setChecked(false);
            }
            realm.commitTransaction();

            memoDataList.addAll(realm.copyFromRealm(results));

        } catch (Exception e) {
            Log.e("delete items", e.getMessage());
        }

        for (MemoData memoData : memoDataList) {
            memoData.setShowCheckBox(false);
        }

        adapterModel.updateMemo(memoDataList);
        adapterView.notifyAdapter();
        view.showItemCount(memoDataList.size());
    }

    @Override
    public void cancelCheckItems() {
        ArrayList<MemoData> memoDataList = new ArrayList<>();
        try {
            RealmResults<MemoData> results = realm.where(MemoData.class).findAll();
            realm.beginTransaction();
            for (MemoData memo:results) {
                memo.setChecked(false);
                memo.setShowCheckBox(false);
            }
            realm.commitTransaction();
            memoDataList.addAll(realm.copyFromRealm(results));
        } catch (Exception e) {
            Log.e("cancel check", e.getMessage());
        }

        adapterModel.updateMemo(memoDataList);
        adapterView.notifyAdapter();
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

    @Override
    public void onItemCheckClick(int position) {
        MemoData memoData = adapterModel.getItem(position);

        boolean currentCheck = memoData.isChecked();
        memoData.setChecked(!currentCheck);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(memoData);
        realm.commitTransaction();

        adapterModel.updateMemoItem(memoData, position);
        adapterView.notifyAdapterItem(position);

    }
}
