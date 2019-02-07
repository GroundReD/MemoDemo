package com.example.p90jzw.memodemo.write;

import com.example.p90jzw.memodemo.data.MemoData;

import io.realm.Realm;
import io.realm.RealmResults;

public class WritePresent implements WriteContract.Presenter {

    private WriteContract.View view;
    Realm realm;

    @Override
    public void attachView(WriteContract.View view) {
        this.view = view;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void detachView() {
        this.view = null;
        realm.close();
    }

    @Override
    public void editExistedMemo(MemoData memoData, String memo) {

        if (memo != null) {
            String header = memo.substring(0, memo.indexOf("\n"));
            String text = memo.substring(memo.indexOf("\n")).trim();
            if (memoData != null) {
                memoData.setHeader(header);
                memoData.setText(text);
            }
        }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(memoData);
        realm.commitTransaction();
    }

    @Override
    public void saveNewMemo(MemoData memoData, String memo) {
        String header;
        String text;
        if (memo != null) {
            if (memo.contains("\n")) {
                header = memo.substring(0, memo.indexOf("\n"));
                text = memo.substring(memo.indexOf("\n")).trim();
                memoData.setHeader(header);
                memoData.setText(text);
            } else {
                header = memo;
                text = "";
                memoData.setHeader(header);
                memoData.setText(text);
            }

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(memoData);
            realm.commitTransaction();

        }
    }

    @Override
    public void deleteMemo(int index) {
        if (index != -1) {
            RealmResults<MemoData> results = realm.where(MemoData.class).equalTo("index", index).findAll();
            realm.beginTransaction();
            results.deleteAllFromRealm();
            realm.commitTransaction();
        }
    }

    @Override
    public MemoData getExistedMemo(int index) {
        MemoData memoData;
        realm.beginTransaction();
        MemoData realmMemoData = realm.where(MemoData.class).equalTo("index", index).findAll().get(0);
        memoData = realm.copyFromRealm(realmMemoData);
        realm.commitTransaction();
        return memoData;
    }


}
