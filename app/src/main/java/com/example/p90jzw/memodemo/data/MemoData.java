package com.example.p90jzw.memodemo.data;

import androidx.annotation.NonNull;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MemoData extends RealmObject {

    @PrimaryKey
    private int index;

    private String header;
    private String text;
    private String editedTime;

    public String getEditedTime() {
        return editedTime;
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

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
