package com.example.kamal.saatzanhamrah.RoomPackage;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kamal.saatzanhamrah.RoomPackage.AppDatabase;
import com.example.kamal.saatzanhamrah.RoomPackage.Employee;

import java.util.List;


    public class DatabaseInitializer {


        private static final String TAG = DatabaseInitializer.class.getName();

        public static void populateAsync(@NonNull final AppDatabase db,String username,String password,String update_username,String email) {
            PopulateDbAsync task = new PopulateDbAsync(db,username,password,update_username,email);
            task.execute();
        }

    public static void populateSync(@NonNull final AppDatabase db,String username,String password,String update_username,String email) {
        populateWithTestData(db,username,password,update_username,email);
    }

        private static Employee addUser(final AppDatabase db, Employee employee) {
            db.employeeDao().insert(employee);
            return employee;
        }

        private static void populateWithTestData(AppDatabase db,String username,String password,String update_username,String email) {
            Employee employee = new Employee();
            employee.setUsername(username);
            employee.setUpdate_username(update_username);
            employee.setPassword(password);
            employee.setEmail(email);
            addUser(db, employee);

//            List<Employee> userList = db.userDao().getAll();
//            Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
        }

        private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

            private final AppDatabase mDb;
            private String username,password,update_username,email;


            PopulateDbAsync(AppDatabase db,String username,String password,String update_username,String email) {
                mDb = db;
                this.username=username;
                this.password=password;
                this.email=email;
                this.update_username=update_username;
            }

            @Override
            protected Void doInBackground(final Void... params) {
                populateWithTestData(mDb,username,password,update_username,email);
                return null;
            }

        }


    }
