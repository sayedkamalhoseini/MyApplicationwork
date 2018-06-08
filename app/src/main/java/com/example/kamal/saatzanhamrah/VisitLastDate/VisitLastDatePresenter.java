package com.example.kamal.saatzanhamrah.VisitLastDate;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kamal on 12/18/2017.
 */

public class VisitLastDatePresenter{
    VisitLastDateFragment view;
    VisitLastDateModel model;

    public VisitLastDatePresenter(VisitLastDateFragment view) {
        this.view=view;
        model=new VisitLastDateModel(this,view.getActivity());
    }


    public void getLastDatePresenter(String url, String start, String end, String user, String kind, int row_start, ProgressBar progressbar, FloatingActionButton floatingActionButton) {
        model.getLastDateModel(url,start,end,user,kind,row_start,progressbar,floatingActionButton);
    }
 public void getLastDateAfterDeletePresenter(String url) {
       // model.getLastDateAfterdeleteModel(url,start,end,user,kind);
    }

    public void passListPresenter(List<LastTime> lastTimeList) {
        view.passListView(lastTimeList);
    }
  public void passListPresenterMore(List<LastTime> lastTimeList) {
        view.passListViewMore(lastTimeList);
    }

    public void dataDeletePresenter(String url, LastTimeAdapter adapter, String startDateDelete, String startTimeDelete, FragmentActivity visitLastDateFragment, String user, String kind, ProgressBar progressbar) {
        model.dataDeleteModel(url,adapter,startDateDelete,startTimeDelete,visitLastDateFragment,progressbar);
    }

    public void deleteMessagePresenter(String result) {
        view.messageDeleteView(result);
    }


    public void getLastDatePresenterMore(String url, String start, String end, String user, String kind, int start_row, ProgressBar progressbar, FloatingActionButton floatingActionButton) {
        model.getLastDateModelMore(url,start,end,start_row,progressbar,floatingActionButton);

    }

    public void buildPdfPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar, CoordinatorLayout coordinatorLayout, TextView textSum) {
        model.buildPdf(url,start,end,user,kind,progressbar,coordinatorLayout,textSum);
    }

    public void sumPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar) {
        model.sumModel(url,start,end,user,kind,progressbar);
    }

    public void resulSumPresenter(String result) {
        view.resultSumView(result);
    }

    public void buildExcelPresenter(String url, String start, String end, String user, String kind, ProgressBar progressbar, CoordinatorLayout coordinatorLayout, TextView textSum) {
        model.buildExcel(url,start,end,user,kind,progressbar,coordinatorLayout,textSum);

    }
}
