package com.example.p90jzw.memodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

import android.app.Application;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.p90jzw.memodemo.data.MemoData;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar memoToolbar;
    @BindView(R.id.write_tv_edited_date)
    TextView date;
    @BindView(R.id.write_et_memo)
    EditText memo;
    @BindView(R.id.write_iv_delete)
    ImageView delete;
    @BindView(R.id.write_iv_write_new)
    ImageView writeNew;

    String edittedDate;
    Realm realm;
    int currentIndex;

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

        edittedDate = saveEdditedDate();
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
                saveMemo(String.valueOf(memo.getText()));
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMemo(String memo) {
        MemoData memoData = new MemoData();
        int currentIndex = getAutoIncrementIndex(new MemoData());
        memoData.setIndex(currentIndex);
        memoData.setEditedTime(edittedDate);

        if (memo != null ) {
            String header = memo.substring(0, memo.indexOf("\n"));
            String text = memo.substring(memo.indexOf("\n"));
            memoData.setHeader(header);
            memoData.setText(text);
        }

        realm.beginTransaction();
        realm.copyFromRealm(memoData);
        realm.commitTransaction();
    }

    private String saveEdditedDate() {
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
        }
        else {
            nextIndex = currentIndex.intValue() + 1;
        }
        return nextIndex;
    }

    @OnClick(R.id.write_iv_delete)
    void remove() {
        RealmResults<MemoData> results = realm.where(MemoData.class).equalTo("index",currentIndex).findAll();

        if(results.isEmpty()) {
            return;
        }
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    @OnClick(R.id.write_iv_write_new)
    void createNewMemo() {
        saveMemo(String.valueOf(memo.getText()));
        //new writeActivity

    }


}
