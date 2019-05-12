package com.example.kamal.saatzanhamrah.VisitLastVacation;

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
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

public class VisitLastVacationFragment extends Fragment implements View.OnClickListener, MainActivity.PassData, LastVacationAdapter.PassDataDelete {
    VisitLastVacationPresenter presenter;
    AppCompatActivity activity;
    EditText visitStart, visitEnd;
    ImageButton setVisitStart, setVisitEnd;
    Button buttonShowVacationHour, buttonShowVacationDate, buttonBuildPdfVacHour, buttonBuildPdfVacDate, buttonBuildExcelVacHour, buttonBuildExcelVacDate, buttonSum, buttonBuildConfirmListExcel, buttonBuildConfirmListPdf;
    private RecyclerView recyclerView;
    private LastVacationAdapter adapter;
    private String url = "http://kamalroid.ir/visit_vacation_hour.php";
    private String urlVacationDate = "http://kamalroid.ir/visit_vacation_date.php";
    private String urlDelete = "http://kamalroid.ir/list_delete_vac_hour.php";
    private String urlDeleteVacDate = "http://kamalroid.ir/list_delete_vac_date.php";
    private String urlSum = "http://kamalroid.ir/get_sum_vac_hour.php";
    private String urlConfirmJust = "http://kamalroid.ir/visit_last_time_confirm.php";
    private String mDay, mMonth, _Date, user, kind, startDateDelete, startTimeDelete;
    List<LastVacation> lastTimeList = new ArrayList<>();
    private int position, start_row;
    private Dialog dialog;
    private ProgressBar progressbar;
    private TextView textTitle;
    private FloatingActionButton floatingActionButton;
    private LinearLayout layoutTitlVacHour, layoutTitleVacDate;
    private TextView textSumVacHourMessage, textSumVacHour, title_confirm, textSumVacDate, textSumVacDateMessage;
    private CoordinatorLayout coordinatorLayout;
    private HorizontalScrollView horizontalScrollView;
    private Toolbar toolbar;
    private TextView view_confirm;
    private TextView textViewExplain1;
    private String userUpdate, flagVacDate = "";
    private LinearLayout linearLayoutAdapter;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new VisitLastVacationPresenter(this);
        verifyStoragePermissions(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visit_vacation, container, false);
        setVisitStart = (ImageButton) view.findViewById(R.id.imageButton_visitLastDate_setDateStart);
        setVisitEnd = (ImageButton) view.findViewById(R.id.imageButton_visitLastDate_setDateEnd);
        buttonShowVacationHour = (Button) view.findViewById(R.id.button_visitLastVacation_hour);
        buttonShowVacationDate = (Button) view.findViewById(R.id.button_visitLastVacation_date);
        buttonBuildPdfVacHour = (Button) view.findViewById(R.id.button_visitLastVacation_buildPdfVacHour);
        buttonBuildPdfVacDate = (Button) view.findViewById(R.id.button_visitLastVacation_buildPdfVacDate);
        buttonBuildExcelVacHour = (Button) view.findViewById(R.id.button_visitLastVacation_buildExcelVacHour);
        buttonBuildExcelVacDate = (Button) view.findViewById(R.id.button_visitLastVacation_buildExcelVacDate);
        buttonSum = (Button) view.findViewById(R.id.button_visitLastVacation_sum);
        visitStart = (EditText) view.findViewById(R.id.editText_visitLastDate_setDateStart);
        visitEnd = (EditText) view.findViewById(R.id.editText_visitLastDate_setDateEnd);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_visitLastDate_ListVisit);
        linearLayoutAdapter = view.findViewById(R.id.linear_visitLastDate_layout);
        progressbar = (ProgressBar) view.findViewById(R.id.progressBar_visitLastDateFragment_loading);
        textSumVacHourMessage = (TextView) view.findViewById(R.id.textView_visitLastDate_sumVacHourMessage);
        textSumVacDateMessage = (TextView) view.findViewById(R.id.textView_visitLastDate_sumVacDateMessage);
        layoutTitlVacHour = (LinearLayout) view.findViewById(R.id.linear_visitLastDate_titleVacHour);
        layoutTitleVacDate = (LinearLayout) view.findViewById(R.id.linear_visitLastDate_titleVacDate);
        textSumVacHour = (TextView) view.findViewById(R.id.textView_visitLastDate_sumVacHour);
        textSumVacDate = (TextView) view.findViewById(R.id.textView_visitLastDate_sumVacDate);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinate_visitLast_layout);
        textTitle = (TextView) getActivity().findViewById(R.id.textView_toolbar_title);
        title_confirm = view.findViewById(R.id.confirm_text);
        toolbar = getActivity().findViewById(R.id.toolbar);
        textViewExplain1 = view.findViewById(R.id.textView_visitLastDate_delete);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_visitListDate_loading);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView_visitLastDate_horizontal);
        ColorDrawable colorToolbar = (ColorDrawable) toolbar.getBackground();
        buttonShowVacationHour.setBackgroundColor(colorToolbar.getColor());
        buttonShowVacationDate.setBackgroundColor(colorToolbar.getColor());
        buttonSum.setBackgroundColor(colorToolbar.getColor());
        buttonBuildPdfVacHour.setBackgroundColor(colorToolbar.getColor());
        buttonBuildPdfVacDate.setBackgroundColor(colorToolbar.getColor());
        buttonBuildExcelVacHour.setBackgroundColor(colorToolbar.getColor());
        buttonBuildExcelVacDate.setBackgroundColor(colorToolbar.getColor());
        if (kind.equals("employer")) {
            title_confirm.setVisibility(View.GONE);
            layoutTitlVacHour.setWeightSum((float) 3.5);
            buttonBuildConfirmListPdf.setVisibility(View.GONE);
            buttonBuildConfirmListExcel.setVisibility(View.GONE);
        }
        textTitle.setText(userUpdate + " " + "مشاهده مرخصی کاربر");
        setVisitStart.setOnClickListener(this);
        setVisitEnd.setOnClickListener(this);
        visitStart.setOnClickListener(this);
        visitEnd.setOnClickListener(this);
        buttonShowVacationHour.setOnClickListener(this);
        buttonShowVacationDate.setOnClickListener(this);
        buttonBuildPdfVacHour.setOnClickListener(this);
        buttonBuildPdfVacDate.setOnClickListener(this);
        buttonBuildExcelVacHour.setOnClickListener(this);
        buttonBuildExcelVacDate.setOnClickListener(this);
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
                                if (flagVacDate.equals(""))
                                    presenter.getLastDatePresenterMore(url, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, start_row, progressbar, floatingActionButton);
                                else
                                    presenter.getLastVacDatePresenterMore(urlVacationDate, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, start_row, progressbar, floatingActionButton);

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
            case R.id.button_visitLastVacation_hour:
                if (Share.check(getContext())) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textSumVacHour.setVisibility(View.GONE);
                    textSumVacHourMessage.setVisibility(View.GONE);
                    layoutTitlVacHour.setVisibility(View.VISIBLE);
                    layoutTitleVacDate.setVisibility(View.INVISIBLE);
                    textViewExplain1.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.VISIBLE);
                    textSumVacHour.setVisibility(View.GONE);
                    textSumVacHourMessage.setVisibility(View.GONE);
                    textSumVacDateMessage.setVisibility(View.GONE);
                    textSumVacDate.setVisibility(View.GONE);
                    flagVacDate="";
                    start_row = 0;
                    presenter.getLastVacHourPresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, start_row, progressbar, floatingActionButton);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.button_visitLastVacation_date:
                if (Share.check(getContext())) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textSumVacHour.setVisibility(View.GONE);
                    textSumVacHourMessage.setVisibility(View.GONE);
                    layoutTitlVacHour.setVisibility(View.INVISIBLE);
                    layoutTitleVacDate.setVisibility(View.VISIBLE);
                    textViewExplain1.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.VISIBLE);
                    textSumVacHour.setVisibility(View.GONE);
                    textSumVacHourMessage.setVisibility(View.GONE);
                    textSumVacDateMessage.setVisibility(View.GONE);
                    textSumVacDate.setVisibility(View.GONE);
                    flagVacDate="vac_Date";
                    start_row = 0;
                    presenter.getLastVacDatePresenter(urlVacationDate, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, start_row, progressbar, floatingActionButton);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }


            case R.id.button_visitLastVacation_buildPdfVacHour:
                textViewExplain1.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSumVacHour.setVisibility(View.GONE);
                textSumVacHourMessage.setVisibility(View.GONE);
                layoutTitleVacDate.setVisibility(View.GONE);
                layoutTitlVacHour.setVisibility(View.GONE);
                textSumVacHour.setVisibility(View.GONE);
                textSumVacHourMessage.setVisibility(View.GONE);
                textSumVacDateMessage.setVisibility(View.GONE);
                textSumVacDate.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildPdfPresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, progressbar, coordinatorLayout, textSumVacHour);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.button_visitLastVacation_buildPdfVacDate:
                textViewExplain1.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSumVacHour.setVisibility(View.GONE);
                textSumVacHourMessage.setVisibility(View.GONE);
                layoutTitleVacDate.setVisibility(View.GONE);
                layoutTitlVacHour.setVisibility(View.GONE);
                textSumVacHour.setVisibility(View.GONE);
                textSumVacHourMessage.setVisibility(View.GONE);
                textSumVacDateMessage.setVisibility(View.GONE);
                textSumVacDate.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildPdfVacDatePresenter(urlVacationDate, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, progressbar, coordinatorLayout, textSumVacHour);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.button_visitLastVacation_buildExcelVacHour:
                textViewExplain1.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSumVacHour.setVisibility(View.GONE);
                textSumVacHourMessage.setVisibility(View.GONE);
                layoutTitlVacHour.setVisibility(View.GONE);
                layoutTitleVacDate.setVisibility(View.GONE);
                textSumVacHour.setVisibility(View.GONE);
                textSumVacHourMessage.setVisibility(View.GONE);
                textSumVacDateMessage.setVisibility(View.GONE);
                textSumVacDate.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildExcelPresenter(url, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, progressbar, coordinatorLayout, textSumVacHour);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.button_visitLastVacation_buildExcelVacDate:
                textViewExplain1.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                textSumVacHour.setVisibility(View.GONE);
                textSumVacHourMessage.setVisibility(View.GONE);
                layoutTitlVacHour.setVisibility(View.GONE);
                layoutTitleVacDate.setVisibility(View.GONE);
                textSumVacHour.setVisibility(View.GONE);
                textSumVacHourMessage.setVisibility(View.GONE);
                textSumVacDateMessage.setVisibility(View.GONE);
                textSumVacDate.setVisibility(View.GONE);
                if (Share.check(getContext())) {
                    progressbar.setVisibility(View.VISIBLE);
                    presenter.buildExcelVacDatePresenter(urlVacationDate, visitStart.getText().toString(), visitEnd.getText().toString(), user, kind, progressbar, coordinatorLayout, textSumVacHour);
                    break;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.button_visitLastVacation_sum:
                textViewExplain1.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                textSumVacHour.setText("");
                textSumVacHour.setVisibility(View.VISIBLE);
                layoutTitlVacHour.setVisibility(View.GONE);
                layoutTitleVacDate.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
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


    public void passListView(List<LastVacation> lastTimeList, String vac_date) {
        this.lastTimeList = lastTimeList;
        if (vac_date.equals(""))
            adapter = new LastVacationAdapter(VisitLastVacationFragment.this, lastTimeList, user, kind);
        else
            adapter = new LastVacationAdapter(VisitLastVacationFragment.this, lastTimeList, user, kind, vac_date);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    public void passListViewMore(List<LastVacation> lastTimeList) {
        this.lastTimeList = lastTimeList;
        adapter.notifyDataSetChanged();
    }

    public void passListVacDateViewMore(List<LastVacation> lastTimeList) {
        this.lastTimeList = lastTimeList;
        adapter.notifyDataSetChanged();
    }


    @Override
    public void sendDataDelete(FragmentActivity activity1, int position, LastVacationAdapter adapter, String startDateDelete, String startTimeDelete) {
        progressbar.setVisibility(View.VISIBLE);
        this.position = position;
        presenter.dataDeletePresenter(urlDelete, adapter, startDateDelete, startTimeDelete, activity1, user, kind, progressbar);
    }

    @Override
    public void sendDataDeleteVacDate(FragmentActivity activity1, int position, LastVacationAdapter adapter, String startDateDelete) {
        progressbar.setVisibility(View.VISIBLE);
        this.position = position;
        presenter.dataDeleteVacDatePresenter(urlDeleteVacDate, adapter, startDateDelete, activity1, user, kind, progressbar);
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
    public void sendData(String user, String kind, String userUpdate) {
        this.user = user;
        this.kind = kind;
        this.userUpdate = userUpdate;
        if (this.kind.equals("employer")) {

        }
    }

    public void resultSumView(String sum_vac_hour, String sum_vac_date) {
        if (!sum_vac_hour.equals("")) {
            textSumVacHourMessage.setVisibility(View.VISIBLE);
            textSumVacHour.setVisibility(View.VISIBLE);
            int workTime = Integer.parseInt(sum_vac_hour);
            sum_vac_hour = Share.changeTime(workTime);
            textSumVacHour.setText(sum_vac_hour);
        } else {
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.noLastVacHour), Toast.LENGTH_SHORT).show();
            textSumVacHour.setText(sum_vac_hour);
        }

        if (!sum_vac_date.equals("")) {
            textSumVacDateMessage.setVisibility(View.VISIBLE);
            textSumVacDate.setVisibility(View.VISIBLE);
            textSumVacDate.setText(sum_vac_date);
        } else {
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.noLastVacHour), Toast.LENGTH_SHORT).show();
            textSumVacDate.setText(sum_vac_date);
        }

    }

    public static void verifyStoragePermissions(android.app.Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

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
