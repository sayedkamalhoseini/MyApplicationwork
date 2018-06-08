package com.example.kamal.saatzanhamrah.VisitLastDate;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer.VisitEmployeeAdapter;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class VisitLastDateFragment extends Fragment implements View.OnClickListener, MainActivity.PassData, VisitEmployeeAdapter.PassDataEmployeeToEmployer, LastTimeAdapter.PassDataDelete {
    VisitLastDatePresenter presenter;
    AppCompatActivity activity;
    EditText visitStart, visitEnd;
    ImageButton setVisitStart, setVisitEnd;
    Button buttonShowList, buttonBuildPdf,buttonBuildExcel, buttonSum;
    private RecyclerView recyclerView;
    private LastTimeAdapter adapter;
    private String url = "http://kamalroid.ir/visit_last_time.php";
    private String urlDelete = "http://kamalroid.ir/list_delete.php";
    private String urlSum = "http://kamalroid.ir/get_sum.php";
    private String mDay, mMonth, _Date, user, kind, startDateDelete, startTimeDelete;
    List<LastTime> lastTimeList = new ArrayList<>();
    private int position, start_row;
    private Dialog dialog;
    private ProgressBar progressbar;
    private TextView textTitle;
    private FloatingActionButton floatingActionButton;
    private LinearLayout layoutTitle;
    private TextView textSumMessage, textSum;
    private CoordinatorLayout coordinatorLayout;
    private HorizontalScrollView horizontalScrollView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new VisitLastDatePresenter(this);
        verifyStoragePermissions(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visit_last_date, container, false);
        setVisitStart = (ImageButton) view.findViewById(R.id.imageButton_visitLastDate_setDateStart);
        setVisitEnd = (ImageButton) view.findViewById(R.id.imageButton_visitLastDate_setDateEnd);
        buttonShowList = (Button) view.findViewById(R.id.button_visitLastDate_showListVisit);
        buttonBuildPdf = (Button) view.findViewById(R.id.button_visitLastDate_buildPdf);
        buttonBuildExcel = (Button) view.findViewById(R.id.button_visitLastDate_buildExcel);
        buttonSum = (Button) view.findViewById(R.id.button_visitLastDate_sum);
        visitStart = (EditText) view.findViewById(R.id.editText_visitLastDate_setDateStart);
        visitEnd = (EditText) view.findViewById(R.id.editText_visitLastDate_setDateEnd);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_visitLastDate_ListVisit);
        progressbar = (ProgressBar) view.findViewById(R.id.progressBar_visitLastDateFragment_loading);
        textSumMessage = (TextView) view.findViewById(R.id.textView_visitLastDate_sumMessage);
        layoutTitle = (LinearLayout) view.findViewById(R.id.linear_visitLastDate_titleListVisit);
        textSum = (TextView) view.findViewById(R.id.textView_visitLastDate_sum);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinate_visitLast_layout);
        textTitle = (TextView) getActivity().findViewById(R.id.textView_toolbar_title);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_visitListDate_loading);
        horizontalScrollView= (HorizontalScrollView) view.findViewById(R.id.scrollView_visitLastDate_horizontal);
        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
            }
        });
        textTitle.setText(user+ " " + "مشاهده کارکرد کاربر");
        setVisitStart.setOnClickListener(this);
        setVisitEnd.setOnClickListener(this);
        visitStart.setOnClickListener(this);
        visitEnd.setOnClickListener(this);
        buttonShowList.setOnClickListener(this);
        buttonBuildPdf.setOnClickListener(this);
        buttonBuildExcel.setOnClickListener(this);
        buttonSum.setOnClickListener(this);
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
                                presenter.getLastDatePresenterMore(url, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, start_row, progressbar, floatingActionButton);

                            }
                        });

                    }
                }
                else if(dy<0){
                    floatingActionButton.setVisibility(View.GONE);
                }
                else {
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
                    recyclerView.setVisibility(View.VISIBLE);
                    textSum.setVisibility(View.GONE);
                    textSumMessage.setVisibility(View.GONE);
                    layoutTitle.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.VISIBLE);
                    start_row = 0;
                    presenter.getLastDatePresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, start_row, progressbar, floatingActionButton);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.button_visitLastDate_buildPdf:
                floatingActionButton.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSum.setVisibility(View.GONE);
                textSumMessage.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildPdfPresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, progressbar,coordinatorLayout,textSum);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.button_visitLastDate_buildExcel:
                floatingActionButton.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSum.setVisibility(View.GONE);
                textSumMessage.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildExcelPresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, progressbar,coordinatorLayout,textSum);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.button_visitLastDate_sum:
                floatingActionButton.setVisibility(View.GONE);
                textSum.setText("");
                textSum.setVisibility(View.VISIBLE);
                textSumMessage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                layoutTitle.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.sumPresenter(urlSum, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, progressbar);
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


    public void passListView(List<LastTime> lastTimeList) {
        this.lastTimeList = lastTimeList;
        adapter = new LastTimeAdapter(VisitLastDateFragment.this, lastTimeList, user, kind);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    public void passListViewMore(List<LastTime> lastTimeList) {
        adapter.notifyDataSetChanged();
    }


    @Override
    public void sendDataEmployeeToEmployer(String user, String kind) {
        this.user = user;
        this.kind = kind;
    }

    @Override
    public void sendDataDelete(FragmentActivity activity1, int position, LastTimeAdapter adapter, String startDateDelete, String startTimeDelete) {
        progressbar.setVisibility(View.VISIBLE);
        this.position = position;
        presenter.dataDeletePresenter(urlDelete, adapter, startDateDelete, startTimeDelete, activity1, user, kind, progressbar);
    }

    public void messageDeleteView(String result) {
        switch (result) {
            case "done":
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.deleteTime), Toast.LENGTH_SHORT).show();
                this.lastTimeList.remove(position);
                adapter.notifyDataSetChanged();
                break;
            case "failer_interesting_database":
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.registerNull), Toast.LENGTH_SHORT).show();
                break;
            case "failure_post":
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.registerError), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                break;
        }

    }


    @Override
    public void sendData(String user, String kind) {
        this.user = user;
        this.kind = kind;
    }

    public void resultSumView(String result) {
        if(!result.equals("")) {
            textSumMessage.setVisibility(View.VISIBLE);
            textSum.setVisibility(View.VISIBLE);
            int workTime = Integer.parseInt(result);
            String _workTimeHour = workTime / 3600 + "";
            String _workTimeMinute = workTime % 3600 / 60 + "";
            String _workTimeSecond = workTime % 3600 % 60 + "";
            result = _workTimeHour + ":" + _workTimeMinute + ":" + _workTimeSecond;
            textSum.setText(result);
        }
        else {
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.noLastDate), Toast.LENGTH_SHORT).show();
            textSum.setText(result);
        }

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

}
