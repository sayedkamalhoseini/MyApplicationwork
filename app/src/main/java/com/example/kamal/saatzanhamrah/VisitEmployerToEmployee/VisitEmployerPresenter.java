package com.example.kamal.saatzanhamrah.VisitEmployerToEmployee;

import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by kamal on 12/24/2017.
 */

public class VisitEmployerPresenter {
    VisitEmployerToEmployeeFragment view;
    VisitEmployerModel model;


    public VisitEmployerPresenter(VisitEmployerToEmployeeFragment view) {
        this.view=view;
        model=new VisitEmployerModel(this,view.getActivity());
    }


    public void visitEmployerToEmployee(String url, String userName, ProgressBar progressbar) {
        model.visitEmployerModel(url,userName,progressbar);
    }

    public void passListVisitEmployerPresenter(List<VisitEmployer> list) {
        view.passListVisitEmployerView(list);
    }
}
