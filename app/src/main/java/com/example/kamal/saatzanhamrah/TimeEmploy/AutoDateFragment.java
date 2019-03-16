package com.example.kamal.saatzanhamrah.TimeEmploy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;
import com.example.kamal.saatzanhamrah.VisitLastDate.VisitLastDateFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.kamal.saatzanhamrah.Share.loadPref;

public class AutoDateFragment extends Fragment implements View.OnClickListener, MainActivity.PassData, MainActivity.EnableData {
    Activity activity;
    String startTimeEmployeeUrl = "http://www.kamalroid.ir/time_register.php";
    String endTimeEmployeeUrl = "http://www.kamalroid.ir/end_time_register.php";
    String showLastRegisterUrl = "http://kamalroid.ir/last_time.php";
    String problemUrl = "http://www.kamalroid.ir/explain_problem.php";
    private Button buttonStart;
    private String user, kind, getUser, getKind;
    TimePresenter presenter;
    LinearLayout linearLayoutHandDate, linearLayoutTitle;
    RelativeLayout relativeLayoutAutoDate;
    private AutoTimeAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressDialog pd;
    Toolbar toolbar;
    Animation slide_left_in;
    Animation slide_right_out;
    private TextView textUserName, textKind, textStatus, textLastStartDate, textLastStartTime, textTitle;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private List<LastTime> lastTimeList = new ArrayList<>();
    private LastTime lastTime = new LastTime();
    private EditText editText;
    private boolean mIsPremium;
    private TextView problemTextView;
    private TextInputLayout textInputLayoutExplain;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TimePresenter(this);
        slide_left_in = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_left_in);
        slide_right_out = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_right_out);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_autotime, container, false);
        buttonStart = (Button) view.findViewById(R.id.Button_time_startWork);
        linearLayoutHandDate = (LinearLayout) view.findViewById(R.id.linear_time_handDate);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        textUserName = (TextView) view.findViewById(R.id.textView_autoTime_userName);
        textKind = (TextView) view.findViewById(R.id.textView_autoTime_kind);
        textStatus = (TextView) view.findViewById(R.id.textView_autoTime_status);
        textLastStartDate = (TextView) view.findViewById(R.id.textView_autoTime_startDate);
        textLastStartTime = (TextView) view.findViewById(R.id.textView_autoTime_startTime);
        linearLayoutTitle = (LinearLayout) view.findViewById(R.id.linear_autoTime_titleLastTime);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_autoTime_loading);
        editText = (EditText) view.findViewById(R.id.editText_time_explain);
        problemTextView = (TextView) view.findViewById(R.id.problemExplain);
        textTitle = (TextView) getActivity().findViewById(R.id.textView_toolbar_title);
        textInputLayoutExplain= (TextInputLayout) view.findViewById(R.id.textInput_time_explain);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinate_autoTime_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_autotime_ListVisit);
        textTitle.setText(getString(R.string.autoDate));
        textUserName.setText(user);
        presenter.sendProblem(problemUrl);
        if (kind.equals("employee"))
            textKind.setText("کارگر/کارمند");
        else if (kind.equals("employer"))
            textKind.setText("کارفرما");

        if (loadPref(getActivity(), "start" + user).equals("false")) {
            recyclerView.setVisibility(View.GONE);
            linearLayoutTitle.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.yellow));
            buttonStart.setText(getString(R.string.endDate));
            textInputLayoutExplain.setVisibility(View.VISIBLE);
            buttonStart.setBackgroundResource(R.drawable.yellowcircle);
            textUserName.setText(user);
            if (kind.equals("employee"))
                textKind.setText("کارگر/کارمند");
            else if (kind.equals("employer"))
                textKind.setText("کارفرما");
            textStatus.setText(getString(R.string.working));
            lastTime = new LastTime();
            lastTimeList = new ArrayList<>();
            textLastStartDate.setText(Share.loadPref(getActivity(), "startLastDate" + user));
            textLastStartTime.setText(Share.loadPref(getActivity(), "startLastTime" + user));

        } else if (loadPref(getActivity(), "end" + user).equals("false")) {
            recyclerView.setVisibility(View.GONE);
            linearLayoutTitle.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green_500));
            buttonStart.setText(getString(R.string.startDate));
            buttonStart.setBackgroundResource(R.drawable.bluecircle);
            textUserName.setText(user);
            if (kind.equals("employee"))
                textKind.setText("کارگر/کارمند");
            else if (kind.equals("employer"))
                textKind.setText("کارفرما");
            textStatus.setText(getString(R.string.resting));
            lastTime = new LastTime();
            lastTimeList = new ArrayList<>();
            textLastStartDate.setText("");
            textLastStartTime.setText("");
            lastTime.setStartWorkDate(Share.loadPref(getActivity(), "startLastDate" + user));
            lastTime.setStartWorkTime(Share.loadPref(getActivity(), "startLastTime" + user));
        } else {
            Share.saveSharePref(getContext(), "end" + user, "false");
            Share.saveSharePref(getContext(), "start" + user, "true");
        }
        buttonStart.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_time_startWork:
                if (Share.check(getContext())) {
                    if (Share.loadPref(getActivity(), "count").equals("1") || Share.loadPref(getActivity(), "count").equals("2")) {
                        if (loadPref(getActivity(), "start" + user).equals("true")) {
                            progressBar.setVisibility(View.VISIBLE);
                            buttonStart.setEnabled(false);
                            buttonStart.setText("درحال ثبت زمان شروع");
                            presenter.startChangeDate(startTimeEmployeeUrl, user, kind, progressBar, buttonStart, textLastStartDate, textLastStartTime);
                            break;
                        } else if (loadPref(getActivity(), "end" + user).equals("true")) {
                            progressBar.setVisibility(View.VISIBLE);
                            buttonStart.setEnabled(false);
                            buttonStart.setText("درحال ثبت زمان پایان");
                            presenter.endChangeDate(endTimeEmployeeUrl, user, kind, editText.getText().toString(), progressBar, buttonStart);
                            break;
                        }
                    } else {
                        Share.showSnackBar(getContext(), coordinatorLayout, getResources().getString(R.string.enableMessage));
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_LONG).show();
                    break;
                }
        }

    }

    public void startRegisterTime(String result) {
        switch (result) {
            case "done":
                Share.saveSharePref(getContext(), "start" + user, "false");
                Share.saveSharePref(getContext(), "end" + user, "true");
                recyclerView.setVisibility(View.GONE);
                linearLayoutTitle.setVisibility(View.GONE);
                Share.showSnackBar(getActivity(), coordinatorLayout, getString(R.string.startRegister));
                editText.setVisibility(View.VISIBLE);
                textInputLayoutExplain.setVisibility(View.VISIBLE);
                buttonStart.startAnimation(slide_right_out);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonStart.startAnimation(slide_left_in);
                        buttonStart.setText(getString(R.string.endDate));
                        buttonStart.setEnabled(true);
                        buttonStart.setBackgroundResource(R.drawable.yellowcircle);
                        textStatus.setText(getString(R.string.working));
                        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellow));

                    }
                }, 300);
                break;
            case "failure_post":
                Share.showSnackBar(getActivity(), coordinatorLayout, getString(R.string.errorInternet));
                if (Share.loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "1");
                } else if (Share.loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                }
                if (loadPref(getActivity(), "start" + user).equals("false")) {
                    buttonStart.setEnabled(true);
                    buttonStart.setText("ثبت زمان شروع");
                } else if (loadPref(getActivity(), "end" + user).equals("false")) {
                    buttonStart.setEnabled(true);
                    buttonStart.setText("ثبت زمان شروع");
                }
                break;
            default:
                Share.showSnackBar(getActivity(), coordinatorLayout, getString(R.string.errorInternet));
                if (Share.loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "1");
                } else if (Share.loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                }
                if (loadPref(getActivity(), "start" + user).equals("false")) {
                    buttonStart.setEnabled(true);
                    buttonStart.setText("ثبت زمان شروع");
                } else if (loadPref(getActivity(), "end" + user).equals("false")) {
                    buttonStart.setEnabled(true);
                    buttonStart.setText("ثبت زمان شروع");
                }

                break;
        }

    }

    public void endRegisterTime(String result) {
        switch (result) {
            case "done":
                Share.saveSharePref(getContext(), "end" + user, "false");
                Share.saveSharePref(getContext(), "start" + user, "true");
                Share.showSnackBar(getActivity(), coordinatorLayout, getString(R.string.endRegister));
                if (Share.loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                } else if (Share.loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "3");
                }
                if (Share.loadPref(getActivity(), "mIsPremium").equals("true"))
                    Share.saveSharePref(getActivity(), "count", "1");
                recyclerView.setVisibility(View.VISIBLE);
                linearLayoutTitle.setVisibility(View.VISIBLE);
                textLastStartDate.setText("");
                textLastStartTime.setText("");
                editText.setVisibility(View.GONE);
                textInputLayoutExplain.setVisibility(View.GONE);
                buttonStart.startAnimation(slide_right_out);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonStart.startAnimation(slide_left_in);
                        buttonStart.setBackgroundResource(R.drawable.bluecircle);
                        buttonStart.setEnabled(true);
                        buttonStart.setText(getString(R.string.startDate));
                        textStatus.setText(getString(R.string.resting));
                        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_500));
                        Share.saveSharePref(getActivity(), "startLastDate", "");
                        Share.saveSharePref(getActivity(), "startLastTime", "");
                    }
                }, 300);
                break;
            case "failure_post":
                Share.showSnackBar(getActivity(), coordinatorLayout, getString(R.string.errorInternet));
                if (Share.loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "1");
                } else if (Share.loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                }
                if (loadPref(getActivity(), "start" + user).equals("false")) {
                    buttonStart.setEnabled(true);
                    buttonStart.setText("ثبت زمان پایان");
                } else if (loadPref(getActivity(), "end" + user).equals("false")) {
                    buttonStart.setEnabled(true);
                    buttonStart.setText("ثبت زمان پایان");
                }

                break;
            default:
                Share.showSnackBar(getActivity(), coordinatorLayout, getString(R.string.errorInternet));
                if (Share.loadPref(getActivity(), "count").equals("1")) {
                    Share.saveSharePref(getActivity(), "count", "1");
                } else if (Share.loadPref(getActivity(), "count").equals("2")) {
                    Share.saveSharePref(getActivity(), "count", "2");
                }
                if (loadPref(getActivity(), "start" + user).equals("false")) {
                    buttonStart.setEnabled(true);
                    buttonStart.setText("ثبت زمان پایان");
                } else if (loadPref(getActivity(), "end" + user).equals("false")) {
                    buttonStart.setEnabled(true);
                    buttonStart.setText("ثبت زمان پایان");
                }
                break;
        }

    }


    public void passListView(List<LastTime> lastTimeList) {
        VisitLastDateFragment visitLastDateFragment = new VisitLastDateFragment();
        adapter = new AutoTimeAdapter(visitLastDateFragment, lastTimeList, user, kind);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void sendData(String user, String kind) {
        this.user = user;
        this.kind = kind;
    }

    @Override
    public void sendEnable(boolean mIsPremium) {
        this.mIsPremium = mIsPremium;
    }

    public void resultProblem(String result) {
        problemTextView.setText(result);
        recyclerView.setVisibility(View.GONE);
    }
}




