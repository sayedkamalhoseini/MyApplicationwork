package com.example.kamal.saatzanhamrah.LoginEmploy;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainActivity;
import com.example.kamal.saatzanhamrah.RoomPackage.AppDatabase;
import com.example.kamal.saatzanhamrah.RegisterEmploy.DatabaseInitializer_Register;
import com.example.kamal.saatzanhamrah.RoomPackage_Employe.Employe;
import com.example.kamal.saatzanhamrah.Share;

public class DatabaseInitializer_Login {

    private Employe employe;
    private Activity activity;

    private static final String TAG = DatabaseInitializer_Register.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, String update_username, String password, Activity activity, ProgressBar progressBar, Button btnLogin, String kind) {
        PopulateDbAsync task = new PopulateDbAsync(db, update_username, password, activity, progressBar, btnLogin, kind);
        task.execute();
    }


    private static Employe populateWithTestData(AppDatabase db, String update_username, String password,String kind) {
        Employe employe = new Employe();


        employe = db.employeeDao().findEmployee(update_username, password,kind);

        if (employe != null)
            return employe;
        else
            return null;
    }

    private static class PopulateDbAsync extends AsyncTask<String, Void, Employe> {

        private final AppDatabase mDb;
        private String password, update_username, userNameMain, kind;
        private Activity activity;
        private ProgressBar progressBar;
        private Button btnLogin;


        PopulateDbAsync(AppDatabase db, String update_username, String password, Activity activity, ProgressBar progressBar, Button btnLogin, String kind) {
            mDb = db;
            this.password = password;
            this.update_username = update_username;
            this.activity = activity;
            this.progressBar = progressBar;
            this.btnLogin = btnLogin;
            this.kind = kind;
        }

        @Override
        protected Employe doInBackground(String... strings) {
            return  populateWithTestData(mDb,update_username,password,kind);
        }


        @Override
        protected void onPostExecute(Employe employe) {
            super.onPostExecute(employe);

            userNameMain = mDb.employeeDao().findUsername(update_username, password);
            if (employe != null) {
                Share.saveSharePref(activity, "userKey", userNameMain);
                Share.saveSharePref(activity, "userKeyUpdate", update_username);
                Share.saveSharePref(activity, "passKey", password);
                Share.saveSharePref(activity, "kindKey", kind);
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("user", userNameMain);
                intent.putExtra("kind", kind);
                activity.startActivity(intent);
                activity.finish();
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
            }
            else {
                Toast.makeText(activity, "لطفا با متصل شدن به اینترنت ابتدا ثبت نام کنید.", Toast.LENGTH_LONG).show();

            }
        }

    }


}
