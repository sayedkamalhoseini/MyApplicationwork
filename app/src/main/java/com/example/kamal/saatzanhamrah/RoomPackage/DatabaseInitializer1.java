package com.example.kamal.saatzanhamrah.RoomPackage;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.kamal.saatzanhamrah.LoginEmploy.LoginActivity;
import com.example.kamal.saatzanhamrah.MainActivity;
import com.example.kamal.saatzanhamrah.Share;

public class DatabaseInitializer1 {

    private Employee employee;
    private Activity activity;

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, String update_username, String password, Activity activity, ProgressBar progressBar, Button btnLogin) {
        PopulateDbAsync task = new PopulateDbAsync(db,update_username,password,activity,progressBar,btnLogin);
        task.execute();
    }

//    public static void populateSync(@NonNull final AppDatabase db,String username,String password,String update_username,String email) {
//        populateWithTestData(db,username,password,update_username,email);
//    }

    private static Employee populateWithTestData(AppDatabase db, String update_username, String password) {
        Employee employee = new Employee();


//        addUser(db, employee);

            employee = db.employeeDao().findEmployee(update_username,password);

            if (employee!=null)
                return employee;
            else
                return null;
//            Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<String, Void, Employee> {

        private final AppDatabase mDb;
        private String password,update_username,userNameMain,kind;
        private Activity activity;
        private ProgressBar progressBar;
        private Button btnLogin;


        PopulateDbAsync(AppDatabase db, String update_username, String password, Activity activity, ProgressBar progressBar, Button btnLogin) {
            mDb = db;
            this.password=password;
            this.update_username=update_username;
            this.activity=activity;
            this.progressBar=progressBar;
            this.btnLogin=btnLogin;
        }

        @Override
        protected void onPostExecute(Employee employee) {
            super.onPostExecute(employee);

            userNameMain=mDb.employeeDao().findUsername(update_username,password);
            kind="employee";
            if(employee!=null){
                Share.saveSharePref(activity, "userKey", userNameMain);
                Share.saveSharePref(activity, "userKeyUpdate", update_username);
                Share.saveSharePref(activity, "passKey", password);
                Share.saveSharePref(activity, "kindKey", kind);
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("user", userNameMain);
                intent.putExtra("kind",kind );
                activity.startActivity(intent);
                activity.finish();
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
            }
        }

        @Override
        protected Employee doInBackground(String... strings) {
            return  populateWithTestData(mDb,update_username,password);
        }
    }


}
