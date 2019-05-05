package com.example.kamal.saatzanhamrah.Vacation;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;

import java.util.List;
import java.util.Map;

/**
 * Created by kamal on 12/11/2017.
 */

public class VacationPresenter {
    VacationModel model;

    VacationFragment view1;




    public VacationPresenter(VacationFragment view1) {
        this.view1=view1;
        model=new VacationModel(this,view1.getActivity());
    }

    public void presenterHandDate(String addEmployeeUrl, Map<String, String> params, ProgressBar progressbar, Button buttonRegisterHandDate) {
        model.hourVacation(addEmployeeUrl,params,progressbar,buttonRegisterHandDate);
    }


    public void resultHandDate(String success, String workTime) {
        view1.resultHandDate(success,workTime);
    }



  }
