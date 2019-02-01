package com.example.p90jzw.memodemo.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    MainAdapter mAdapter;
    MainPresenter mainPresenter;

    ArrayList<MemoData> memoDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // tool bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);


        memoDataList = new ArrayList<>();

//        MemoData m = new MemoData();
//        m.setIndex(0);
//        m.setEditedTime("2019-01-29");
//        m.setHeader("asdfsafd");
//        m.setText("asdfjsdaf");
//
//        memoDataList.add(m);

        mAdapter = new MainAdapter(this);
        mAdapter.addMemo(memoDataList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.hasFixedSize();
        mRecyclerView.setAdapter(mAdapter);

        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.setMainAdapterModel(mAdapter);
        mainPresenter.setMainAdapterView(mAdapter);


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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm realm;
    }

    @Override
    public void showMemo(int memoIndex) {
        Intent intent = new Intent(this, WriteActivity.class);
        intent.putExtra("MEMO_INDEX", memoIndex);
        startActivity(intent);
    }

    @OnClick(R.id.main_iv_write_new)
    void crateNewMemo() {
        showMemo(-1);
    }
}
