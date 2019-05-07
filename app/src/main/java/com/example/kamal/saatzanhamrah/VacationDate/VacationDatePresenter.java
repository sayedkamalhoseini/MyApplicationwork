package com.example.kamal.saatzanhamrah.VacationDate;

import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Map;

/**
 * Created by kamal on 12/11/2017.
 */

public class VacationDatePresenter {
    VacationDateModel model;

    VacationDateFragment view1;




    public VacationDatePresenter(VacationDateFragment view1) {
        this.view1=view1;
        model=new VacationDateModel(this,view1.getActivity());
    }

    public void presenterHandDate(String addEmployeeUrl, Map<String, String> params, ProgressBar progressbar, Button buttonRegisterHandDate) {
        model.hourVacation(addEmployeeUrl,params,progressbar,buttonRegisterHandDate);
    }


    public void resultHandDate(String success) {
        view1.resultHandDate(success);
    }



  }
