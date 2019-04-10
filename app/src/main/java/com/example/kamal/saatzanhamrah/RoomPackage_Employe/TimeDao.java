package com.example.kamal.saatzanhamrah.RoomPackage_Employe;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

public interface TimeDao {
//    @Query("SELECT * FROM Employee")
//    List<Employe> getAll();

//    @Query("SELECT * FROM time where update_username LIKE  :update_username AND pass LIKE :password")
//    Employe findEmployee(String update_username, String password);
//
//    @Query("SELECT username FROM Employee where update_username LIKE  :update_username AND pass LIKE :password")
//    String findUsername(String update_username, String password);
//
//    @Query("SELECT COUNT(*) from Employee")
//    int countUsers();

    @Insert
    void insert(Time... times);

//    @Delete
//    void delete(Employe employe);

    @Query("Update time set end_date =:end_date, end_time=:end_time, worktime=:worktime, explains=:explains where token_delete=:token_delete")
    void update(String end_date,String end_time,String worktime,String explains,String token_delete);

}
