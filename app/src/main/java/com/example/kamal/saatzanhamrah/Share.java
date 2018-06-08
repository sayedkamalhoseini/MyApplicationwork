package com.example.kamal.saatzanhamrah;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import static android.support.v4.app.ActivityCompat.requestPermissions;


/**
 * Created by kamal on 8/22/2017.
 */
public class Share {

    public static final String TAG = "MyTag";

    public static String loadPref(Activity context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("total",
                Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public static void saveSharePref(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("total", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean check(Context context) {


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();


        if (info == null) {


            return false;


        } else {


            return true;

        }


    }



    public static void spinnerAdapter(Context context, Spinner spinner, int arrayHour) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, arrayHour, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public static void getStringResponse(final Activity activity, int method, String url, JSONObject jsonValue, final StringVolleyCallBack callBack) {

        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.onSuccessResponse(response);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error + "");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return callBack.onMapPost();
            }
        };
        stringRequest.setTag(TAG);
        MySingleton.getInstance(activity).addToRequestQueue(stringRequest);
      }

    public static void createandDisplayPdf(String explains,String text1, String text2, String text3,String numberRow, Document doc) {

        try {

            BaseFont farsiFont = BaseFont.createFont("assets/fonts/XB Zar.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font paraFont = new Font(farsiFont, 15);

            Paragraph p5 = new Paragraph(explains, paraFont);
            Paragraph p1 = new Paragraph(text1, paraFont);
            Paragraph p2 = new Paragraph(text2, paraFont);
            Paragraph p3 = new Paragraph(text3, paraFont);
            Paragraph p4 = new Paragraph(numberRow, paraFont);

            PdfPTable table = new PdfPTable(5);
            PdfPCell cell5 = new PdfPCell(p5);
            PdfPCell cell1 = new PdfPCell(p1);
            PdfPCell cell2 = new PdfPCell(p2);
            PdfPCell cell3 = new PdfPCell(p3);
            PdfPCell cell4 = new PdfPCell(p4);
            if (text1.equals("")) {
                cell1.setBorder(2);
                cell2.setBorder(2);
                cell3.setBorder(2);
                cell4.setBorder(2);
                cell5.setBorder(2);
              } else {
                cell1.setBorder(0);
                cell2.setBorder(0);
                cell3.setBorder(0);
                cell4.setBorder(0);
                cell5.setBorder(0);
            }
            cell1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell2.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell3.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell4.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell5.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setWidthPercentage(100);
            table.addCell(cell5);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            doc.add(table);
        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
    }


    public interface StringVolleyCallBack {
        public void onSuccessResponse(String result);

        public void onError(String error);

        public Map onMapPost();
    }



    public static void showSnackBar(Context context, CoordinatorLayout layout, String value) {
        final Snackbar snackbar = Snackbar
                .make(layout, value, Snackbar.LENGTH_LONG)
                .setAction("بستن", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        TextView txtVal = (TextView) (snackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
        txtVal.setTextSize(15);
        TextView txtAction = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_action);
        txtAction.setTextSize(15);
        txtAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.setDuration(10000);
        snackbar.show();
          }

    private static void checkRunTimePermission(Activity activity) {
        String[] permissionArrays = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(activity,permissionArrays,1);
        } else {

        }
    }

    public static void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults,Activity activity) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    checkRunTimePermission(activity);
                }
                return;
            }
        }
    }
}




