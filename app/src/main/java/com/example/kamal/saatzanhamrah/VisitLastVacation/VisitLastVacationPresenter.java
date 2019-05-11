package com.example.kamal.saatzanhamrah.VisitLastVacation;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kamal on 12/18/2017.
 */

public class VisitLastVacationPresenter {
    VisitLastVacationFragment view;
    VisitLastVacationModel model;

    public VisitLastVacationPresenter(VisitLastVacationFragment view) {
        this.view=view;
        model=new VisitLastVacationModel(this,view.getActivity());
    }


    public void getLastDatePresenter(String url, String start, String end, String user, String kind, int row_start, ProgressBar progressbar, FloatingActionButton floatingActionButton) {
       }
 public void getLastDateAfterDeletePresenter(String url) {
       // model.getLastDateAfterdeleteModel(url,start,end,user,kind);
    }

    public void passListPresenter(List<LastVacation> lastTimeList,String vac_date) {
        view.passListView(lastTimeList,vac_date);
    }
  public void passListPresenterMore(List<LastVacation> lastTimeList) {
        view.passListViewMore(lastTimeList);
    }

    public void dataDeletePresenter(String url, LastVacationAdapter adapter, String startDateDelete, String startTimeDelete, FragmentActivity visitLastDateFragment, String user, String kind, ProgressBar progressbar) {
        model.dataDeleteModel(url,adapter,startDateDelete,startTimeDelete,visitLastDateFragment,progressbar);
    }

    public void deleteMessagePresenter(String result) {
        view.messageDeleteView(result);
    }


    public void getLastDatePresenterMore(String url, String start, String end, String user, String kind, int start_row, ProgressBar progressbar, FloatingActionButton floatingActionButton) {
        model.getLastDateModelMore(url,start,end,start_row,progressbar,floatingActionButton);

    }

    public void buildPdfPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar, CoordinatorLayout coordinatorLayout, TextView textSum) {
        model.buildPdfVacHour(url,start,end,user,kind,progressbar,coordinatorLayout,textSum);
    }

    public void sumPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar) {
        model.sumModel(url,start,end,user,kind,progressbar);
    }

    public void resulSumPresenter(String sum_vac_hour,String sum_vac_date) {
        view.resultSumView(sum_vac_hour,sum_vac_date);
    }

    public void buildExcelPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar, CoordinatorLayout coordinatorLayout, TextView textSum) {
        model.buildExcel(url,start,end,user,kind,progressbar,coordinatorLayout,textSum);

    }

    public void getLastVacHourPresenter(String url, String start, String end, String user, String kind, int row_start, ProgressBar progressbar, FloatingActionButton floatingActionButton) {
        model.getLastVacHourModel(url,start,end,user,kind,row_start,progressbar,floatingActionButton);
    }

    public void getLastVacDatePresenter(String url, String start, String end, String user, String kind, int row_start, ProgressBar progressbar, FloatingActionButton floatingActionButton) {
        model.getLastVacDateModel(url,start,end,user,kind,row_start,progressbar,floatingActionButton);
    }

    public void buildPdfVacDatePresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar, CoordinatorLayout coordinatorLayout, TextView textSum) {
        model.buildPdfVacDate(url,start,end,user,kind,progressbar,coordinatorLayout,textSum);
    }

    public void dataDeleteVacDatePresenter(String url, LastVacationAdapter adapter, String startDateDelete, FragmentActivity visitLastDateFragment, String user, String kind, ProgressBar progressbar) {
        model.dataDeleteVacDateModel(url,adapter,startDateDelete,visitLastDateFragment,progressbar);
    }

}
