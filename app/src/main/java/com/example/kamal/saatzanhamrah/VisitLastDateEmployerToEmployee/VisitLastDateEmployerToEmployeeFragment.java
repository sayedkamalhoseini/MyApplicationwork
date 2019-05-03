package com.example.kamal.saatzanhamrah.VisitLastDateEmployerToEmployee;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer.VisitEmployeeAdapter;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

public class VisitLastDateEmployerToEmployeeFragment extends Fragment implements View.OnClickListener, MainActivity.PassData, VisitEmployeeAdapter.PassDataEmployeeToEmployer, VisitLastDateEmployerToEmployeeAdapter.PassDataCheck {
    VisitLastDateEmployerToEmployeePresenter presenter;
    AppCompatActivity activity;
    EditText visitStart, visitEnd;
    ImageButton setVisitStart, setVisitEnd;
    Button buttonShowList, buttonBuildPdf, buttonSum, buttonBuildExcel, buttonBuildConfirmListExcel, buttonBuildConfirmListPdf;
    private RecyclerView recyclerView;
    private VisitLastDateEmployerToEmployeeAdapter adapter;
    private String url = "http://kamalroid.ir/visit_last_time.php";
    private String urlConfirm = "http://kamalroid.ir/confirm_time.php";
    private String urlConfirmJust = "http://kamalroid.ir/visit_last_time_confirm.php";
    private String urlSum = "http://kamalroid.ir/get_sum.php";
    private String mDay, mMonth, _Date, user, kind;
    List<LastTimeConfirm> lastTimeList = new ArrayList<>();
    LastTimeConfirm lastTimeConfirm;
    private int position, start_row;
    private Dialog dialog;
    private CheckBox checkBox;
    private ProgressBar progressbar;
    private TextView textViewExplain1;
    private TextView textTitle;
    private FloatingActionButton floatingActionButton;
    private LinearLayout layoutTitle;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private int flagConfirm = 0;
    private TextView textSumMessage, textSum;
    private String userUpdate,userMain;
    private HorizontalScrollView horizontalScrollView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int pos;
    private String mHour,mMinute,mSecond;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new VisitLastDateEmployerToEmployeePresenter(this);
        verifyStoragePermissions(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visit_last_date_confirm, container, false);
        setVisitStart = (ImageButton) view.findViewById(R.id.imageButton_visitLastDate_setDateStart);
        setVisitEnd = (ImageButton) view.findViewById(R.id.imageButton_visitLastDate_setDateEnd);
        buttonShowList = (Button) view.findViewById(R.id.button_visitLastDate_showListVisit);
        buttonBuildPdf = (Button) view.findViewById(R.id.button_visitLastDate_buildPdf);
        buttonBuildExcel = (Button) view.findViewById(R.id.button_visitLastDate_buildExcel);
        buttonBuildConfirmListExcel = (Button) view.findViewById(R.id.button_visitLastDate_confirm_excel);
        buttonBuildConfirmListPdf = (Button) view.findViewById(R.id.button_visitLastDate_confirm_pdf);
        buttonSum = (Button) view.findViewById(R.id.button_visitLastDate_sum);
        visitStart = (EditText) view.findViewById(R.id.editText_visitLastDate_setDateStart);
        visitEnd = (EditText) view.findViewById(R.id.editText_visitLastDate_setDateEnd);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_visitLastDate_ListVisit);
        progressbar = (ProgressBar) view.findViewById(R.id.progressBar_visitLastDateFragment_loading);
        textSumMessage = (TextView) view.findViewById(R.id.textView_visitLastDate_sumMessage);
        layoutTitle = (LinearLayout) view.findViewById(R.id.linear_visitLastDate_titleListVisit);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinate_visitLast_layout);
        textSum = (TextView) view.findViewById(R.id.textView_visitLastDate_sum);
        textTitle = (TextView) getActivity().findViewById(R.id.textView_toolbar_title);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView_visitLastDate_horizontal);
        toolbar = getActivity().findViewById(R.id.toolbar);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_visitListDate_loading);
        textViewExplain1=view.findViewById(R.id.textView_visitLastDate_confirm);
        ColorDrawable colorToolbar = (ColorDrawable) toolbar.getBackground();
        buttonShowList.setBackgroundColor(colorToolbar.getColor());
        buttonSum.setBackgroundColor(colorToolbar.getColor());
        buttonBuildPdf.setBackgroundColor(colorToolbar.getColor());
        buttonBuildExcel.setBackgroundColor(colorToolbar.getColor());
        buttonBuildConfirmListExcel.setBackgroundColor(colorToolbar.getColor());
        buttonBuildConfirmListPdf.setBackgroundColor(colorToolbar.getColor());
        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
            }
        });
        textTitle.setText(userUpdate + "مشاهده کارکرد کاربر");
        setVisitStart.setOnClickListener(this);
        setVisitEnd.setOnClickListener(this);
        visitStart.setOnClickListener(this);
        visitEnd.setOnClickListener(this);
        buttonShowList.setOnClickListener(this);
        buttonBuildPdf.setOnClickListener(this);
        buttonBuildExcel.setOnClickListener(this);
        buttonSum.setOnClickListener(this);
        buttonBuildConfirmListExcel.setOnClickListener(this);
        buttonBuildConfirmListPdf.setOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int count = recyclerView.getAdapter().getItemCount();
                int getLastVisible = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if (dy > 0) {
                    if (getLastVisible == count - 1) {
                        floatingActionButton.setVisibility(View.VISIBLE);
                        floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressbar.setVisibility(View.VISIBLE);
                                start_row += 20;
                                presenter.getLastDatePresenterMore(url, visitStart.getText().toString(), visitEnd.getText().toString(), userMain, kind, start_row, progressbar, floatingActionButton);


                            }
                        });

                    }
                } else if (dy < 0) {
                    floatingActionButton.setVisibility(View.GONE);
                } else {
                    floatingActionButton.setVisibility(View.GONE);
                }


            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_visitLastDate_setDateStart:
                calender(visitStart);
                break;
            case R.id.imageButton_visitLastDate_setDateEnd:
                calender(visitEnd);
                break;
            case R.id.editText_visitLastDate_setDateStart:
                calender(visitStart);
                break;
            case R.id.editText_visitLastDate_setDateEnd:
                calender(visitEnd);
                break;
            case R.id.button_visitLastDate_showListVisit:
                if (Share.check(getContext())) {
                    textViewExplain1.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    textSum.setVisibility(View.GONE);
                    textSumMessage.setVisibility(View.GONE);
                    layoutTitle.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.VISIBLE);
                    start_row = 0;
                    presenter.getLastDatePresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), userMain, kind, start_row, progressbar, floatingActionButton,userUpdate);
                    flagConfirm = 0;
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }


            case R.id.button_visitLastDate_buildPdf:
                textViewExplain1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSum.setVisibility(View.GONE);
                textSumMessage.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildPdfPresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), userMain, kind, progressbar, textSum, userUpdate);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.button_visitLastDate_confirm_pdf:
                textViewExplain1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSum.setVisibility(View.GONE);
                textSumMessage.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildPdfPresenter(urlConfirmJust, visitStart.getText().toString(), visitEnd.getText().toString(), userMain, kind, progressbar, textSum,userUpdate);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.button_visitLastDate_buildExcel:
                textViewExplain1.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSum.setVisibility(View.GONE);
                textSumMessage.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildExcelPresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), userMain, kind, progressbar, coordinatorLayout, textSum, userUpdate);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.button_visitLastDate_confirm_excel:
                textViewExplain1.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSum.setVisibility(View.GONE);
                textSumMessage.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildExcelPresenter(urlConfirmJust, visitStart.getText().toString(), visitEnd.getText().toString(), userMain, kind, progressbar, coordinatorLayout, textSum,userUpdate);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }


            case R.id.button_visitLastDate_sum:
                textViewExplain1.setVisibility(View.GONE);
                textSum.setVisibility(View.VISIBLE);
                textSumMessage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.sumPresenter(urlSum, visitStart.getText().toString(), visitEnd.getText().toString(), userMain, kind, progressbar);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    public void calender(final EditText setDate) {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                             @Override
                                                                             public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                                                                 monthOfYear += 1;
                                                                                 if (dayOfMonth < 10) {
                                                                                     mDay = "0" + dayOfMonth;
                                                                                 } else {
                                                                                     mDay = dayOfMonth + "";
                                                                                 }
                                                                                 if (monthOfYear < 10) {
                                                                                     mMonth = "0" + monthOfYear;
                                                                                 } else {
                                                                                     mMonth = monthOfYear + "";
                                                                                 }

                                                                                 setDate.setText(year + "/" + mMonth + "/" + mDay);
                                                                             }
                                                                         }, now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay());

        datePickerDialog.setThemeDark(true);
        datePickerDialog.show(getActivity().getFragmentManager(), "tpd");
    }



    public void passListView(List<LastTimeConfirm> lastTimeList) {
        this.lastTimeList = lastTimeList;
        adapter = new VisitLastDateEmployerToEmployeeAdapter(VisitLastDateEmployerToEmployeeFragment.this, lastTimeList, user, kind);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


    }

    public void passListViewMore(List<LastTimeConfirm> lastTimeList) {
        this.lastTimeList = lastTimeList;
        adapter.notifyDataSetChanged();
    }


    @Override
    public void sendDataEmployeeToEmployer(String userMain, String userUpdate, String kind) {
        this.kind = kind;
        this.userMain=userMain;
        this.userUpdate=userUpdate;
    }

