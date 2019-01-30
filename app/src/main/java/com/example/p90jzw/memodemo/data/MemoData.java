package com.example.p90jzw.memodemo.data;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MemoData extends RealmObject {

    @PrimaryKey
    private int index;

    private String header;
    private String text;
    private Date editedTime;

    public Date getEditedTime() {
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

    public void setEditedTime(Date editedTime) {
        this.editedTime = editedTime;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
