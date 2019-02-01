package com.example.p90jzw.memodemo.main;

import android.content.Context;

public interface MainContract {

    interface View {
        void showMemo(int memoIndex);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void loadItems(Context context);

        void setMainAdapterModel(MainAdapterContract.Model adapterModel);

        void setMainAdapterView(MainAdapterContract.View adapterView);
    }
}
