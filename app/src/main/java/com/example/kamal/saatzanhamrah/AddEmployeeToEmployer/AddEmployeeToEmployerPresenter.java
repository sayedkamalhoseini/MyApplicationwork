package com.example.kamal.saatzanhamrah.AddEmployeeToEmployer;

/**
 * Created by kamal on 12/29/2017.
 */

public class AddEmployeeToEmployerPresenter {
    AddEmployeeToEmployerModel model;
    AddEmployeeToEmployerFragment view;


  public AddEmployeeToEmployerPresenter(AddEmployeeToEmployerFragment view){
      this.view=view;
      model=new AddEmployeeToEmployerModel(this,view.getActivity());
  }

    public void presenterAddEmployee(String editTextAddEmployeeToEmployer,String addEmployeeUrl,String user) {
        model.addEmployee(editTextAddEmployeeToEmployer,addEmployeeUrl,user);
    }

    public void resultAddEmployeePresenter(String result) {
        view.resultAddEmployeeView(result);
    }
}
