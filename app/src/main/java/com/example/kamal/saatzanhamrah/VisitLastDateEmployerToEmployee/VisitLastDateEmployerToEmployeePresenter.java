package com.example.kamal.saatzanhamrah.VisitLastDateEmployerToEmployee;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kamal on 12/18/2017.
 */

public class VisitLastDateEmployerToEmployeePresenter {
    VisitLastDateEmployerToEmployeeFragment view;
    VisitLastDateEmployerToEmployeeModel model;

    public VisitLastDateEmployerToEmployeePresenter(VisitLastDateEmployerToEmployeeFragment view) {
        this.view=view;
        model=new VisitLastDateEmployerToEmployeeModel(this,view.getActivity());
    }


    public void getLastDatePresenter(String url, String start, String end, String user, String kind, int row_start, ProgressBar progressbar, FloatingActionButton floatingActionButton, String userUpdate) {
        model.getLastDateModel(url,start,end,user,kind,row_start,progressbar,floatingActionButton);
    }

    public void passListPresenter(List<LastTimeConfirm> lastTimeList) {
        view.passListView(lastTimeList);
    }
  public void passListPresenterMore(List<LastTimeConfirm> lastTimeList) {
        view.passListViewMore(lastTimeList);
    }

    public void dataCheckPresenter(String url, VisitLastDateEmployerToEmployeeAdapter adapter, String startDateDelete, String startTimeDelete, FragmentActivity visitLastDateFragment, String user, String kind, ProgressBar progressbar, int check) {
        model.dataCheckModel(url,adapter,startDateDelete,startTimeDelete,visitLastDateFragment,progressbar,check);
    }

    public void MessageConfirmPresenter(String result) {
        view.messageConfirm(result);
    }


    public void getLastDatePresenterMore(String url, String start, String end, String user, String kind, int start_row, ProgressBar progressbar, FloatingActionButton floatingActionButton) {
        model.getLastDateModelMore(url,start,end,start_row,progressbar,floatingActionButton);

    }


    public void buildPdfPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar, TextView textSum, String userUpdate) {
        model.buildPdf(url,start,end,user,kind,progressbar,textSum,userUpdate);
    }

    public void sumPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar) {
        model.sumModel(url,start,end,user,kind,progressbar);
    }
    public void buildExcelPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar, CoordinatorLayout coordinatorLayout, TextView textSum, String userUpdate) {
        model.buildExcel(url,start,end,user,kind,progressbar,coordinatorLayout,textSum,userUpdate);

    }


    public void resulSumPresenter(String result) {
        view.resultSumView(result);
    }

   }
