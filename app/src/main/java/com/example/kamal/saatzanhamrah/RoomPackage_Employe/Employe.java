package com.example.kamal.saatzanhamrah.RoomPackage_Employe;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employe {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdate_username() {
        return update_username;
    }

    public void setUpdate_username(String update_username) {
        this.update_username = update_username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "update_username")
    private String update_username;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "pass")
    private String password;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "kind")
    private String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
