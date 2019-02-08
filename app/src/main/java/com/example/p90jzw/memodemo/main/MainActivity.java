package com.example.p90jzw.memodemo.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p90jzw.memodemo.R;
import com.example.p90jzw.memodemo.write.WriteActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    public final static int EDIT = 0;
    public final static int CANCEL = 1;
    public final static int SEARCH = 2;
    public final static int SEARCH_CANCEL = 4;

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_tv_number_of_memo)
    TextView memoCountView;
    @BindView (R.id.main_iv_delete)
    ImageView memoDelete;
    @BindView(R.id.main_iv_write_new)
    ImageView memoWrite;
    @BindView(R.id.main_iv_grid)
    ImageView gridView;
    @BindView(R.id.main_iv_list)
    ImageView listView;

    private MainAdapter mAdapter;
    private MainPresenter mainPresenter;
    private int mode;
    private boolean isGridView = false;

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


        mAdapter = new MainAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.hasFixedSize();
        mRecyclerView.setAdapter(mAdapter);

        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.setMainAdapterModel(mAdapter);
        mainPresenter.setMainAdapterView(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.loadItems();
        mode = CANCEL;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        switch (mode) {
            case EDIT:
                menu.findItem(R.id.cancel).setVisible(true);
                menu.findItem(R.id.edit_memo).setVisible(false);
                break;
            case CANCEL:
                menu.findItem(R.id.cancel).setVisible(false);
                menu.findItem(R.id.edit_memo).setVisible(true);
            case SEARCH:
                menu.findItem(R.id.cancel).setVisible(false);
                menu.findItem(R.id.edit_memo).setVisible(false);
                menu.findItem(R.id.search_memo).setVisible(false);
                menu.findItem(R.id.my_search_bar).setVisible(true);
                menu.findItem(R.id.search_cancel).setVisible(true);
                break;
            case SEARCH_CANCEL:
                menu.findItem(R.id.cancel).setVisible(true);
                menu.findItem(R.id.edit_memo).setVisible(true);
                menu.findItem(R.id.search_memo).setVisible(true);
                menu.findItem(R.id.my_search_bar).setVisible(false);
                menu.findItem(R.id.search_cancel).setVisible(false);
                break;

                default:
                    break;
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_memo:
                mode = EDIT;
                mainPresenter.showItemCheckBox();
                memoDelete.setVisibility(View.VISIBLE);
                memoWrite.setVisibility(View.INVISIBLE);
                return true;
            case R.id.cancel:
                mode = CANCEL;
                mainPresenter.cancelCheckItems();
                memoDelete.setVisibility(View.INVISIBLE);
                memoWrite.setVisibility(View.VISIBLE);
                return true;
            case R.id.search_memo:
                mode = SEARCH;
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;

            case R.id.search_cancel:
                mode=SEARCH_CANCEL;
                break;

                default:
                    break;
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

    @OnClick(R.id.main_iv_delete)
    void deleteSeletedMemo() {
        mode = CANCEL;
        mainPresenter.deleteCheckedItems();
        memoDelete.setVisibility(View.GONE);
        memoWrite.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.main_iv_grid)
    void changeGridLayoutManager (){
        isGridView = true;
        gridView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter.setGridView(isGridView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.main_iv_list)
    void changeLinearLayoutManager() {
        isGridView = false;
        gridView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter.setGridView(isGridView);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setAdapter(mAdapter);
    }

}
