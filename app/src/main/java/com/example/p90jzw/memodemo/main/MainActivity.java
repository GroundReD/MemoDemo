package com.example.p90jzw.memodemo.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.p90jzw.memodemo.R;
import com.example.p90jzw.memodemo.WriteActivity;
import com.example.p90jzw.memodemo.data.MemoData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_tv_number_of_memo)
    TextView memoCountView;

    private MainAdapter mAdapter;
    private MainPresenter mainPresenter;

    private ArrayList<MemoData> memoDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // tool bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.all_memo);
        }


        memoDataList = new ArrayList<>();

//        MemoData m = new MemoData();
//        m.setIndex(0);
//        m.setEditedTime("2019-01-29");
//        m.setHeader("asdfsafd");
//        m.setText("asdfjsdaf");
//
//        memoDataList.add(m);

        mAdapter = new MainAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.hasFixedSize();
        mRecyclerView.setAdapter(mAdapter);

        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.setMainAdapterModel(mAdapter);
        mainPresenter.setMainAdapterView(mAdapter);
//        mainPresenter.loadItems(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.loadItems(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_memo:
                mainPresenter.showItemCheckBox(this);
                return true;
            case R.id.search_memo:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public void showMemo(int memoIndex) {
        Intent intent = new Intent(this, WriteActivity.class);
        intent.putExtra("MEMO_INDEX", memoIndex);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left, R.anim.no_change);
    }

    @Override
    public void showItemCount(int itemCount) {
        if (itemCount > 0) {
            memoCountView.setText(getString(R.string.num_of_memo, itemCount));
        } else {
            memoCountView.setText("0개의 메모");
        }

    }

    @OnClick(R.id.main_iv_write_new)
    void crateNewMemo() {
        showMemo(-1);
    }


}
