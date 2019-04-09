package com.example.kamal.saatzanhamrah.RoomPackage;



import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    @Query("SELECT * FROM employee where update_username LIKE  :update_username AND pass LIKE :password")
    Employee findByName(String update_username, String password);

    @Query("SELECT COUNT(*) from employee")
    int countUsers();

    @Insert
    void insertAll(Employee... employees);

    @Delete
    void delete(Employee employee);
}
