package com.example.kamal.saatzanhamrah.TimeEmploy;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;

import java.util.List;
import java.util.Map;

/**
 * Created by kamal on 12/11/2017.
 */

public class TimePresenter {
    TimeModel model;
    AutoDateFragment view;
    HandDateFragment view1;



    public TimePresenter(AutoDateFragment view) {
        this.view=view;
        model=new TimeModel(this,view.getActivity());
    }

    public TimePresenter(HandDateFragment view1) {
        this.view1=view1;
        model=new TimeModel(this,view1.getActivity());
    }


    public void startChangeDate(String timeEmployeeUrl, String user, String kind, ProgressBar progressBar, Button buttonStart, TextView textLastStartDate, TextView textLastStartTime) {
        model.startTimeRegister(timeEmployeeUrl,user,kind,progressBar,buttonStart,textLastStartDate,textLastStartTime);
    }

    public void presenterHandDate(String addEmployeeUrl, Map<String, String> params, ProgressBar progressbar, Button buttonRegisterHandDate) {
        model.handDate(addEmployeeUrl,params,progressbar,buttonRegisterHandDate);
    }



    public void passListPresenter(List<LastTime> lastTimeList) {
        view.passListView(lastTimeList);
    }

    public void messageStart(String result) {
        view.startRegisterTime(result);
    }

    public void messageEnd(String result) {
        view.endRegisterTime(result);
    }

    public void endChangeDate(String endTimeEmployeeUrl, String user, String kind, String text, ProgressBar progressBar, Button buttonEnd) {
        model.endTimeRegister(endTimeEmployeeUrl,user,kind,text,progressBar,buttonEnd);

    }

    public void resultHandDate(String success, String workTime) {
        view1.resultHandDate(success,workTime);
    }

    public void sendProblem(String problemUrl) {
        model.explain_problem(problemUrl);
    }


    public void resultProblem(String result) {
        view.resultProblem(result);
    }

  }
