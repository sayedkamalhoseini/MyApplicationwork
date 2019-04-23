package com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

import java.util.List;

public class VisitEmployeeToEmployerFragment extends Fragment implements MainActivity.PassData{
    VisitEmployeePresenter presenter;
    Activity activity;
    private VisitEmployeeAdapter adapter;
    private RecyclerView recyclerView;
    private String url="http://kamalroid.ir/get_employee_to_employer1.php";
    private String user;
    private ProgressBar progressbar;
    private String userUpdate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter=new VisitEmployeePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_visit_employee_to_employer,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_vistEmployee_listEmployee);
        progressbar= (ProgressBar) view.findViewById(R.id.progressBar_visitEmployeeToEmployer_loading);
        if(Share.check(getContext())) {
            progressbar.setVisibility(View.VISIBLE);
            presenter.visitEmployeeToEmployer(url, user,progressbar);
        }  else{
            Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void passListVisitEmployeeView(List<VisitEmployee> list) {
        adapter = new VisitEmployeeAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void sendData(String user, String kind,String userUpdate) {
        this.user=user;
        this.userUpdate=userUpdate;
    }
}
