package com.example.kamal.saatzanhamrah.DeletePackage;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kamal on 1/16/2018.
 */

public class DeleteModel {
    DeletePresenter presenter;
    Activity activity;
    String _workTimeHour, _workTimeMinute, _workTimeSecond;

    public DeleteModel(DeletePresenter deletePresenter, FragmentActivity activity) {
        this.presenter=deletePresenter;
        this.activity=activity;
    }

    public void getListDeleteModel(String url, String dateDelete, final String user, final String kind) {
        final String keyStart = dateDelete.replace("/", "");
        Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("lastVisit");
                    List<LastTime> lastTimeList = new ArrayList<>();
                    LastTime lastTime;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        lastTime = new LastTime();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String startLastDate = jsonObject1.getString("start_date");
                        String startLastTime = jsonObject1.getString("start_time");
                        String endLastDate = jsonObject1.getString("end_date");
                        String endLastTime = jsonObject1.getString("end_time");
                        String _workTime = jsonObject1.getString("worktime");
                        if (!_workTime.equals("null")) {
                            int workTime = Integer.parseInt(_workTime);
                            _workTimeHour = workTime / 3600 + "";
                            _workTimeMinute = workTime % 3600 / 60 + "";
                            _workTimeSecond = workTime % 3600 % 60 + "";
                            _workTime = _workTimeHour +":"+ _workTimeMinute +":"+ _workTimeSecond;
                        }
                        lastTime.setStartWorkDate(startLastDate);
                        lastTime.setStartWorkTime(startLastTime);
                        lastTime.setEndWorkDate(endLastDate);
                        lastTime.setEndWorkTime(endLastTime);
                        lastTime.setStartWorkDate(startLastDate);
                        lastTime.setStartWorkTime(startLastTime);
                        lastTime.setEndWorkDate(endLastDate);
                        lastTime.setEndWorkTime(endLastTime);
                        lastTime.setWorkTime(_workTime);
                        lastTimeList.add(lastTime);
                        presenter.passListPresenter(lastTimeList);

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
                Map<String, String> Params1 = new HashMap<>();
                Params1.put("user", user);
                Params1.put("keyStart", keyStart);
                Params1.put("kind", kind);
                Params1.put("key_text_android", "ktaa");
                return Params1;

            }
        });
    }
}
