package com.example.p90jzw.memodemo;

import com.example.p90jzw.memodemo.data.MemoData;

import java.text.SimpleDateFormat;
import java.util.Date;
import io.realm.Realm;

public class DataUtils {

    private static DataUtils newInstance = new DataUtils();
    private Realm realm;

    public static DataUtils getInstance() {
        return newInstance;
    }

    public DataUtils() {
        realm = Realm.getDefaultInstance();
    }
    public Realm getRealmInstance() {
        return realm;
    }


    public String saveEditedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        return format.format(new Date());
    }

    public int getAutoIncrementIndex(Object object) {
        int nextIndex;
        Number currentIndex = null;

        if (object instanceof MemoData) {
            currentIndex = realm.where(MemoData.class).max("index");
        }

        if (currentIndex == null) {
            nextIndex = 0;
        } else {
            nextIndex = currentIndex.intValue() + 1;
        }
        return nextIndex;
    }
}
