package com.example.p90jzw.memodemo;

import com.example.p90jzw.memodemo.data.MemoData;

public interface WriteContract {
    interface View {
        void saveMemo();
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void editExistedMemo(MemoData memoData, String memo);

        void saveNewMemo(MemoData memoData, String memo);

        void deleteMemo(int index);

        MemoData getExistedMemo(int index);


    }
}
