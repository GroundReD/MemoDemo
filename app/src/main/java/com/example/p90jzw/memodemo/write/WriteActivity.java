package com.example.p90jzw.memodemo.write;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p90jzw.memodemo.DataUtils;
import com.example.p90jzw.memodemo.R;
import com.example.p90jzw.memodemo.data.MemoData;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteActivity extends AppCompatActivity implements WriteContract.View {

    public final static int NEW = -1;

    androidx.appcompat.widget.Toolbar memoToolbar;
    @BindView(R.id.write_tv_edited_date)
    TextView date;
    @BindView(R.id.write_et_memo)
    EditText memo;
    @BindView(R.id.write_iv_delete)
    ImageView delete;
    @BindView(R.id.write_iv_write_new)
    ImageView writeNew;

    private String editedDate;
    private int memoIndex;
    private InputMethodManager imm;
    private WritePresent writePresenter;
    private MemoData writeMemoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);

        // set toolbar
        memoToolbar = findViewById(R.id.memo_toolbar);
        setSupportActionBar(memoToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow);
            getSupportActionBar().setTitle("메모");
        }

        // set Presenter
        writePresenter = new WritePresent();
        writePresenter.attachView(this);

        // two intent
        // - crate new memo = -1
        // - edit existed memo >= 0
        memoIndex = getIntent().getIntExtra("MEMO_INDEX", NEW);
        initView();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void initView() {
        setMemoData();
        memo.setText(writeMemoData.getHeader() != null ? writeMemoData.getAllText() : "");
        memo.setSelection(memo.length());
        date.setText(writeMemoData.getEditedTime());

    }

    private void setMemoData() {
        // set memoData
        if (memoIndex != NEW) {
            writeMemoData = writePresenter.getExistedMemo(memoIndex);
        } else {
            writeMemoData = new MemoData();
            writeMemoData.setIndex(DataUtils.getInstance().getAutoIncrementIndex(writeMemoData));
        }
        writeMemoData.setEditedTime(DataUtils.getInstance().saveEditedDate());
        Log.d("index", String.valueOf(memoIndex));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.write_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = String.valueOf(memo.getText());
        if( !text.isEmpty() || !text.equals("")) {
            saveMemo();
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
        }

        // back button 클릭 시 저장 + activity pop
        // save button 클릭 시 저장만
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.no_change, R.anim.slide_right);
                return true;
            case R.id.save_memo:
                memo.clearFocus();
                imm.hideSoftInputFromWindow(memo.getWindowToken(), 0);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.write_iv_delete)
    void remove() {
        if (memoIndex != NEW) {
            writePresenter.deleteMemo(memoIndex);
        }
        finish();
        overridePendingTransition(R.anim.no_change, R.anim.slide_right);

    }

    @OnClick(R.id.write_iv_write_new)
    void createNewMemo() {
        saveMemo();
        //new writeActivity
        Intent intent = new Intent(this, WriteActivity.class);
        intent.putExtra("MEMO_INDEX", -1);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writePresenter.detachView();
    }

    @Override
    public void onBackPressed() {
        saveMemo();
        finish();
        overridePendingTransition(R.anim.no_change, R.anim.slide_right);
    }

    @Override
    public void saveMemo() {
        if (memoIndex != NEW) {
            // 비었으면 삭제
            if (String.valueOf(memo.getText()).equals("")) {
                writePresenter.deleteMemo(memoIndex);
            } else {
                writePresenter.editExistedMemo(writeMemoData, String.valueOf(memo.getText()));
            }
        } else {
            // 비었으면 저장 안함
            if (String.valueOf(memo.getText()).equals("")) {
                return;
            }
            writePresenter.saveNewMemo(writeMemoData, String.valueOf(memo.getText()));
        }
    }
}
