package com.example.kamal.saatzanhamrah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import co.ronash.pushe.Pushe;
import com.example.kamal.saatzanhamrah.AddEmployeeToEmployer.AddEmployeeToEmployerFragment;
import com.example.kamal.saatzanhamrah.LoginEmploy.LoginActivity;
import com.example.kamal.saatzanhamrah.TimeEmploy.AutoDateFragment;
import com.example.kamal.saatzanhamrah.TimeEmploy.HandDateFragment;
import com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer.VisitEmployeeToEmployerFragment;
import com.example.kamal.saatzanhamrah.VisitEmployerToEmployee.VisitEmployerToEmployeeFragment;
import com.example.kamal.saatzanhamrah.VisitLastDate.VisitLastDateFragment;
import com.example.kamal.saatzanhamrah.util.IabHelper;
import com.example.kamal.saatzanhamrah.util.IabResult;
import com.example.kamal.saatzanhamrah.util.Inventory;
import com.example.kamal.saatzanhamrah.util.Purchase;

import ir.adad.client.Adad;
import ir.adad.client.Banner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PassData passData;
    private EnableData enableData;
    private Fragment autoDateFragment, visitLastDateFragment, addEmployeeToEmployerFragment,
            visitEmployeeToEmployerFragment, handDateFragment, visitEmployerToEmployee, aboutUsFragment;
    private String user, kind;
    private Toolbar toolbar;
    private ImageButton imageButton;
    private Banner banner;
    private ListView listView;
    private DrawerLayout drawerLayout;
    static final String TAG = "tag";


    // SKUs for our products: the premium upgrade (non-consumable)
    static final String SKU_PREMIUM ="2018saatzan";

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
        Pushe.initialize(this,true);
        Adad.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(com.example.kamal.saatzanhamrah.R.id.toolbar);
        banner = (Banner) findViewById(R.id.banner_ad_view);
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
                            toolbar.getMenu().findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_enable).setVisible(false);
                            Share.saveSharePref(MainActivity.this, "count", "1");
                            Share.saveSharePref(MainActivity.this, "mIsPremium", "true");
                            enableData = (EnableData) autoDateFragment;
                            enableData.sendEnable(mIsPremium);
                        }
                        else{
                            if (Share.loadPref(MainActivity.this, "count").equals("1")) {
                                toolbar.getMenu().findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_enable).setVisible(true);
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
        setSupportActionBar(toolbar);
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
        passData.sendData(user, kind);
        getSupportFragmentManager().beginTransaction().replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, autoDateFragment).commit();
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.example.kamal.saatzanhamrah.R.menu.menu_items, menu);
        if (Share.loadPref(MainActivity.this, "mIsPremium").equals("true")) {
            MenuItem item = menu.findItem(R.id.item_menuItems_enable);
            item.setVisible(false);
        }

        if (kind.equals("employee")) {
            MenuItem item = menu.findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_visitWorkEmployee);
            item.setVisible(false);
        } else if (kind.equals("employer")) {
            MenuItem item1 = menu.findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_addEmployeeToEmployer);
            item1.setVisible(false);
            MenuItem item2 = menu.findItem(com.example.kamal.saatzanhamrah.R.id.item_menuItems_visitEmployer);
            item2.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_enable:
                mHelper.launchPurchaseFlow(this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");
                break;
            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_visitDateMyWork:
                banner.setVisibility(View.GONE);
                visitLastDateFragment = new VisitLastDateFragment();
                passData = (PassData) visitLastDateFragment;
                passData.sendData(user, kind);
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, visitLastDateFragment).commit();
                break;
            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_addEmployeeToEmployer:
                banner.setVisibility(View.VISIBLE);
                addEmployeeToEmployerFragment = new AddEmployeeToEmployerFragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, addEmployeeToEmployerFragment).commit();
                passData = (PassData) addEmployeeToEmployerFragment;
                passData.sendData(user, kind);
                break;
            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_visitWorkEmployee:
                banner.setVisibility(View.GONE);
                visitEmployeeToEmployerFragment = new VisitEmployeeToEmployerFragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, visitEmployeeToEmployerFragment).commit();
                passData = (PassData) visitEmployeeToEmployerFragment;
                passData.sendData(user, kind);
                break;
            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_registerAutoTime:
                banner.setVisibility(View.VISIBLE);
                autoDateFragment = new AutoDateFragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, autoDateFragment).commit();
                passData = (PassData) autoDateFragment;
                passData.sendData(user, kind);
                break;
            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_registerHandTime:
                if (Share.loadPref(MainActivity.this, "start" + user).equals("true")) {
                    banner.setVisibility(View.VISIBLE);
                    handDateFragment = new HandDateFragment();
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, handDateFragment).commit();
                    passData = (PassData) handDateFragment;
                    passData.sendData(user, kind);
                    enableData = (EnableData) handDateFragment;
                    enableData.sendEnable(mIsPremium);
                    break;
                } else {
                    Toast.makeText(this,getString(R.string.messageErrorHandDate), Toast.LENGTH_LONG).show();
                    break;
                }

            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_visitEmployer:
                banner.setVisibility(View.VISIBLE);
                visitEmployerToEmployee = new VisitEmployerToEmployeeFragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, visitEmployerToEmployee).commit();
                passData = (PassData) visitEmployerToEmployee;
                passData.sendData(user, kind);
                break;
            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_exit:
                Share.saveSharePref(this, "userKey", "");
                Share.saveSharePref(this, "passKey", "");
                Share.saveSharePref(this, "kindKey", "");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case com.example.kamal.saatzanhamrah.R.id.item_menuItems_aboutUs:
                banner.setVisibility(View.VISIBLE);
                aboutUsFragment = new AboutUsFragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(com.example.kamal.saatzanhamrah.R.id.frameLayout_main_containerFragment, aboutUsFragment).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        mHelper.launchPurchaseFlow(this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, "payload-string");
    }

    public interface PassData {
        public void sendData(String user, String kind);
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

    public interface EnableData {
        public void sendEnable(boolean mIsPremium);
    }


}
