package com.example.kamal.saatzanhamrah.RoomPackage;

import android.app.Activity;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.kamal.saatzanhamrah.RoomPackage_Employe.Employe;
import com.example.kamal.saatzanhamrah.RoomPackage_Employe.EmployeeDao;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;
import com.example.kamal.saatzanhamrah.VisitLastDate.TimeDao;


@Database(entities = {LastTime.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;


    public abstract TimeDao timeDao();

//    static final Migration Migration_1_2=
//            new Migration(1,2){
//                @Override
//                public void migrate(@NonNull SupportSQLiteDatabase database) {
//                database.execSQL("Create TABLE time (id INTEGER,username TEXT,token INTEGER,token_delete TEXT,start_date TEXT,start_time TEXT,start_date_miladi TEXT,end_date TEXT,end_time TEXT,worktime TEXT,explains TEXT,confirm_employer TEXT,PRIMARY KEY(id))");
//                }
//            };


    public static AppDatabase getAppDatabase(Activity context) {

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "/storage/emulated/0/you/saatZan_database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}