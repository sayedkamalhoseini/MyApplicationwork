package com.example.kamal.saatzanhamrah.RoomPackage;

import android.app.Activity;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.example.kamal.saatzanhamrah.RoomPackage_Employe.Employe;
import com.example.kamal.saatzanhamrah.RoomPackage_Employe.EmployeeDao;
import com.example.kamal.saatzanhamrah.RoomPackage_Employe.Time;
import com.example.kamal.saatzanhamrah.RoomPackage_Employe.TimeDao;


@Database(entities = {Employe.class, Time.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract EmployeeDao employeeDao();
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
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}