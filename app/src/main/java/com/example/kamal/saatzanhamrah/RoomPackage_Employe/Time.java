package com.example.kamal.saatzanhamrah.RoomPackage_Employe;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "time")
public class Time {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "token")
    private int token;

    @ColumnInfo(name = "token_delete")
    private String token_delete;

    @ColumnInfo(name = "start_date")
    private String start_date;

    @ColumnInfo(name = "start_time")
    @Nullable
    private String start_time;

    @ColumnInfo(name = "start_date_miladi")
    @Nullable
    private String start_date_miladi;

    @ColumnInfo(name = "end_date")
    @Nullable
    private String end_date;

    @ColumnInfo(name = "end_time")
    @Nullable
    private String end_time;

    @ColumnInfo(name = "worktime")
    @Nullable
    private String worktime;

    @ColumnInfo(name = "explains")
    @Nullable
    private String explains;

    @ColumnInfo(name = "confirm_employer")
    @Nullable
    private int confirm_employer;

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStart_date_miladi() {
        return start_date_miladi;
    }

    public void setStart_date_miladi(String start_date_miladi) {
        this.start_date_miladi = start_date_miladi;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public int getConfirm_employer() {
        return confirm_employer;
    }

    public void setConfirm_employer(int confirm_employer) {
        this.confirm_employer = confirm_employer;
    }
}
