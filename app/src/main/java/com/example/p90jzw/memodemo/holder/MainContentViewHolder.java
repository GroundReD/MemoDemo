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

public class MainContentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_checkbox)
    CheckBox checkBox;
    @BindView(R.id.item_tv_text_header)
    TextView textTitle;
    @BindView(R.id.item_tv_edited_time)
    TextView textEditTime;
    @BindView(R.id.item_tv_contents)
    TextView textContent;

    Context mContext;
    OnItemClickListener onItemClickListener;

    public MainContentViewHolder(@NonNull View itemView, Context mContext, OnItemClickListener onItemClickListener) {
        super(itemView);

        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;

        ButterKnife.bind(this, itemView);
    }

    public void bind(Context context, MemoData memoData, int position) {

        itemView.setOnClickListener(v -> {
            if(!memoData.getShowCheckBox()) {
                onItemClickListener.onItemClick(position);
            } else {
                onItemClickListener.onItemCheckClick(position);
            }
        });

        checkBox.setOnClickListener(v-> {
            onItemClickListener.onItemCheckClick(position);
        });

        textTitle.setText(memoData.getHeader());
        textEditTime.setText(memoData.getEditedTimePreview());
        textContent.setText(memoData.getText());

        if(memoData.getShowCheckBox()) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        if (memoData.isChecked()) {
            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightBlueOp40));
            if(!checkBox.isChecked()) {
                checkBox.setChecked(true);
            }
        } else {
            itemView.setBackgroundColor(Color.TRANSPARENT);
            if(checkBox.isChecked()) {
                checkBox.setChecked(false);
            }
        }

    }
}
