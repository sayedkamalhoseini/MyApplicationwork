package com.example.kamal.saatzanhamrah.RoomPackage_Employe;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TimeDao {
    @Query("SELECT id,token,token_delete,start_date,start_time,end_date,end_time,worktime,confirm_employer FROM Time where token>=:token1 and token<=:token2 order by token_delete asc limit 20 offset :start_row ")
    List<Time> getAllPaging(int token1, int token2, int start_row);

    @Query("SELECT id,token,token_delete,start_date,start_time,end_date,end_time,worktime,confirm_employer FROM Time where token>=:token1 and token<=:token2 order by token_delete asc")
    List<Time> getAll(int token1, int token2);

//    @Query("SELECT * FROM time where update_username LIKE  :update_usernamecom.example.kamal.saatzanhamrah.TimeEmploy AND pass LIKE :password")
//    Employe findEmployee(String update_username, String password);
//
//    @Query("SELECT username FROM Employee where update_username LIKE  :update_username AND pass LIKE :password")
//    String findUsername(String update_username, String password);
//
//    @Query("SELECT COUNT(*) from Employee")com.example.kamal.saatzanhamrah.TimeEmploy
//    int countUsers();

    @Insert
    void insert(Time... times);

//    @Delete
//    void delete(Employe employe);

    @Query("Update Time set end_date =:end_date, end_time=:end_time, worktime=:worktime, explains=:explains where token_delete=:token_delete")
    void update(String end_date, String end_time, String worktime, String explains, String token_delete);

}
