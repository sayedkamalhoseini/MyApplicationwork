package com.example.kamal.saatzanhamrah.TimeEmploy;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.kamal.saatzanhamrah.RoomPackage.AppDatabase;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;


public class DatabaseInitializer_Time_End {


    private static final String TAG = DatabaseInitializer_Time_End.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, String username, String end_date, String end_time, String worktime, String explains, String token_delete) {
        PopulateDbAsync task = new PopulateDbAsync(db, username, end_date, end_time, worktime,explains,token_delete);
        task.execute();
    }


    private static LastTime addUser(final AppDatabase db, LastTime time) {
        db.timeDao().insert(time);
        return time;
    }

    private static void populateWithTestData(AppDatabase db, String username, String end_date, String end_time, String worktime, String explains, String tokenDelete) {

       db.timeDao().update(end_date,end_time,worktime,explains,tokenDelete);


//            List<Employe> userList = db.userDao().getAll();
//            Log.d(DatabaseInitializer_Register.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private String username,end_date,end_time,worktime,explains,tokenDelete;



        PopulateDbAsync(AppDatabase db, String username, String end_date, String end_time, String worktime, String explains, String token_delete) {
            mDb = db;
            this.username = username;
            this.end_date=end_date;
            this.end_time=end_time;
            this.worktime=worktime;
            this.explains=explains;
            this.tokenDelete=token_delete;

        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb,username, end_date, end_time, worktime,explains,tokenDelete);
            return null;
        }

    }


}
