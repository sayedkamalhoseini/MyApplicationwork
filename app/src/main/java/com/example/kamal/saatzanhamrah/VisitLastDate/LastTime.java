package com.example.kamal.saatzanhamrah.VisitLastDate;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "time")
public class LastTime {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "token")
    private int token;

    @ColumnInfo(name = "token_delete")
    private String token_delete;

    @ColumnInfo(name = "startWorkDate")
    private String startWorkDate;

    @ColumnInfo(name = "startWorkTime")
    @Nullable
    private String startWorkTime;

    @ColumnInfo(name = "start_date_miladi")
    @Nullable
    private String start_date_miladi;

    @ColumnInfo(name = "endWorkDate")
    @Nullable
    private String endWorkDate;

    @ColumnInfo(name = "endWorkTime")
    @Nullable
    private String endWorkTime;

    @ColumnInfo(name = "workTime")
    @Nullable
    private String workTime;

    @ColumnInfo(name = "explains")
    @Nullable
    private String explains;

    @ColumnInfo(name = "confirm_employer")
    @Nullable
    private String confirm_employer;

    @ColumnInfo(name = "isSelected")
    @Nullable
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getToken_delete() {
        return token_delete;
    }

    public void setToken_delete(String token_delete) {
        this.token_delete = token_delete;
    }

    public String getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getStart_date_miladi() {
        return start_date_miladi;
    }

    public void setStart_date_miladi(String start_date_miladi) {
        this.start_date_miladi = start_date_miladi;
    }

    public String getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(String endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getConfirm_employer() {
        return confirm_employer;
    }

    public void setConfirm_employer(String confirm_employer) {
        this.confirm_employer = confirm_employer;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