//    @Override
//    public void sendDataDelete(FragmentActivity activity1, int position, VisitLastDateEmployerToEmployeeAdapter adapter, String startDateDelete, String startTimeDelete) {
//        progressbar.setVisibility(View.VISIBLE);
//        this.position = position;
//        presenter.dataDeletePresenter(urlDelete, adapter, startDateDelete, startTimeDelete, activity1, user, kind, progressbar);
//    }

    public void messageConfirm(String result) {
        switch (result) {
            case "confirm":
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.confirmTime), Toast.LENGTH_SHORT).show();
                lastTimeConfirm.setSelected(true);
                break;
            case "noConfirm":
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.noConfirmTime), Toast.LENGTH_SHORT).show();
                lastTimeConfirm.setSelected(false);
                break;
            case "failer_interesting_database":
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.registerNull), Toast.LENGTH_SHORT).show();
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
                break;
            case "failure_post":
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.registerError), Toast.LENGTH_SHORT).show();
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
                break;
            default:
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
                break;
        }

    }


    @Override
    public void sendData(String user, String kind,String userUpdate) {
        this.user = user;
        this.kind = kind;

    }

    public void resultSumView(String result) {
        textSumMessage.setVisibility(View.VISIBLE);
        textSum.setVisibility(View.VISIBLE);
        int workTime = Integer.parseInt(result);

        result = Share.changeTime(workTime);

        textSum.setText(result);

    }

    public static void verifyStoragePermissions(android.app.Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void sendDataCheck(FragmentActivity activity1, VisitLastDateEmployerToEmployeeAdapter adapter, String startDate, String startTime, LastTimeConfirm lastTimeConfirm, int pos, CheckBox checkBox, int check) {
        this.lastTimeConfirm = lastTimeConfirm;
        this.checkBox = checkBox;
        this.pos = pos;
        progressbar.setVisibility(View.VISIBLE);
        presenter.dataCheckPresenter(urlConfirm, adapter, startDate, startTime, activity1, userMain, kind, progressbar, check);

    }


}
