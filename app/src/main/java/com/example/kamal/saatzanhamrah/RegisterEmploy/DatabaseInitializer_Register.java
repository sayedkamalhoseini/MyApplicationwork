package com.example.kamal.saatzanhamrah.RegisterEmploy;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.kamal.saatzanhamrah.RoomPackage.AppDatabase;
import com.example.kamal.saatzanhamrah.RoomPackage_Employe.Employe;


public class DatabaseInitializer_Register {


    private static final String TAG = DatabaseInitializer_Register.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, String username, String password, String update_username, String email, String kind) {
        PopulateDbAsync task = new PopulateDbAsync(db, username, password, update_username, email, kind);
        task.execute();
    }


    private static Employe addUser(final AppDatabase db, Employe employe) {
        db.employeeDao().insert(employe);
        return employe;
    }

    private static void populateWithTestData(AppDatabase db, String username, String password, String update_username, String email, String kind) {
        Employe employe = new Employe();
        employe.setUsername(username);
        employe.setUpdate_username(update_username);
        employe.setPassword(password);
        employe.setEmail(email);
        if (kind.equals("employee")) {
            employe.setKind("employee");
        }
        else if (kind.equals("employer")) {
            employe.setKind("employer");
        }

        addUser(db,employe);



//            List<Employe> userList = db.userDao().getAll();
//            Log.d(DatabaseInitializer_Register.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private String username, password, update_username, email, kind;


        PopulateDbAsync(AppDatabase db, String username, String password, String update_username, String email, String kind) {
            mDb = db;
            this.username = username;
            this.password = password;
            this.email = email;
            this.update_username = update_username;
            this.kind = kind;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb, username, password, update_username, email, kind);
            return null;
        }

    }


}
