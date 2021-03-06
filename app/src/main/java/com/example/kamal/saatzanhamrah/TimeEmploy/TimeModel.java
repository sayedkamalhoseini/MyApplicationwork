package com.example.kamal.saatzanhamrah.TimeEmploy;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kamal on 12/11/2017.
 */

public class TimeModel {
    FragmentActivity activity;
    TimePresenter presenter;
    List<LastTime> lastTimeList;


    public TimeModel(TimePresenter presenter, FragmentActivity activity) {
        this.presenter = presenter;
        this.activity = activity;
    }

    public void startTimeRegister(String timeEmployeeUrl, final String user, final String kind, final ProgressBar progressBar, final Button buttonStart, final TextView textLastStartDate, final TextView textLastStartTime) {

        Share.getStringResponse(activity, Request.Method.POST, timeEmployeeUrl, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String success = jsonObject.getString("success");
                    if (success.equals("done")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("startTime");
                        lastTimeList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String startDate = jsonObject1.getString("start_date");
                            String startTime = jsonObject1.getString("start_time");
                            textLastStartDate.setText(startDate);
                            textLastStartTime.setText(startTime);
                            Share.saveSharePref(activity, "startLastDate" + user, startDate);
                            Share.saveSharePref(activity, "startLastTime" + user, startTime);
                        }
                        presenter.messageStart(success);
                        progressBar.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public void onError(String error) {
                presenter.messageStart(error);
                progressBar.setVisibility(View.GONE);
                buttonStart.setEnabled(true);
            }

            @Override
            public Map onMapPost() {
                Map<String, String> timeParams = new HashMap<String, String>();
                timeParams.put("user", user);
                timeParams.put("kind", kind);
                timeParams.put("key_text_android", "ktaa");
                return timeParams;
            }
        });
    }

    public void endTimeRegister(String timeEmployeeUrl, final String user, final String kind, final String text, final ProgressBar progressBar, final Button buttonEnd) {

        Share.getStringResponse(activity, Request.Method.POST, timeEmployeeUrl, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String success = jsonObject.getString("success");
                    if (success.equals("done")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("endTime");
                        lastTimeList = new ArrayList<>();
                        LastTime lastTime;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            lastTime = new LastTime();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String endLastDate = jsonObject1.getString("end_date");
                            String endLastTime = jsonObject1.getString("end_time");
                            String _workTime = jsonObject1.getString("work_time");
                            if (!_workTime.equals("null")) {
                                int workTime = Integer.parseInt(_workTime);
                                _workTime = Share.changeTime(workTime);
                            }
                            lastTime.setStartWorkDate(Share.loadPref(activity, "startLastDate" + user));
                            lastTime.setStartWorkTime(Share.loadPref(activity, "startLastTime" + user));
                            lastTime.setEndWorkDate(endLastDate);
                            lastTime.setEndWorkTime(endLastTime);
                            lastTime.setWorkTime(_workTime);
                            lastTimeList.add(lastTime);
                        }
                        presenter.messageEnd(success);
                        presenter.passListPresenter(lastTimeList);
                        progressBar.setVisibility(View.GONE);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(String error) {
                presenter.messageEnd(error);
                progressBar.setVisibility(View.INVISIBLE);
                buttonEnd.setEnabled(true);
            }

            @Override
            public Map onMapPost() {
                Map<String, String> timeParams = new HashMap<String, String>();
                timeParams.put("user", user);
                timeParams.put("kind", kind);
                timeParams.put("explains", text);
                timeParams.put("key_text_android", "ktaa");
                return timeParams;
            }
        });
    }


    public void handDate(String addEmployeeUrl, final Map<String, String> params1, final ProgressBar progressbar, final Button buttonRegisterHandDate) {
        try {

            if (Integer.parseInt(params1.get("dateEnd").replace("/", "")) > Integer.parseInt(params1.get("dateStart").replace("/", ""))) {
                Share.getStringResponse(activity, Request.Method.POST, addEmployeeUrl, null, new Share.StringVolleyCallBack() {
                    @Override
                    public void onSuccessResponse(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String success = jsonObject.getString("success");
                            String workTime = jsonObject.getString("workTime");
                            presenter.resultHandDate(success, workTime);
                            progressbar.setVisibility(View.GONE);
                            buttonRegisterHandDate.setEnabled(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_LONG).show();
                        progressbar.setVisibility(View.GONE);
                        buttonRegisterHandDate.setEnabled(true);
                    }

                    @Override
                    public Map onMapPost() {
                        Map<String, String> Params = new HashMap<>();
                        Params.put("user", params1.get("user"));
                        Params.put("kind", params1.get("kind"));
                        Params.put("token", params1.get("dateStart").replace("/", ""));
                        Params.put("token_delete", params1.get("user") + params1.get("dateStart").replace("/", "") + params1.get("timeStart").replace(":", ""));
                        Params.put("date_start", params1.get("dateStart"));
                        Params.put("time_start", params1.get("timeStart"));
                        Params.put("date_end", params1.get("dateEnd"));
                        Params.put("time_end", params1.get("timeEnd"));
                        Params.put("start_date_miladi", params1.get("miladiStart"));
                        Params.put("end_date_miladi", params1.get("miladiEnd"));
                        Params.put("explains", params1.get("explains"));
                        Params.put("key_text_android", "ktaa");
                        return Params;

                    }
                });
            } else if (Integer.parseInt(params1.get("dateEnd").replace("/", "")) == Integer.parseInt(params1.get("dateStart").replace("/", ""))) {
                if (Integer.parseInt(params1.get("timeEnd").replace(":", "")) >= Integer.parseInt(params1.get("timeStart").replace(":", ""))) {
                    Share.getStringResponse(activity, Request.Method.POST, addEmployeeUrl, null, new Share.StringVolleyCallBack() {
                        @Override
                        public void onSuccessResponse(String result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String success = jsonObject.getString("success");
                                String workTime = jsonObject.getString("workTime");
                                presenter.resultHandDate(success, workTime);
                                progressbar.setVisibility(View.GONE);
                                buttonRegisterHandDate.setEnabled(true);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.GONE);
                            buttonRegisterHandDate.setEnabled(true);
                        }

                        @Override
                        public Map onMapPost() {
                            Map<String, String> Params = new HashMap<>();
                            Params.put("user", params1.get("user"));
                            Params.put("kind", params1.get("kind"));
                            Params.put("token", params1.get("dateStart").replace("/", ""));
                            Params.put("token_delete", params1.get("user") + params1.get("dateStart").replace("/", "") + params1.get("timeStart").replace(":", ""));
                            Params.put("date_start", params1.get("dateStart"));
                            Params.put("time_start", params1.get("timeStart"));
                            Params.put("date_end", params1.get("dateEnd"));
                            Params.put("time_end", params1.get("timeEnd"));
                            Params.put("start_date_miladi", params1.get("miladiStart"));
                            Params.put("end_date_miladi", params1.get("miladiEnd"));
                            Params.put("explains", params1.get("explains"));
                            Params.put("key_text_android", "ktaa");
                            return Params;

                        }
                    });

                } else {
                    Toast.makeText(activity, R.string.endLargerStart, Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                    buttonRegisterHandDate.setEnabled(true);
                    return;
                }

            } else {
                Toast.makeText(activity, R.string.endLargerStart, Toast.LENGTH_LONG).show();
                progressbar.setVisibility(View.GONE);
                buttonRegisterHandDate.setEnabled(true);
                return;
            }
        } catch (Exception e) {
            Toast.makeText(activity, R.string.fill_field, Toast.LENGTH_LONG).show();
            progressbar.setVisibility(View.GONE);
            buttonRegisterHandDate.setEnabled(true);
        }
    }

    public void explain_problem(String problemUrl) {
        Share.getStringResponse(activity, Request.Method.POST, problemUrl, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                presenter.resultProblem(result);
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public Map onMapPost() {
                return null;
            }
        });
    }

}

