package com.example.p90jzw.memodemo.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.p90jzw.memodemo.R;
import com.example.p90jzw.memodemo.data.MemoData;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    MainAdapter mAdapter;

    ArrayList<MemoData> memoDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // tool bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        memoDataList = new ArrayList<>();

        MemoData m = new MemoData();
        m.setIndex(1);
        m.setEditedTime("2019-01-29");
        m.setHeader("asdfsafd");
        m.setText("asdfjsdaf");

        memoDataList.add(m);
        m.setIndex(2);
        memoDataList.add(m);
        m.setIndex(3);
        memoDataList.add(m);
        m.setIndex(4);
        memoDataList.add(m);
        m.setIndex(5);

        mAdapter = new MainAdapter(this);
        mAdapter.setMemoItemList(memoDataList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.hasFixedSize();
        mRecyclerView.setAdapter(mAdapter);


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
}
