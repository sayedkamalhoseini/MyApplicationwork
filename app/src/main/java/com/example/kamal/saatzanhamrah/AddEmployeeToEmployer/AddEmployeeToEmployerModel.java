package com.example.kamal.saatzanhamrah.AddEmployeeToEmployer;

import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.Share;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamal on 12/29/2017.
 */

public class AddEmployeeToEmployerModel {
    AddEmployeeToEmployerPresenter presenter;
    FragmentActivity activity;
    String userUpdate;

    public AddEmployeeToEmployerModel(AddEmployeeToEmployerPresenter presenter, FragmentActivity activity) {
        this.presenter = presenter;
        this.activity = activity;
        userUpdate = Share.loadPref(activity, "userKeyUpdate");

    }

    public void addEmployee(final String s, String addEmployeeUrl, final String user) {
        Share.getStringResponse(activity, Request.Method.POST, addEmployeeUrl, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                presenter.resultAddEmployeePresenter(result);
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public Map onMapPost() {
                Map<String, String> Params = new HashMap<>();
                Params.put("userEmployee", user);
                Params.put("userEmployer", s);
                Params.put("key_text_android", "ktaa");
                return Params;

            }
        });
    }

}
