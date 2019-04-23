package com.example.kamal.saatzanhamrah.VisitEmployerToEmployee;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

import java.util.List;

public class VisitEmployerToEmployeeFragment extends Fragment implements MainActivity.PassData {
    VisitEmployerPresenter presenter;
    Activity activity;
    private VisitEmployerAdapter adapter;
    private RecyclerView recyclerView;
    private String url="http://kamalroid.ir/get_employer_to_employee1.php";
    private String user;
    private TextView textTitle;
    private ProgressBar progressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter=new VisitEmployerPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_visit_employer_to_employee,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_visitEmployerToEmployee_listEmployer);
        progressbar= (ProgressBar) view.findViewById(R.id.progressBar_visitEmployerToEmployee_loading);
        textTitle=(TextView)getActivity().findViewById(R.id.textView_toolbar_title);
        textTitle.setText(getString(R.string.visitEmployer));
        if(Share.check(getContext())) {
            progressbar.setVisibility(View.VISIBLE);
            presenter.visitEmployerToEmployee(url, user,progressbar);
        }  else{
            Toast.makeText(getActivity(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void passListVisitEmployerView(List<VisitEmployer> list) {
        adapter = new VisitEmployerAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void sendData(String user, String kind,String userUpdate) {
        this.user=user;
    }
}
