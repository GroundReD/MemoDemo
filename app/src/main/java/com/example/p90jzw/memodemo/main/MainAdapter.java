package com.example.p90jzw.memodemo.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p90jzw.memodemo.R;
import com.example.p90jzw.memodemo.data.MemoData;
import com.example.p90jzw.memodemo.holder.MainContentViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainContentViewHolder> implements MainAdapterContract.Model, MainAdapterContract.View {
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_CONTENT = 1;

    private Context mContext;
    private List<MemoData> memoDataList;

    public MainAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setMemoItemList(List<MemoData> memoItems) {
        memoDataList = memoItems;
    }



    @NonNull
    @Override
    public MainContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_row, parent, false);
        return new MainContentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainContentViewHolder holder, int position) {
        holder.bind(memoDataList.get(position));
    }


    @Override
    public int getItemCount() {
        return memoDataList != null ? memoDataList.size() :0;

    }
}
