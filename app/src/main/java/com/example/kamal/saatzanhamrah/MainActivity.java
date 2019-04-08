package com.example.kamal.saatzanhamrah;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.ronash.pushe.Pushe;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.AddEmployeeToEmployer.AddEmployeeToEmployerFragment;
import com.example.kamal.saatzanhamrah.LoginEmploy.LoginActivity;
import com.example.kamal.saatzanhamrah.TimeEmploy.AutoDateFragment;
import com.example.kamal.saatzanhamrah.TimeEmploy.HandDateFragment;
import com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer.VisitEmployee;
import com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer.VisitEmployeeToEmployerFragment;
import com.example.kamal.saatzanhamrah.VisitEmployerToEmployee.VisitEmployerToEmployeeFragment;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTimeAdapter;
import com.example.kamal.saatzanhamrah.VisitLastDate.VisitLastDateFragment;
import com.example.kamal.saatzanhamrah.util.IabHelper;
import com.example.kamal.saatzanhamrah.util.IabResult;
import com.example.kamal.saatzanhamrah.util.Inventory;
import com.example.kamal.saatzanhamrah.util.Purchase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private PassData passData;
    private EnableData enableData;
    private Fragment autoDateFragment;
    private EditText explain;
    private String user, kind;
    private Toolbar toolbar;
    private ImageButton imageButton;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Fragment fragment;
    private Runnable runnable;
    private TextView userName, kindText;
    private PopupWindow myPopUp;
    private RelativeLayout relativeLayout;
    private EditText user_update, email_update;
    private String urlGetEmail = "http://kamalroid.ir/get_email.php";
    private String urlGetUpdate = "http://kamalroid.ir/update_user_name.php";
    private ProgressBar progressBar;
    private Button buttonSettings, buttonExitUpdate;
    private String userUpdate;

    static final String TAG = "tag";


    // SKUs for our products: the premium upgrade (non-consumable)
    static final String SKU_PREMIUM = "2018saatzan";

    // Does the user have the premium upgrade?
    boolean mIsPremium = true;
    boolean statusEnable = false;

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 1372;

    // The helper object
    IabHelper mHelper;
    ProgressDialog pd;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener;


    @Override
    protected void onStop() {
        super.onStop();

        if (MySingleton.getInstance(this).getRequestQueue() != null) {
            MySingleton.getInstance(this).getRequestQueue().cancelAll(Share.TAG);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pushe.initialize(this, true);
        setContentView(R.layout.navigation_drawer);

        toolbar = (Toolbar) findViewById(com.example.kamal.saatzanhamrah.R.id.toolbar);
        explain = findViewById(R.id.editText_time_explain);
        relativeLayout = findViewById(R.id.main_layout);
        userUpdate = Share.loadPref(MainActivity.this, "userKeyUpdate");
        setSupportActionBar(toolbar);

        if (Share.loadPref(this, "count").equals("")) {
            Share.saveSharePref(this, "count", "1");
        }
        String base64EncodedPublicKey = getResources().getString(R.string.codeMaiket);
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        try {
            mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
                public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                    Log.d(TAG, "Query inventory finished.");
                    if (result.isFailure()) {
                        Log.d(TAG, "Failed to query inventory: " + result);
                        return;
                    } else {
                        Log.d(TAG, "Query inventory was successful.");
                        mIsPremium = inventory.hasPurchase(SKU_PREMIUM);
                        if (mIsPremium) {
                            navigationView.getMenu().findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_enable).setVisible(false);
                            Share.saveSharePref(MainActivity.this, "count", "1");
                            Share.saveSharePref(MainActivity.this, "mIsPremium", "true");
                            enableData = (EnableData) autoDateFragment;
                            enableData.sendEnable(mIsPremium);
                        } else {
                            if (Share.loadPref(MainActivity.this, "count").equals("1")) {
                                navigationView.getMenu().findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_enable).setVisible(true);
                                Share.saveSharePref(MainActivity.this, "count", "1");
                                Share.saveSharePref(MainActivity.this, "mIsPremium", "false");
                                enableData = (EnableData) autoDateFragment;
                                enableData.sendEnable(mIsPremium);
                            }
                        }

                        Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
                    }

                    Log.d(TAG, "Initial inventory query finished; enabling main UI.");
                }


            };
        } catch (Exception e) {
            Toast.makeText(this, "خارج شدید.", Toast.LENGTH_SHORT).show();
        }

        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                if (result.isFailure()) {
                    Log.d(TAG, "Error purchasing: " + result);
                    return;
                } else if (purchase.getSku().equals(SKU_PREMIUM)) {
                    // give user access to premium content and update the UI
                    Toast.makeText(MainActivity.this, "خرید موفق", Toast.LENGTH_LONG).show();
                    Share.saveSharePref(MainActivity.this, "mIsPremium", "true");
                    mIsPremium = true;
                    enableData = (EnableData) autoDateFragment;
                    enableData.sendEnable(mIsPremium);
                    toolbar.getMenu().findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_enable).setVisible(false);
                    Share.saveSharePref(MainActivity.this, "count", "1");
                    Share.saveSharePref(MainActivity.this, "mIsPremium", "true");
                }
            }
        };


        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                }
                // Hooray, IAB is fully set up!
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        kind = intent.getStringExtra("kind");


        if (Share.loadPref(this, "start" + user).equals(false)) {
            toolbar.setBackgroundColor(ContextCompat.getColor(this, com.example.kamal.saatzanhamrah.R.color.yellow));
        } else if (Share.loadPref(this, "end" + user).equals(false)) {
            toolbar.setBackgroundColor(ContextCompat.getColor(this, com.example.kamal.saatzanhamrah.R.color.green_500));
        }
        autoDateFragment = new AutoDateFragment();
        passData = (PassData) autoDateFragment;
        passData.sendData(user, kind, userUpdate);
        getSupportFragmentManager().beginTransaction().replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, autoDateFragment).commit();
        setupNavigationDrawer();
    }


    @Override
    public void onClick(View v) {
        mHelper.launchPurchaseFlow(this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {

        runnable = new Runnable() {
            @Override
            public void run() {

                fragment = null;
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.item_menuItems_enable:
                        mHelper.launchPurchaseFlow(MainActivity.this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");
                        break;
                    case R.id.item_menuItems_visitDateMyWork:
                        fragment = new VisitLastDateFragment();
                        passData = (PassData) fragment;
                        passData.sendData(user, kind, userUpdate);
                        break;
                    case R.id.item_menuItems_addEmployeeToEmployer:
                        fragment = new AddEmployeeToEmployerFragment();
                        passData = (PassData) fragment;
                        passData.sendData(user, kind, userUpdate);
                        break;
                    case R.id.item_menuItems_visitWorkEmployee:
                        fragment = new VisitEmployeeToEmployerFragment();
                        passData = (PassData) fragment;
                        passData.sendData(user, kind, userUpdate);
                        break;
                    case R.id.item_menuItems_registerAutoTime:
                        fragment = new AutoDateFragment();
                        passData = (PassData) fragment;
                        passData.sendData(user, kind, userUpdate);
                        break;

                    case R.id.item_menuItems_settings:

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                        View custumView = inflater.inflate(R.layout.pop_up_settings, null);
                        builder.setView(custumView);
                        builder.setCancelable(false);

                        AlertDialog alert = builder.create();
                        alert.show();


                        user_update = custumView.findViewById(R.id.editText_update_user);
                        email_update = custumView.findViewById(R.id.editText_update_email);
                        progressBar = custumView.findViewById(R.id.progressBar_settings);
                        buttonSettings = custumView.findViewById(R.id.Button_settings);
                        buttonExitUpdate = custumView.findViewById(R.id.button_exit_update);

                        getInfo(user_update, email_update, progressBar, buttonSettings, alert);

                        break;
                    case R.id.item_menuItems_registerHandTime:

                        if (Share.loadPref(MainActivity.this, "start" + user).equals("true")) {
                            fragment = new HandDateFragment();
                            passData = (PassData) fragment;
                            passData.sendData(user, kind, userUpdate);
                            enableData = (EnableData) fragment;
                            enableData.sendEnable(mIsPremium);
                            break;
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.messageErrorHandDate), Toast.LENGTH_LONG).show();
                            break;
                        }

                    case R.id.item_menuItems_visitEmployer:
                        fragment = new VisitEmployerToEmployeeFragment();
                        passData = (PassData) fragment;
                        passData.sendData(user, kind, userUpdate);
                        break;
                    case R.id.item_menuItems_exit:
                        Share.saveSharePref(MainActivity.this, "userKeyUpdate", "");
                        Share.saveSharePref(MainActivity.this, "userKey", "");
                        Share.saveSharePref(MainActivity.this, "passKey", "");
                        Share.saveSharePref(MainActivity.this, "kindKey", "");
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.item_menuItems_aboutUs:
                        fragment = new AboutUsFragment();
                        break;
                }

                if (fragment != null) {

                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout_main_containerFragment, fragment).commit();
                }
            }
        };

        drawerLayout.closeDrawer(Gravity.START);


        return true;
    }

    public interface PassData {
        public void sendData(String user, String kind, String userUpdate);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }


    @Override
    public void onDestroy() {
        //از سرویس در زمان اتمام عمر activity قطع شوید
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    private void setupNavigationDrawer() {
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.navigation_layout);
        kindText = navigationView.getHeaderView(0).findViewById(R.id.kind);
        userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
        if (kind.equals("employee"))
            kindText.setText("کارمند/کارگر");
        else if (kind.equals("employer"))
            kindText.setText("کارفرما");

        userName.setText(userUpdate);


        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();

                if (runnable != null) {
                    new Handler().post(runnable);
                    runnable = null;
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                          }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });


        Menu menuNav = navigationView.getMenu();
        if (Share.loadPref(MainActivity.this, "mIsPremium").equals("true")) {
            MenuItem item = menuNav.findItem(R.id.item_menuItems_enable);
            item.setVisible(false);
        }


        if (kind.equals("employee")) {
            MenuItem item = menuNav.findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_visitWorkEmployee);
            item.setVisible(false);
        } else if (kind.equals("employer")) {
            MenuItem item1 = menuNav.findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_addEmployeeToEmployer);
            item1.setVisible(false);
            MenuItem item2 = menuNav.findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_visitEmployer);
            item2.setVisible(false);
        }

    }

    public interface EnableData {
        public void sendEnable(boolean mIsPremium);
    }

    public void getInfo(final EditText user_update, final EditText email_update, final ProgressBar progressBar, final Button buttonSettings, final AlertDialog alert) {
        progressBar.setVisibility(View.VISIBLE);

        Share.getStringResponse(this, Request.Method.POST, urlGetEmail, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("result1");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String userNameInfo = jsonObject1.getString("username");
                        String emailInfo = jsonObject1.getString("email");
                        if (emailInfo.equals("null")) {
                            email_update.setHint("");
                        } else {
                            email_update.setText(emailInfo);
                        }
                        user_update.setText(userNameInfo);

                        progressBar.setVisibility(View.GONE);


                    }


                } catch (JSONException e) {
                    List<VisitEmployee> list = new ArrayList<VisitEmployee>();
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public Map onMapPost() {
                Map<String, String> Params = new HashMap<>();
                Params.put("user", user);
                Params.put("kind", kind);
                Params.put("key_text_android", "ktaa");
                return Params;

            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String user_update1 = user_update.getText().toString().trim();
                final String email_update1 = email_update.getText().toString().trim();
                if (email_update1.equals("")) {
                    Toast.makeText(MainActivity.this, "ایمیل را وارد کنید.", Toast.LENGTH_SHORT).show();
                } else {

                    Share.getStringResponse(MainActivity.this, Request.Method.POST, urlGetUpdate, null, new Share.StringVolleyCallBack() {
                        @Override
                        public void onSuccessResponse(String result) {
                            if (result.equals("done")) {
                                Share.saveSharePref(MainActivity.this, "userKeyUpdate", user_update1);
//                            user=user_update1;
                                userName.setText(user_update1);
                                Toast.makeText(MainActivity.this, "ویرایش شد.", Toast.LENGTH_LONG).show();

                                progressBar.setVisibility(View.GONE);
                            } else if (result.equals("this user there is")) {
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.repeatUser), Toast.LENGTH_LONG).show();

                            } else if (result.equals("noDone")) {
                                Toast.makeText(MainActivity.this, "تغییری ایجاد نشد.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                            }
                            alert.cancel();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public Map onMapPost() {
                            Map<String, String> Params = new HashMap<>();
                            Params.put("user", user);
                            Params.put("kind", kind);
                            Params.put("update_user", user_update1);
                            Params.put("update_email", email_update1);
                            Params.put("key_text_android", "ktaa");
                            return Params;

                        }
                    });

                }
            }
        });


        buttonExitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }
}
