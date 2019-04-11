package com.example.kamal.saatzanhamrah.RoomPackage_Employe;



import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM Employee")
    List<Employe> getAll();

    @Query("SELECT * FROM Employee where update_username LIKE  :update_username AND pass LIKE :password AND kind LIKE :kind")
    Employe findEmployee(String update_username, String password,String kind);

    @Query("SELECT username FROM Employee where update_username LIKE  :update_username AND pass LIKE :password")
    String findUsername(String update_username, String password);

    @Query("SELECT COUNT(*) from Employee")
    int countUsers();

    @Insert
    void insert(Employe... employes);

    @Delete
    void delete(Employe employe);
}
