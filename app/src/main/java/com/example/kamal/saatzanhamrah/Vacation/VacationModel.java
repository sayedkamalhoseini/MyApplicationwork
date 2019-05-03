package com.example.kamal.saatzanhamrah.Vacation;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kamal on 12/11/2017.
 */

public class VacationModel {
    FragmentActivity activity;
    VacationPresenter presenter;
    List<LastTime> lastTimeList;


    public VacationModel(VacationPresenter presenter, FragmentActivity activity) {
        this.presenter = presenter;
        this.activity = activity;
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


}

