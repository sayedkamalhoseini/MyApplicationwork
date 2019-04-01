package com.example.kamal.saatzanhamrah.VisitEmployerToEmployee;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

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

public class VisitEmployerModel {
    FragmentActivity activity;
    private String user;
    VisitEmployerPresenter presenter;
    String userUpdate;

    public VisitEmployerModel(VisitEmployerPresenter presenter, FragmentActivity activity) {
        this.activity=activity;
        this.presenter=presenter;
        userUpdate=Share.loadPref(activity,"userKeyUpdate");
    }


    public void visitEmployerModel(String url, final String user, final ProgressBar progressbar) {
        this.user =user;
        Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray jsonArray=jsonObject.getJSONArray("employer");
                    List<VisitEmployer> list=new ArrayList<VisitEmployer>();
                    VisitEmployer visitEmployer;
                    for(int i=0;i<jsonArray.length();i++){
                        visitEmployer=new VisitEmployer();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String userNameEmployer=jsonObject1.getString("update_employer");
                        visitEmployer.setUserNameEmployer(userNameEmployer);
                        list.add(visitEmployer);
                    }
                    presenter.passListVisitEmployerPresenter(list);
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
                Params.put("userEmployee", userUpdate);
                Params.put("key_text_android", "ktaa");
                return Params;

            }
        });
    }


}
