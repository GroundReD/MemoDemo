package com.example.p90jzw.memodemo.main;

import android.content.Context;

public interface MainContract {

    interface View {
        void showMemo(int memoIndex);

        void showItemCount(int count);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void loadItems();

        void showItemCheckBox();

        void deleteCheckedItems();

        void cancelCheckItems();

        void setMainAdapterModel(MainAdapterContract.Model adapterModel);

        void setMainAdapterView(MainAdapterContract.View adapterView);
    }
}
