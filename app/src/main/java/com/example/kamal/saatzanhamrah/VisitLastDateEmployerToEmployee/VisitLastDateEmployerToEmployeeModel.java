package com.example.kamal.saatzanhamrah.VisitLastDateEmployerToEmployee;

import android.app.Activity;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.kamal.saatzanhamrah.R.string.workTime;

/**
 * Created by kamal on 12/18/2017.
 */

public class VisitLastDateEmployerToEmployeeModel {
    Activity activity;
    VisitLastDateEmployerToEmployeePresenter presenter;
    Document doc;
    FileOutputStream fOut;
    List<LastTime> lastTimeList;
    String _workTimeHour, _workTimeMinute, _workTimeSecond, user, kind, sumTime;


    public VisitLastDateEmployerToEmployeeModel(VisitLastDateEmployerToEmployeePresenter visitLastDatePresenter, FragmentActivity activity) {
        this.activity = activity;
        presenter = visitLastDatePresenter;
    }

    public void getLastDateModel(final String url, final String start, final String end, final String user, final String kind, final int start_row, final ProgressBar progressbar, final FloatingActionButton floatingActionButton) {
        if (start.equals("") || end.equals("")) {
            Toast.makeText(activity, "لطفا زمان شروع و پایان را وارد کنید.", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        final String keyStart = start.replace("/", "");
        final String keyEnd = end.replace("/", "");
        if (Integer.parseInt(keyStart) > Integer.parseInt(keyEnd)) {
            Toast.makeText(activity, R.string.endLargerStart, Toast.LENGTH_SHORT).show();
        } else {
            this.user = user;
            this.kind = kind;
            Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
                @Override
                public void onSuccessResponse(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("lastVisit");
                        lastTimeList = new ArrayList<>();
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
                                _workTime = _workTimeHour + ":" + _workTimeMinute + ":" + _workTimeSecond;
                            }
                            lastTime.setStartWorkDate(startLastDate);
                            lastTime.setStartWorkTime(startLastTime);
                            lastTime.setEndWorkDate(endLastDate);
                            lastTime.setEndWorkTime(endLastTime);
                            lastTime.setWorkTime(_workTime);
                            lastTimeList.add(lastTime);
                        }
                        presenter.passListPresenter(lastTimeList);
                        floatingActionButton.setVisibility(View.GONE);
                        progressbar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_SHORT).show();
                    floatingActionButton.setVisibility(View.GONE);
                    progressbar.setVisibility(View.GONE);


                }

                @Override
                public Map onMapPost() {
                    Map<String, String> Params1 = new HashMap<>();
                    Params1.put("user", user);
                    Params1.put("keyStart", keyStart);
                    Params1.put("keyEnd", keyEnd);
                    Params1.put("kind", kind);
                    Params1.put("start_row", String.valueOf(start_row));
                    Params1.put("paging", "yes");
                    Params1.put("key_text_android", "ktaa");
                    return Params1;
                }
            });
        }

    }

    public void getLastDateModelMore(final String url, final String start, final String end, final int start_row, final ProgressBar progressbar, final FloatingActionButton floatingActionButton) {
        final String keyStart = start.replace("/", "");
        final String keyEnd = end.replace("/", "");
        Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("lastVisit");
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
                            _workTime = _workTimeHour + ":" + _workTimeMinute + ":" + _workTimeSecond;
                        }
                        lastTime.setStartWorkDate(startLastDate);
                        lastTime.setStartWorkTime(startLastTime);
                        lastTime.setEndWorkDate(endLastDate);
                        lastTime.setEndWorkTime(endLastTime);
                        lastTime.setWorkTime(_workTime);
                        lastTimeList.add(lastTime);
                    }
                    presenter.passListPresenterMore(lastTimeList);
                    floatingActionButton.setVisibility(View.GONE);
                    progressbar.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_SHORT).show();
                floatingActionButton.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public Map onMapPost() {
                Map<String, String> Params1 = new HashMap<>();
                Params1.put("user", user);
                Params1.put("keyStart", keyStart);
                Params1.put("keyEnd", keyEnd);
                Params1.put("kind", kind);
                Params1.put("start_row", String.valueOf(start_row));
                Params1.put("paging", "yes");
                Params1.put("key_text_android", "ktaa");
                return Params1;
            }
        });

    }

    public void buildPdf(final String url, final String start, final String end, final String user, final String kind, final ProgressBar progressbar, final TextView textSum) {
        if (start.equals("") || end.equals("")) {
            Toast.makeText(activity, R.string.enterStartEnd, Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        final String keyStart = start.replace("/", "");
        final String keyEnd = end.replace("/", "");
        if (Integer.parseInt(keyStart) > Integer.parseInt(keyEnd)) {
            Toast.makeText(activity, R.string.endLargerStart, Toast.LENGTH_SHORT).show();
        } else {
            this.user = user;
            this.kind = kind;
            Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
                @Override
                public void onSuccessResponse(String result) {
                    try {
                        doc = new Document();
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        File dir = new File(path);
                        if (!dir.exists())
                            dir.mkdirs();

                        File file = new File(dir, user+"_time"+".pdf");
                        fOut = new FileOutputStream(file);
                        PdfWriter.getInstance(doc, fOut);
                        doc.open();

                    } catch (DocumentException de) {
                        Log.e("PDFCreator", "DocumentException:" + de);
                    } catch (IOException e) {
                        Log.e("PDFCreator", "ioException:" + e);
                    }
                    try {
                        Share.createandDisplayPdf("","","", "","", doc);
                        Share.createandDisplayPdf(end,"تا تاریخ", start,"از تاریخ", "کارکرد کاربر"+user, doc);
                        Share.createandDisplayPdf("","", "", "","", doc);
                        JSONObject jsonObject = new JSONObject(result);
                        String _sum=jsonObject.getString("sum");
                        JSONArray jsonArray = jsonObject.getJSONArray("lastVisit");
                        lastTimeList = new ArrayList<>();
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
                                _workTime = _workTimeHour + ":" + _workTimeMinute + ":" + _workTimeSecond;
                            }
                            if (!_sum.equals("null")) {
                                int sum = Integer.parseInt(_sum);
                                String sumHour = sum / 3600 + "";
                                String sumMinute = sum % 3600 / 60 + "";
                                String sumSecond = sum % 3600 % 60 + "";
                                sumTime = sumHour + ":" + sumMinute + ":" + sumSecond;
                            }

                            lastTime.setStartWorkDate(startLastDate);
                            lastTime.setStartWorkTime(startLastTime);
                            lastTime.setEndWorkDate(endLastDate);
                            lastTime.setEndWorkTime(endLastTime);
                            lastTime.setWorkTime(_workTime);
                            if (i == 0) {
                                Share.createandDisplayPdf(activity.getResources().getString(R.string.explains),activity.getResources().getString(workTime), activity.getResources().getString(R.string.endTime), activity.getResources().getString(R.string.startTime),activity.getResources().getString(R.string.numberRow), doc);
                            }

                            Share.createandDisplayPdf("",_workTime, endLastDate, startLastDate,(i+1)+"", doc);
                            Share.createandDisplayPdf("","", endLastTime, startLastTime,"", doc);
                            lastTimeList.add(lastTime);
                        }
                        Share.createandDisplayPdf("","", "", "","", doc);
                        Share.createandDisplayPdf("","مجموع کل کارکرد"+sumTime, "", "","", doc);
                        doc.close();
                        progressbar.setVisibility(View.GONE);
                        textSum.setVisibility(View.VISIBLE);
                        textSum.setText("فایل"+user+"_time"+".pdf"+"در حافظه داخلی یا خارجی گوشی ذخیره شد.");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);

                }

                @Override
                public Map onMapPost() {
                    Map<String, String> Params1 = new HashMap<>();
                    Params1.put("user", user);
                    Params1.put("keyStart", keyStart);
                    Params1.put("keyEnd", keyEnd);
                    Params1.put("kind", kind);
                    Params1.put("start_row", "0");
                    Params1.put("paging", "no");
                    Params1.put("key_text_android", "ktaa");
                    return Params1;
                }
            });
        }

    }



    public void dataDeleteModel(String url, final VisitLastDateEmployerToEmployeeAdapter adapter, String startDateDelete, String startTimeDelete, final FragmentActivity activityDelete, final ProgressBar progressbar) {
        String keyDateDelete = startDateDelete.replace("/", "");
        String keyTimeDelete = startTimeDelete.replace(":", "");
        final String keyDelete = user + keyDateDelete + keyTimeDelete;
        Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                presenter.deleteMessagePresenter(result);
                progressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.INVISIBLE);

            }

            @Override
            public Map onMapPost() {
                Map<String, String> Params1 = new HashMap<>();
                Params1.put("user", user);
                Params1.put("keyDelete", keyDelete);
                Params1.put("kind", kind);
                Params1.put("key_text_android", "ktaa");
                return Params1;

            }
        });
    }

    public void sumModel(String url, String start, String end, final String user, final String kind, final ProgressBar progressbar) {
        if (start.equals("") || end.equals("")) {
            Toast.makeText(activity, "لطفا زمان شروع و پایان را وارد کنید.", Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        final String keyStart = start.replace("/", "");
        final String keyEnd = end.replace("/", "");
        if (Integer.parseInt(keyStart) > Integer.parseInt(keyEnd)) {
            Toast.makeText(activity, R.string.endLargerStart, Toast.LENGTH_SHORT).show();
        } else {
            this.user = user;
            this.kind = kind;
            Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
                @Override
                public void onSuccessResponse(String result) {
                        presenter.resulSumPresenter(result);
                        progressbar.setVisibility(View.GONE);

                 }

                @Override
                public void onError(String error) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);


                }

                @Override
                public Map onMapPost() {
                    Map<String, String> Params1 = new HashMap<>();
                    Params1.put("user", user);
                    Params1.put("keyStart", keyStart);
                    Params1.put("keyEnd", keyEnd);
                    Params1.put("kind", kind);
                    Params1.put("key_text_android", "ktaa");
                    return Params1;
                }
            });
        }

    }
}
