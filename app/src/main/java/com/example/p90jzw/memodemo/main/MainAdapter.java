package com.example.p90jzw.memodemo.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p90jzw.memodemo.R;
import com.example.p90jzw.memodemo.data.MemoData;
import com.example.p90jzw.memodemo.holder.MainContentViewHolder;
import com.example.p90jzw.memodemo.holder.MainContentsCardViewHolder;
import com.example.p90jzw.memodemo.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainAdapterContract.Model, MainAdapterContract.View {

    private Context mContext;
    private List<MemoData> memoDataList;
    private OnItemClickListener onItemClickListener;
    private boolean isGridView;

    public MainAdapter(Context mContext) {
        this.mContext = mContext;
        isGridView = false;
    }

    public void setGridView(boolean gridView) {
        isGridView = gridView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(isGridView? R.layout.item_text_card:R.layout.item_text_row, parent, false);
        return isGridView ? new MainContentsCardViewHolder(view, mContext,onItemClickListener) :new MainContentViewHolder(view, mContext, onItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(!isGridView) {
            MainContentViewHolder listViewHolder = (MainContentViewHolder) holder;
            listViewHolder.bind(mContext, memoDataList.get(position), position);
        } else {
            MainContentsCardViewHolder cardViewHolder= (MainContentsCardViewHolder) holder;
            cardViewHolder.bind(mContext,memoDataList.get(position),position);
        }

    }


    @Override
    public int getItemCount() {
        return memoDataList != null ? memoDataList.size() : 0;

    }

    @Override
    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public void notifyAdapterItem(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void updateMemo(ArrayList<MemoData> memoDataItems) {
        memoDataList = memoDataItems;
    }

    @Override
    public void updateMemoItem(MemoData memoData, int position) {
        memoDataList.set(position,memoData);

    }

    @Override
    public void clearItem() {

    }

    @Override
    public MemoData getItem(int position) {
        return memoDataList.get(position);
    }
}
