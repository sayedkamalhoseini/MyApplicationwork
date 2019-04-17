package com.example.kamal.saatzanhamrah.MainPackage;

import android.app.DownloadManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer.VisitEmployee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainModel {

    private MainPresenter mainPresenter=new MainPresenter();
    private String my_buy;
    private String success;

    public void buyModel(final MainActivity mainActivity, String urlConfirm) {
        Share.getStringResponse(mainActivity, Request.Method.POST, urlConfirm, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("result1");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    success = jsonObject1.getString("success");

                    if (success.equals("find")) {
                        my_buy = jsonObject1.getString("key");
                        Share.saveSharePref(mainActivity, "my_key", my_buy);
                        mainPresenter.result(mainActivity,my_buy);
                    }
                    if (success.equals("no_connect")) {
                        Toast.makeText(mainActivity, mainActivity.getString(R.string.registerError), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(String error) {

            }

            @Override
            public Map onMapPost() {
                Map<String, String> params = new HashMap<>();
                params.put("key_text_android", "ktaa");
                return params;
            }
        });
    }
}
