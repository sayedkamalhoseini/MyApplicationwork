package com.example.kamal.saatzanhamrah.VacationDate;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.TimeEmploy.JalaliCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.HashMap;
import java.util.Map;

import static com.example.kamal.saatzanhamrah.Share.loadPref;

public class VacationDateFragment extends Fragment implements View.OnClickListener, MainActivity.EnableData {

    private EditText editTextHandDateStart, explains;
    private ImageButton imageButtonHandDateStart;
    private int yearStart, monthStart, dayStart;
    private Button buttonRegisterHandDate;
    private String user, kind;
    private String mDay, mMonth, _miladiStart;
    private VacationDatePresenter presenter;
    private String handDateUrl = "http://kamalroid.ir/date_vacation.php";
    private Map<String, String> params;
    private TabLayout tabLayout;
    private TextView textTitle;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressbar;
    private boolean mIsPremium;
    private DateVerifyVacationFragment dateVerifyVacationFragment;
    private Info_vacation_hour info_vacation_hour;
    private String flag, url;
    private Activity activity;
    private String flagTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new VacationDatePresenter(this);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacation_date, container, false);
        buttonRegisterHandDate = (Button) view.findViewById(R.id.button_time_registerHandDateVacation);
        imageButtonHandDateStart = (ImageButton) view.findViewById(R.id.imageButton_time_setDateStartVacation);
        editTextHandDateStart = (EditText) view.findViewById(R.id.editText_time_setDateStartVacation_);
        explains = (EditText) view.findViewById(R.id.editText_time_explain);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinate_time_handLayout);
        progressbar = (ProgressBar) view.findViewById(R.id.progressBar_time_loading);
        textTitle = (TextView) getActivity().findViewById(R.id.textView_toolbar_title);
        textTitle.setText(getString(R.string.vacationDate));
        imageButtonHandDateStart.setOnClickListener(this);
        buttonRegisterHandDate.setOnClickListener(this);
        editTextHandDateStart.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_time_registerHandDateVacation:
                if (Share.check(getContext())) {
                    if (loadPref(getActivity(), "count").equals("1") || loadPref(getActivity(), "count").equals("2")) {
                        if(editTextHandDateStart.getText().toString().equals("")){
                            Toast.makeText(getActivity(), getResources().getString(R.string.insertVacation), Toast.LENGTH_SHORT).show();
                        }else {
                            buttonRegisterHandDate.setEnabled(false);
                            progressbar.setVisibility(View.VISIBLE);
                            JalaliCalendar.gDate shamsiStart = new JalaliCalendar.gDate(yearStart, monthStart - 1, dayStart);
                            JalaliCalendar.gDate miladiStart = JalaliCalendar.jalaliToMiladi(shamsiStart);
                            _miladiStart = miladiStart.getYear() + "/" + (miladiStart.getMonth() + 1) + "/" + miladiStart.getDay();
                            createParams();
                            presenter.presenterHandDate(handDateUrl, params, progressbar, buttonRegisterHandDate);
                        }
                        break;
                    } else {
                        Share.showSnackBar(getContext(), coordinatorLayout, getResources().getString(R.string.enableMessage));
                        break;
                    }

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.editText_time_setDateStartVacation_:
                calenderStart(editTextHandDateStart);
                break;
            case R.id.imageButton_time_setDateStartVacation:
                calenderStart(editTextHandDateStart);
                break;
        }

    }

    public void calenderStart(final EditText setDate) {
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
                                                                                 yearStart = year;
                                                                                 monthStart = monthOfYear;
                                                                                 dayStart = dayOfMonth;
                                                                                 setDate.setText(year + "/" + mMonth + "/" + mDay);

                                                                             }
                                                                         }, now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay());

        datePickerDialog.setThemeDark(true);
        datePickerDialog.show(getActivity().getFragmentManager(), "tpd");

    }


    private void createParams() {
        params = new HashMap<>();
        params.put("user", user);
        params.put("kind", kind);
        params.put("dateStart", editTextHandDateStart.getText().toString());
        params.put("miladiStart", _miladiStart);
        params.put("explains", explains.getText().toString());
    }


    public void resultHandDate(String result) {
        switch (result) {
            case "done":
                dateVerifyVacationFragment = new DateVerifyVacationFragment();
                info_vacation_hour = (Info_vacation_hour) dateVerifyVacationFragment;
                info_vacation_hour.sendInfoHand(params);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_main_containerFragment, dateVerifyVacationFragment).commit();
                buttonRegisterHandDate.setEnabled(true);
                if (loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                } else if (loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "3");
                }
                if (Share.loadPref(getActivity(), "mIsPremium").equals("true"))
                    Share.saveSharePref(getActivity(), "count", "1");
                break;
            case "failer_interesting_database":
                Share.showSnackBar(getContext(), coordinatorLayout, getString(R.string.registerNull));
                if (loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "1");
                } else if (loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                }
                buttonRegisterHandDate.setEnabled(true);
                break;
            case "failure_post":
                Share.showSnackBar(getContext(), coordinatorLayout, getString(R.string.registerError));
                if (loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "1");
                } else if (loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                }
                buttonRegisterHandDate.setEnabled(true);
                break;
            case "this time there is":
                Share.showSnackBar(getContext(), coordinatorLayout, getString(R.string.vacationDateThereIs));
                if (loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "1");
                } else if (loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                }
                buttonRegisterHandDate.setEnabled(true);
                break;
            default:
                Share.showSnackBar(getContext(), coordinatorLayout, getString(R.string.error));
                if (loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "1");
                } else if (loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                }
                buttonRegisterHandDate.setEnabled(true);
                break;
        }

    }

    @Override
    public void sendEnable(boolean mIsPremium, String user, String kind) {
        this.mIsPremium = mIsPremium;
        this.user = user;
        this.kind = kind;
    }


    public interface Info_vacation_hour {
        public void sendInfoHand(Map<String, String> params);

    }
}
