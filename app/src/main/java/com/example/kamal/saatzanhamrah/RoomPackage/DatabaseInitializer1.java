package com.example.kamal.saatzanhamrah.RoomPackage;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

public class DatabaseInitializer1 {

    private Employee employee;

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, String update_username, String password) {
        PopulateDbAsync task = new PopulateDbAsync(db,update_username,password);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db,String username,String password,String update_username,String email) {
        populateWithTestData(db,username,password,update_username,email);
    }

    private static Employee addUser(final AppDatabase db, Employee employee) {
        db.employeeDao().insertAll(employee);
        return employee;
    }

    private static Employee populateWithTestData(AppDatabase db, String update_username, String password) {
        Employee employee = new Employee();


//        addUser(db, employee);

            employee = db.employeeDao().findByName(update_username,password);

            if (employee!=null)
                return employee;
            else
                return null;
//            Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<String, Void, Employee> {

        private final AppDatabase mDb;
        private String password,update_username;


        PopulateDbAsync(AppDatabase db,String update_username,String password) {
            mDb = db;
            this.password=password;
            this.update_username=update_username;
        }

        @Override
        protected void onPostExecute(Employee employee) {
            super.onPostExecute(employee);
        }

        @Override
        protected Employee doInBackground(String... strings) {
            return  populateWithTestData(mDb,update_username,password);
        }
    }


}
