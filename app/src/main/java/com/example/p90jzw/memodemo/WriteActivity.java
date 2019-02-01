package com.example.p90jzw.memodemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    String editedDate;
    Realm realm;
    int memoIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);


        //set toolbar
        memoToolbar = findViewById(R.id.memo_toolbar);
        setSupportActionBar(memoToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.iconmonstr_arrow_64_240);
        getSupportActionBar().setTitle("메모");

        //get Realm instance
        realm = Realm.getDefaultInstance();

        // two intent
        // - crate new memo = -1
        // - edit existed memo >= 0
        memoIndex = getIntent().getIntExtra("MEMO_INDEX", NEW);
        if (memoIndex != NEW) {
            showExistedMemo(memoIndex);
        } else {

        }

        editedDate = saveEditedDate();

        Log.d("index",String.valueOf(memoIndex));
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
                finish();
                return true;
            case R.id.save_memo:
                if(memoIndex == NEW) {
                    saveNewMemo(String.valueOf(memo.getText()));
                } else {
                    saveMemo(String.valueOf(memo.getText()));
                }

                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMemo(String memo) {
        MemoData memoData = realm.where(MemoData.class).equalTo("index",memoIndex).findAll().get(0);

        realm.beginTransaction();
        String header = memo.substring(0, memo.indexOf("\n"));
        String text = memo.substring(memo.indexOf("\n"));
        memoData.setEditedTime(editedDate);
        memoData.setHeader(header);
        memoData.setText(text);
        realm.commitTransaction();
    }


    private void saveNewMemo(String memo) {
        MemoData memoData = new MemoData();
        int currentIndex = getAutoIncrementIndex(new MemoData());
        memoData.setIndex(currentIndex);
        memoData.setEditedTime(editedDate);

        if (memo != null) {
            String header = memo.substring(0, memo.indexOf("\n"));
            String text = memo.substring(memo.indexOf("\n"));
            memoData.setHeader(header);
            memoData.setText(text);
        }

        realm.beginTransaction();
        MemoData realmMemoData = realm.createObject(MemoData.class);
        realmMemoData = realm.copyFromRealm(memoData);
        realm.commitTransaction();
    }

    private String saveEditedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 DD일 HH시 mm분");
        return format.format(new Date());
    }


    private int getAutoIncrementIndex(Object object) {
        int nextIndex;
        Number currentIndex = null;

        if (object instanceof Member) {
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
        RealmResults<MemoData> results = realm.where(MemoData.class).equalTo("index", memoIndex).findAll();

        if (results.isEmpty()) {
            return;
        }
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    @OnClick(R.id.write_iv_write_new)
    void createNewMemo() {
        if(memoIndex != NEW) {
            saveMemo(String.valueOf(memo.getText()));
        } else {
            saveNewMemo(String.valueOf(memo.getText()));
        }
        //new writeActivity
        Intent intent = new Intent(this,WriteActivity.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
