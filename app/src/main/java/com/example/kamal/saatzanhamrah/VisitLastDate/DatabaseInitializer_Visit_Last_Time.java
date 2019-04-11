package com.example.kamal.saatzanhamrah.VisitLastDate;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.widget.ProgressBar;

import com.example.kamal.saatzanhamrah.RoomPackage.AppDatabase;
import com.example.kamal.saatzanhamrah.RoomPackage_Employe.Time;

import org.json.JSONObject;

import java.util.List;


public class DatabaseInitializer_Visit_Last_Time {


    private static final String TAG = DatabaseInitializer_Visit_Last_Time.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, String username, String start_date, String end_date, int start_row, ProgressBar progressBar, FloatingActionButton floatingActionButton, String paging, VisitLastDateFragment visitLastDateFragment) {
        PopulateDbAsync task = new PopulateDbAsync(db, username, start_date, end_date, start_row, progressBar, floatingActionButton, paging, visitLastDateFragment);
        task.execute();
    }


    private static Time addUser(final AppDatabase db, Time time) {
        db.timeDao().insert(time);
        return time;
    }

    private static List<Time> populateWithTestData(AppDatabase db, String username, String start_date, String end_date, int start_row, ProgressBar progressBar, FloatingActionButton floatingActionButton, String paging) {


        String keyStart = start_date.replace("/", "");
        String keyEnd = end_date.replace("/", "");
        int start = Integer.parseInt(keyStart);
        int end = Integer.parseInt(keyEnd);

        if (paging.equals("paging"))
            return db.timeDao().getAllPaging(start, end, start_row);
//        else if (paging.equals("noPaging"))
//            return db.timeDao().getAll(start, end);
        else return null;


    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, List<Time>> {

        private final AppDatabase mDb;


        private String username, start_date, end_date, paging;
        private int start_row;
        private ProgressBar progressBar;
        private FloatingActionButton floatingActionButton;
        VisitLastDateFragment visitLastDateFragment;


        PopulateDbAsync(AppDatabase db, String username, String star_date, String end_date, int start_row, ProgressBar progressBar, FloatingActionButton floatingActionButton, String paging, VisitLastDateFragment visitLastDateFragment) {
            mDb = db;
            this.username = username;
            this.start_date = star_date;
            this.end_date = end_date;
            this.start_row = start_row;
            this.progressBar = progressBar;
            this.floatingActionButton = floatingActionButton;
            this.paging = paging;
            this.visitLastDateFragment = visitLastDateFragment;

        }

        @Override
        protected List<Time> doInBackground(final Void... params) {
            return populateWithTestData(mDb, username, start_date, end_date, start_row, progressBar, floatingActionButton, paging);
        }

        @Override
        protected void onPostExecute(List<Time> times) {
            super.onPostExecute(times);
            if (paging.equals("paging"))
                visitLastDateFragment.passListViewRoom(times);
//            else if(paging.equals("paging")){
//                visitLastDateFragment.passListViewMoreRoom(times);


        }

    }


}
