package com.example.p90jzw.memodemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p90jzw.memodemo.data.MemoData;
import com.example.p90jzw.memodemo.holder.MainContentViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainAdapterContract.Model, MainAdapterContract.View {
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_CONTENT = 1;

    private Context mContext;
    private List<MemoData> memoDataList;

    public MainAdapter(Context mContext){
        this.mContext = mContext;
        memoDataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_row, parent, false);
        return new MainContentViewHolder(view);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((MainContentViewHolder) viewHolder).bind(memoDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return memoDataList.size();
    }
}
