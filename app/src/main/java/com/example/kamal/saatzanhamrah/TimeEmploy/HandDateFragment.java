package com.example.kamal.saatzanhamrah.TimeEmploy;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VerifyFragment;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.kamal.saatzanhamrah.Share.loadPref;

public class HandDateFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, MainActivity.PassData, MainActivity.EnableData {

    private EditText editTextHandDateStart, editTextHandDateEnd, explains;
    private Spinner spinnerHandTimeHourStart, spinnerHandTimeMinuteStart, spinnerHandTimeHourEnd, spinnerHandTimeMinuteEnd;
    private ImageButton imageButtonHandDateStart, imageButtonHandDateEnd;
    private int yearStart, monthStart, yearEnd, monthEnd, dayStart, dayEnd;
    private Button buttonRegisterHandDate;
    private String user, kind;
    private String mDay, mMonth, hourStart, minuteStart, hourEnd, minuteEnd, _miladiStart, _miladiEnd;
    private TimePresenter presenter;
    private String handDateUrl = "http://kamalroid.ir/hand_date_20190501.php";
    private Map<String, String> params;
    private TabLayout tabLayout;
    private TextView textTitle;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressbar;
    private boolean mIsPremium;
    private VerifyFragment verifyFragment;
    private Info_hand_date info_hand_date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new TimePresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hand_date, container, false);
        buttonRegisterHandDate = (Button) view.findViewById(R.id.button_time_registerHandDate);
        imageButtonHandDateStart = (ImageButton) view.findViewById(R.id.imageButton_time_setDateStart);
        imageButtonHandDateEnd = (ImageButton) view.findViewById(R.id.imageButton_time_setDateEnd);
        editTextHandDateStart = (EditText) view.findViewById(R.id.editText_time_setDateStart);
        editTextHandDateEnd = (EditText) view.findViewById(R.id.editText_time_setDateEnd);
        explains = (EditText) view.findViewById(R.id.editText_time_explain);
        spinnerHandTimeHourStart = (Spinner) view.findViewById(R.id.spinner_time_handTimeHourStart);
        spinnerHandTimeMinuteStart = (Spinner) view.findViewById(R.id.spinner_time_handTimeMinuteStart);
        spinnerHandTimeHourEnd = (Spinner) view.findViewById(R.id.spinner_time_handTimeHourEnd);
        spinnerHandTimeMinuteEnd = (Spinner) view.findViewById(R.id.spinner_time_handTimeMinuteEnd);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinate_time_handLayout);
        progressbar = (ProgressBar) view.findViewById(R.id.progressBar_time_loading);
        textTitle = (TextView) getActivity().findViewById(R.id.textView_toolbar_title);
        textTitle.setText(getString(R.string.handDate));
        imageButtonHandDateStart.setOnClickListener(this);
        imageButtonHandDateEnd.setOnClickListener(this);
        buttonRegisterHandDate.setOnClickListener(this);
        editTextHandDateStart.setOnClickListener(this);
        editTextHandDateEnd.setOnClickListener(this);
        spinnerHandTimeHourStart.setOnItemSelectedListener(this);
        spinnerHandTimeMinuteStart.setOnItemSelectedListener(this);
        spinnerHandTimeHourEnd.setOnItemSelectedListener(this);
        spinnerHandTimeMinuteEnd.setOnItemSelectedListener(this);
        Share.spinnerAdapter(getContext(), spinnerHandTimeHourStart, R.array.arrayStartHour);
        Share.spinnerAdapter(getContext(), spinnerHandTimeMinuteStart, R.array.arrayStartMinute);
        Share.spinnerAdapter(getContext(), spinnerHandTimeHourEnd, R.array.arrayEndHour);
        Share.spinnerAdapter(getContext(), spinnerHandTimeMinuteEnd, R.array.arrayEndMinute);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_time_registerHandDate:
                if (Share.check(getContext())) {
                        if (loadPref(getActivity(), "count").equals("1") || loadPref(getActivity(), "count").equals("2")) {
                            buttonRegisterHandDate.setEnabled(false);
                            progressbar.setVisibility(View.VISIBLE);
                            JalaliCalendar.gDate shamsiStart = new JalaliCalendar.gDate(yearStart, monthStart - 1, dayStart);
                            JalaliCalendar.gDate miladiStart = JalaliCalendar.jalaliToMiladi(shamsiStart);
                            _miladiStart = miladiStart.getYear() + "/" + (miladiStart.getMonth() + 1) + "/" + miladiStart.getDay();
                            JalaliCalendar.gDate shamsiEnd = new JalaliCalendar.gDate(yearEnd, monthEnd - 1, dayEnd);
                            JalaliCalendar.gDate miladiEnd = JalaliCalendar.jalaliToMiladi(shamsiEnd);
                            _miladiEnd = miladiEnd.getYear() + "/" + (miladiEnd.getMonth() + 1) + "/" + miladiEnd.getDay();
                            createParams();
                            if(hourStart.equals("ساعت شروع کار")|| hourEnd.equals("ساعت پایان کار") || minuteStart.equals("دقیقه شروع کار")|| minuteEnd.equals("دقیقه پایان کار")){
                                Toast.makeText(getContext(), R.string.fill_field, Toast.LENGTH_LONG).show();
                                buttonRegisterHandDate.setEnabled(true);
                                progressbar.setVisibility(View.GONE);

                            }else {
                                presenter.presenterHandDate(handDateUrl, params, progressbar, buttonRegisterHandDate);
                            }
                            break;
                        } else {
                            Share.showSnackBar(getContext(), coordinatorLayout, getResources().getString(R.string.enableMessage));
                            break;
                        }

                    }

                 else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.editText_time_setDateStart:
                calenderStart(editTextHandDateStart);
                break;
            case R.id.editText_time_setDateEnd:
                calenderEnd(editTextHandDateEnd);
                break;
            case R.id.imageButton_time_setDateStart:
                calenderStart(editTextHandDateStart);
                break;
            case R.id.imageButton_time_setDateEnd:
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

    public void calenderEnd(final EditText setDate) {
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
                                                                                 yearEnd = year;
                                                                                 monthEnd = monthOfYear;
                                                                                 dayEnd = dayOfMonth;

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
        params.put("dateEnd", editTextHandDateEnd.getText().toString());
        params.put("timeStart", hourStart + ":" + minuteStart);
        params.put("timeEnd", hourEnd + ":" + minuteEnd);
        params.put("miladiStart", _miladiStart);
        params.put("miladiEnd", _miladiEnd);
        params.put("explains", explains.getText().toString());
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_time_handTimeHourStart:
                hourStart = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_time_handTimeMinuteStart:
                minuteStart = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_time_handTimeHourEnd:
                hourEnd = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_time_handTimeMinuteEnd:
                minuteEnd = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void sendData(String user, String kind,String userUpdate) {
        this.user = user;
        this.kind = kind;

    }


    public void resultHandDate(String result, String workTime) {
        switch (result) {
            case "done":
                verifyFragment = new VerifyFragment();
                info_hand_date= (Info_hand_date) verifyFragment;
                info_hand_date.sendInfoHand(params,workTime);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_main_containerFragment, verifyFragment).commit();
                buttonRegisterHandDate.setEnabled(true);
                if (loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                } else if (loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "3");
                }
                if(Share.loadPref(getActivity(),"mIsPremium").equals("true"))
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
                Share.showSnackBar(getContext(), coordinatorLayout, getString(R.string.timeThereIs));
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
    public void sendEnable(boolean mIsPremium) {
        this.mIsPremium = mIsPremium;
    }

    public interface Info_hand_date{
        public void sendInfoHand(Map<String, String> params,String workTime);

    }
}
