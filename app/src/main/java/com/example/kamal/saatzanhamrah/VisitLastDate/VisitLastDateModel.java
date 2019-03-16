package com.example.kamal.saatzanhamrah.VisitLastDate;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

import static com.example.kamal.saatzanhamrah.R.string.error;
import static com.example.kamal.saatzanhamrah.R.string.startDate;
import static com.example.kamal.saatzanhamrah.R.string.startTime;
import static com.example.kamal.saatzanhamrah.R.string.workTime;

/**
 * Created by kamal on 12/18/2017.
 */

public class VisitLastDateModel {
    Activity activity;
    VisitLastDatePresenter presenter;
    Document doc;
    FileOutputStream fOut;
    List<LastTime> lastTimeList;
    String _workTimeHour, _workTimeMinute, _workTimeSecond, user, kind, sumTime;


    public VisitLastDateModel(VisitLastDatePresenter visitLastDatePresenter, FragmentActivity activity) {
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
            progressbar.setVisibility(View.GONE);
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
                            String confirm_employer = jsonObject1.getString("confirm_employer");

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
                            lastTime.setConfirm_employer(confirm_employer);
                            if(confirm_employer.equals("1")){
                                lastTime.setSelected(true);
                            }else if(confirm_employer.equals("0")){
                                lastTime.setSelected(false);
                            }else{
                                lastTime.setSelected(false);
                            }

                            lastTimeList.add(lastTime);
                        }
                        presenter.passListPresenter(lastTimeList);
                        floatingActionButton.setVisibility(View.GONE);
                        progressbar.setVisibility(View.GONE);


                    } catch (JSONException e) {
                        progressbar.setVisibility(View.GONE);
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

    public void buildPdf(final String url, final String start, final String end, final String user, final String kind, final ProgressBar progressbar, final CoordinatorLayout coordinatorLayout, final TextView textSum) {
        if (start.equals("") || end.equals("")) {
            Toast.makeText(activity, R.string.enterStartEnd, Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        final String keyStart = start.replace("/", "");
        final String keyEnd = end.replace("/", "");
        if (Integer.parseInt(keyStart) > Integer.parseInt(keyEnd)) {
            Toast.makeText(activity, R.string.endLargerStart, Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
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
                        File file = new File(dir, user + "_time"+keyStart+"_"+keyEnd + ".pdf");
                        fOut = new FileOutputStream(file);
                        PdfWriter.getInstance(doc, fOut);
                        doc.open();
                    } catch (DocumentException de) {
                        Log.e("PDFCreator", "DocumentException:" + de);
                    } catch (IOException e) {
                        Log.e("PDFCreator", "ioException:" + e);
                    }
                    try {
                        Share.createandDisplayPdf("", "", "", "", "", doc);
                        Share.createandDisplayPdf("", "", "ساعت زن همراه", "", "", doc);
                        Share.createandDisplayPdf("", "", "", "", "", doc);
                        Share.createandDisplayPdf(end, "تا تاریخ", start, "از تاریخ", "کارکرد کاربر" + user, doc);
                        Share.createandDisplayPdf("", "", "", "", "", doc);
                        JSONObject jsonObject = new JSONObject(result);
                        String success = jsonObject.getString("success");
                        String _sum = jsonObject.getString("sum");
                        if (success.equals("1")) {
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
                                String explains = jsonObject1.getString("explains");
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
                                    Share.createandDisplayPdf(activity.getResources().getString(R.string.explains), activity.getResources().getString(workTime), activity.getResources().getString(R.string.endTime), activity.getResources().getString(R.string.startTime), activity.getResources().getString(R.string.numberRow), doc);
                                }

                                Share.createandDisplayPdf("", _workTime, endLastDate, startLastDate, (i + 1) + "", doc);
                                Share.createandDisplayPdf(explains, "", endLastTime, startLastTime, "", doc);
                                lastTimeList.add(lastTime);
                            }
                            Share.createandDisplayPdf("", "", "", "", "", doc);
                            Share.createandDisplayPdf("", "مجموع کل کارکرد" + sumTime, "", "", "", doc);
                            doc.close();
                            progressbar.setVisibility(View.GONE);
                            textSum.setVisibility(View.VISIBLE);
                            textSum.setText("فایل" + " " + user +keyStart+"_"+keyEnd+ "_time" + ".pdf" + "در حافظه داخلی یا خارجی گوشی ذخیره شد.");
                        } else {
                            Toast.makeText(activity, activity.getResources().getString(R.string.noLastDate), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);

                        }

                    } catch (JSONException e) {
                        Toast.makeText(activity, R.string.tryAgain, Toast.LENGTH_LONG).show();
                        progressbar.setVisibility(View.GONE);
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


    public void dataDeleteModel(String url, final LastTimeAdapter adapter, String startDateDelete, String startTimeDelete, final FragmentActivity activityDelete, final ProgressBar progressbar) {
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
            progressbar.setVisibility(View.GONE);
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

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private boolean saveExcelFile(Context context, String fileName) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return false;
        }

        boolean success = false;
        Workbook wb = new HSSFWorkbook();

        Cell c = null;
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet sheet1 = null;
        sheet1 = wb.createSheet("Export");

        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("مجموع کار");
        c.setCellStyle(cs);
        c = row.createCell(1);
        c.setCellValue("ساعت پایان کار");
        c.setCellStyle(cs);
        c = row.createCell(2);
        c.setCellValue("تاریخ پایان کار");
        c.setCellStyle(cs);
        c = row.createCell(3);
        c.setCellValue("ساعت شروع کار");
        c.setCellStyle(cs);
        c = row.createCell(4);
        c.setCellValue("تاریخ شروع کار");
        c.setCellStyle(cs);
        c = row.createCell(5);
        c.setCellValue("ردیف");
        c.setCellStyle(cs);


        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));

        File root = new File(Environment.getExternalStorageDirectory(), "");
        if (!root.exists()) {
            root.setReadable(true);
            root.setWritable(true);
            root.mkdirs();
        }
        File file = new File(root, fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            success = true;
        } catch (IOException e) {
        } catch (Exception e) {
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }

    public void buildExcel(String url, final String start, final String end, final String user, final String kind, final ProgressBar progressbar, CoordinatorLayout coordinatorLayout, final TextView textSum) {
        if (start.equals("") || end.equals("")) {
            Toast.makeText(activity, R.string.enterStartEnd, Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        final String keyStart = start.replace("/", "");
        final String keyEnd = end.replace("/", "");
        if (Integer.parseInt(keyStart) > Integer.parseInt(keyEnd)) {
            Toast.makeText(activity, R.string.endLargerStart, Toast.LENGTH_SHORT).show();
            progressbar.setVisibility(View.GONE);
        } else {
            this.user = user;
            this.kind = kind;
            Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
                @Override
                public void onSuccessResponse(String result) {
                    if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                        return;
                    }

                    boolean success = false;
                    Workbook wb = new HSSFWorkbook();

                    Cell c = null;
                    CellStyle cs = wb.createCellStyle();
                    cs.setFillForegroundColor(HSSFColor.LIME.index);
                    cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    cs.setAlignment(CellStyle.ALIGN_CENTER);


                    Sheet sheet1 = null;
                    sheet1 = wb.createSheet("Export");


                    Row row = sheet1.createRow(0);

                    c = row.createCell(0);
                    c.setCellValue("ردیف");
                    c.setCellStyle(cs);
                    c = row.createCell(1);
                    c.setCellValue("تاریخ شروع کار");
                    c.setCellStyle(cs);
                    c = row.createCell(2);
                    c.setCellValue("ساعت شروع کار");
                    c.setCellStyle(cs);
                    c = row.createCell(3);
                    c.setCellValue("تاریخ پایان کار");
                    c.setCellStyle(cs);
                    c = row.createCell(4);
                    c.setCellValue("ساعت پایان کار");
                    c.setCellStyle(cs);
                    c = row.createCell(5);
                    c.setCellValue("مجموع کار");
                    c.setCellStyle(cs);
                    c = row.createCell(6);
                    c.setCellValue("توضیحات");
                    c.setCellStyle(cs);


                    sheet1.setColumnWidth(0, (8 * 500));
                    sheet1.setColumnWidth(1, (8 * 500));
                    sheet1.setColumnWidth(2, (8 * 500));
                    sheet1.setColumnWidth(3, (8 * 500));
                    sheet1.setColumnWidth(4, (8 * 500));
                    sheet1.setColumnWidth(5, (8 * 500));
                    sheet1.setColumnWidth(6, (8 * 500));

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String success1 = jsonObject.getString("success");
                        String _sum = jsonObject.getString("sum");
                        if (success1.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("lastVisit");
                            lastTimeList = new ArrayList<>();
                            LastTime lastTime;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                lastTime = new LastTime();
                                row = sheet1.createRow(i + 1);
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String startLastDate = jsonObject1.getString("start_date");
                                String startLastTime = jsonObject1.getString("start_time");
                                String endLastDate = jsonObject1.getString("end_date");
                                String endLastTime = jsonObject1.getString("end_time");
                                String _workTime = jsonObject1.getString("worktime");
                                String explains = jsonObject1.getString("explains");
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

                                c = row.createCell(0);
                                c.setCellValue(i + 1);
                                c.setCellStyle(cs);
                                c = row.createCell(1);
                                c.setCellValue(startLastDate);
                                c.setCellStyle(cs);
                                c = row.createCell(2);
                                c.setCellValue(startLastTime);
                                c.setCellStyle(cs);
                                c = row.createCell(3);
                                c.setCellValue(endLastDate);
                                c.setCellStyle(cs);
                                c = row.createCell(4);
                                c.setCellValue(endLastTime);
                                c.setCellStyle(cs);
                                c = row.createCell(5);
                                c.setCellValue(_workTime);
                                c.setCellStyle(cs);
                                c = row.createCell(6);
                                c.setCellValue(explains);
                                c.setCellStyle(cs);
                            }

                            File root = new File(Environment.getExternalStorageDirectory(), "");
                            if (!root.exists()) {
                                root.setReadable(true);
                                root.setWritable(true);
                                root.mkdirs();
                            }
                            File file = new File(root, user + keyStart+"_"+keyEnd+"_time" + ".xls");
                            FileOutputStream os = null;

                            try {
                                os = new FileOutputStream(file);
                                wb.write(os);
                                success = true;
                            } catch (IOException e) {
                            } catch (Exception e) {
                            } finally {
                                try {
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                }
                            }
                            progressbar.setVisibility(View.GONE);
                            textSum.setVisibility(View.VISIBLE);
                            textSum.setText("فایل" + " " + user + keyStart+"_"+keyEnd+"_time" + ".xls" + "در حافظه داخلی یا خارجی گوشی ذخیره شد.");
                        } else {
                            Toast.makeText(activity, activity.getResources().getString(R.string.noLastDate), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);

                        }

                    } catch (JSONException e) {
                        Toast.makeText(activity, R.string.tryAgain, Toast.LENGTH_LONG).show();
                        progressbar.setVisibility(View.GONE);
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
}

