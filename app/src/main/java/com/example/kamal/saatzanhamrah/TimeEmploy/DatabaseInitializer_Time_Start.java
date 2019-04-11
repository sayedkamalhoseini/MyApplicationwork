package com.example.kamal.saatzanhamrah.TimeEmploy;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.kamal.saatzanhamrah.RoomPackage.AppDatabase;
import com.example.kamal.saatzanhamrah.RoomPackage_Employe.Time;


public class DatabaseInitializer_Time_Start {


    private static final String TAG = DatabaseInitializer_Time_Start.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, String username, int token, String token_delete, String start_date, String start_time, String start_date_miladi, String kind) {
        PopulateDbAsync task = new PopulateDbAsync(db, username, token, token_delete, start_date, start_time,start_date_miladi,kind);
        task.execute();
    }


    private static void addUser(final AppDatabase db,Time time) {
        db.timeDao().insert(time);

    }

    private static void populateWithTestData(AppDatabase db, String username,int token, String token_delete, String start_date, String start_time, String start_date_miladi, String kind) {
        Time  time= new Time();
        time.setUsername(username);
        time.setToken(token);
        time.setToken_delete(token_delete);
        time.setStart_date(start_date);
        time.setStart_time(start_time);
        time.setStart_date_miladi(start_date_miladi);

        addUser(db,time);



//            List<Employe> userList = db.userDao().getAll();
//            Log.d(DatabaseInitializer_Register.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private String username, token_delete, start_date,start_time,start_date_miladi,kind;
        private int token;


        PopulateDbAsync(AppDatabase db, String username, int token, String token_delete, String start_date, String start_time, String start_date_miladi, String kind) {
            mDb = db;
            this.username = username;
            this.token = token;
            this.token_delete = token_delete;
            this.start_date = start_date;
            this.start_time = start_time;
            this.start_date_miladi=start_date_miladi;
            this.kind = kind;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb, username, token, token_delete,start_date,start_time,start_date_miladi,kind);
            return null;
        }

    }


}
