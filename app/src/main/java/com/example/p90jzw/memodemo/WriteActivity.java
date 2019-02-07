package com.example.p90jzw.memodemo;

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

import com.example.p90jzw.memodemo.data.MemoData;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class WriteActivity extends AppCompatActivity {

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
    private Realm realm;
    private int memoIndex;
    private InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);


        //set toolbar
        memoToolbar = findViewById(R.id.memo_toolbar);
        setSupportActionBar(memoToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow);
            getSupportActionBar().setTitle("메모");
        }

        //get Realm instance
        realm = Realm.getDefaultInstance();

        // two intent
        // - crate new memo = -1
        // - edit existed memo >= 0
        memoIndex = getIntent().getIntExtra("MEMO_INDEX", NEW);
        Log.d("index", String.valueOf(memoIndex));

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.write_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (memoIndex == NEW) {
                    saveNewMemo(String.valueOf(memo.getText()));
                } else {
                    saveMemo(String.valueOf(memo.getText()));
                }
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.no_change, R.anim.slide_right);
                return true;
            case R.id.save_memo:
                if (memoIndex == NEW) {
                    saveNewMemo(String.valueOf(memo.getText()));
                } else {
                    saveMemo(String.valueOf(memo.getText()));
                }
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                memo.clearFocus();
                imm.hideSoftInputFromWindow(memo.getWindowToken(), 0);
               return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMemo(String memo) {
        MemoData memoData = realm.where(MemoData.class).equalTo("index", memoIndex).findAll().get(0);

        realm.beginTransaction();
        String header = memo.substring(0, memo.indexOf("\n"));
        String text = memo.substring(memo.indexOf("\n")).trim();
        memoData.setEditedTime(editedDate);
        memoData.setHeader(header);
        memoData.setText(text);
        realm.commitTransaction();
    }


    private void saveNewMemo(String memo) {
        MemoData memoData = new MemoData();
        int currentIndex = getAutoIncrementIndex(memoData);
        Log.d("curIndex", String.valueOf(currentIndex));
        memoData.setIndex(currentIndex);
        memoData.setEditedTime(editedDate);

        if (memo != null) {
            if (memo.contains("\n")) {
                String header = memo.substring(0, memo.indexOf("\n"));
                String text = memo.substring(memo.indexOf("\n")).trim();
                memoData.setHeader(header);
                memoData.setText(text);
            } else {
                String header = memo;
                String text = "";
                memoData.setHeader(header);
                memoData.setText(text);
            }
        } else if (memo.isEmpty()) {
            return;
        }

        realm.beginTransaction();
        realm.copyToRealm(memoData);
        realm.commitTransaction();
    }

    private String saveEditedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        return format.format(new Date());
    }


    private int getAutoIncrementIndex(Object object) {
        int nextIndex;
        Number currentIndex = null;

        if (object instanceof MemoData) {
            currentIndex = realm.where(MemoData.class).max("index");
        }

        if (currentIndex == null) {
            nextIndex = 0;
        } else {
            nextIndex = currentIndex.intValue() + 1;
        }
        return nextIndex;
    }

    @OnClick(R.id.write_iv_delete)
    void remove() {
        Log.e("delIndex", String.valueOf(memoIndex));
        RealmResults<MemoData> results = realm.where(MemoData.class).equalTo("index", memoIndex).findAll();

        if (results.isEmpty()) {
            finish();
            return;
        }
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();

        finish();

    }

    @OnClick(R.id.write_iv_write_new)
    void createNewMemo() {
        if (memoIndex != NEW) {
            saveMemo(String.valueOf(memo.getText()));
        } else {
            saveNewMemo(String.valueOf(memo.getText()));
        }

        //new writeActivity
        Intent intent = new Intent(this, WriteActivity.class);
        intent.putExtra("MEMO_INDEX", -1);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    void setCurrentIndex() {

    }

    void showExistedMemo(int memoIndex) {
        RealmResults<MemoData> results = realm.where(MemoData.class).equalTo("index", memoIndex).findAll();
        memo.setText(results.get(0) != null ? results.get(0).getAllText() : "");
        date.setText(results.get(0) != null ? results.get(0).getEditedTime() : "yyyy-MM-dd");
    }

    void showEmptyMemo() {
        memo.setText("");
        date.setText(editedDate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onBackPressed() {
        if (memoIndex != NEW) {
            saveMemo(String.valueOf(memo.getText()));
        } else {
            saveNewMemo(String.valueOf(memo.getText()));
        }

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("new memo index ", String.valueOf(memoIndex));
        editedDate = saveEditedDate();
        if (memoIndex != NEW) {
            showExistedMemo(memoIndex);
        } else {
            showEmptyMemo();
        }
    }


}
