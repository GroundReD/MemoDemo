package com.example.p90jzw.memodemo.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.p90jzw.memodemo.R;
import com.example.p90jzw.memodemo.data.MemoData;
import com.example.p90jzw.memodemo.listener.OnItemClickListener;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainContentsCardViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_tv_text_header_grid)
    TextView textTitle;
    @BindView(R.id.item_tv_edited_time_grid)
    TextView textEditTime;
    @BindView(R.id.item_tv_contents_grid)
    TextView textContent;

    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MainContentsCardViewHolder(@NonNull View itemView, Context mContext, OnItemClickListener onItemClickListener) {
        super(itemView);

        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;

        ButterKnife.bind(this, itemView);
    }

    public void bind(Context context, MemoData memoData, int position) {

        itemView.setOnClickListener(v -> {
            if (!memoData.getShowCheckBox()) {
                onItemClickListener.onItemClick(position);
            } else {
                onItemClickListener.onItemCheckClick(position);
            }
        });

        textTitle.setText(memoData.getHeader());
        textEditTime.setText(memoData.getEditedTimePreview());
        textContent.setText(memoData.getText());

        if (memoData.isChecked()) {
            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightBlueOp40));
        } else {
            itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}

