package com.example.p90jzw.memodemo.data;

import com.example.p90jzw.memodemo.DataUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MemoData extends RealmObject {

    @PrimaryKey
    private int index;

    private boolean checked = false;
    private boolean showCheckBox = false;

    private String header;
    private String text;
    private String editedTime;

    public String getEditedTime() {
        return editedTime;
    }

    public String getEditedTimePreview() {
        String preview = editedTime.substring(0, 13);
        return preview;
    }

    public String getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    public String getHeader() {
        return header;
    }

    public void setEditedTime(String editedTime) {
        this.editedTime = editedTime;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAllText() {
        return header + "\n" + text;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean getShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }


    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
