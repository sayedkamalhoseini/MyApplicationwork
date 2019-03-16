package com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VisitLastDate.VisitLastDateFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kamal on 12/24/2017.
 */

public class VisitEmployeeModel {
    FragmentActivity activity;
    private String user;
    VisitEmployeePresenter presenter;

    public VisitEmployeeModel(VisitEmployeePresenter presenter, FragmentActivity activity) {
        this.activity=activity;
        this.presenter=presenter;
    }


    public void visitEmployeeModel(String url, final String user, final ProgressBar progressbar) {
        this.user =user;
        Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray jsonArray=jsonObject.getJSONArray("employee");
                    List<VisitEmployee> list=new ArrayList<VisitEmployee>();
                    VisitEmployee visitEmployee;
                    for(int i=0;i<jsonArray.length();i++){
                        visitEmployee=new VisitEmployee();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String userNameEmployee=jsonObject1.getString("username_employee");
                        visitEmployee.setUserNameEmployee(userNameEmployee);
                        list.add(visitEmployee);
                    }
                    presenter.passListVisitEmployeePresenter(list);
                    progressbar.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(activity, activity.getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public Map onMapPost() {
                Map<String, String> Params = new HashMap<>();
                Params.put("userEmployer", user);
                Params.put("key_text_android", "ktaa");
                return Params;

            }
        });
    }

    public void goToVisitTime(){
        Intent intent = new Intent(activity, VisitLastDateFragment.class);
        intent.putExtra("user", user);
        intent.putExtra("kind","employee");
        activity.startActivity(intent);

    }

}
