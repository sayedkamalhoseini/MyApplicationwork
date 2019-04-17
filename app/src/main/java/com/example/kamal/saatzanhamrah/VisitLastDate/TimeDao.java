package com.example.kamal.saatzanhamrah.VisitLastDate;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;

import java.util.List;

@Dao
public interface TimeDao {
    @Query("SELECT * FROM Time where token>=:token1 and token<=:token2 and username Like :username order by token_delete asc limit 20 offset :start_row ")
    List<LastTime> getAllPaging(int token1, int token2, int start_row, String username);

    @Query("SELECT * FROM Time where token>=:token1 and token<=:token2 and username Like :username order by token_delete asc")
    List<LastTime> getAll(int token1, int token2,String username);

//    @Query("SELECT * FROM time where update_username LIKE  :update_usernamecom.example.kamal.saatzanhamrah.TimeEmploy AND pass LIKE :password")
//    Employe findEmployee(String update_username, String password);
//
//    @Query("SELECT username FROM Employee where update_username LIKE  :update_username AND pass LIKE :password")
//    String findUsername(String update_username, String password);
//
//    @Query("SELECT COUNT(*) from Employee")com.example.kamal.saatzanhamrah.TimeEmploy
//    int countUsers();

    @Insert
    void insert(LastTime... times);

//    @Delete
//    void delete(Employe employe);

    @Query("Update time set endWorkDate =:end_date, endWorkTime=:end_time, workTime=:worktime, explains=:explains where token_delete=:token_delete")
    void update(String end_date, String end_time, String worktime, String explains, String token_delete);

}
