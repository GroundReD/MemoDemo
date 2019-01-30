package com.example.p90jzw.memodemo;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p90jzw.memodemo.data.MemoData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements MainContract.View {

    @BindView(R.id.rc_main_list)
    RecyclerView mRecyclerView;
    MainAdapter mAdapter;

    List<MemoData> memoDataList;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new MainAdapter(getActivity());
        mRecyclerView.hasFixedSize();
        mRecyclerView.setAdapter(mAdapter);

            memoDataList = new ArrayList<>();

            MemoData m = new MemoData();
            m.setEditedTime(new Date(2019-01-29));
            m.setHeader("asdfsafd");
            m.setText("asdfjsdaf");

            memoDataList.add(m);
            memoDataList.add(m);
            memoDataList.add(m);
            memoDataList.add(m);

            mAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }
}
