package com.example.kamal.saatzanhamrah.DeletePackage;

import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;

import java.util.List;


public class DeletePresenter {
    DeleteFragment view;
    DeleteModel model;
    public DeletePresenter(DeleteFragment deleteFragment) {
        this.view =deleteFragment;
        model=new DeleteModel(this,deleteFragment.getActivity());
    }

    public void getListDeletePrsenter(String url, String dateDelete, String user, String kind) {
        model.getListDeleteModel(url,dateDelete,user,kind);
    }

    public void passListPresenter(List<LastTime> deleteList) {
        view.passListView(deleteList);
    }
}
