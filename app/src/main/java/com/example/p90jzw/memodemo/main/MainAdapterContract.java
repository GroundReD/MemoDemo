package com.example.p90jzw.memodemo.main;

import com.example.p90jzw.memodemo.data.MemoData;
import com.example.p90jzw.memodemo.listener.OnItemClickListener;

import java.util.ArrayList;

public interface MainAdapterContract {
    interface View {
        void setOnItemClickListener(OnItemClickListener clickListener);

        void notifyAdapter();

        void notifyAdapterItem(int position);
    }

    interface Model {
        void updateMemo(ArrayList<MemoData> memoDataItems);

        void updateMemoItem(MemoData memoData, int position);

        void clearItem();

        MemoData getItem(int position);

    }
}
