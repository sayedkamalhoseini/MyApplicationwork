package com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer;

import java.util.List;

/**
 * Created by kamal on 12/24/2017.
 */

public class VisitEmployeePresenter {
    VisitEmployeeToEmployerFragment view;
    VisitEmployeeModel model;


    public VisitEmployeePresenter(VisitEmployeeToEmployerFragment view) {
        this.view=view;
        model=new VisitEmployeeModel(this,view.getActivity());
    }


    public void visitEmployeeToEmployer(String url, String userName) {
        model.visitEmployeeModel(url,userName);
    }

    public void passListVisitEmployeePresenter(List<VisitEmployee> list) {
        view.passListVisitEmployeeView(list);
    }
}
