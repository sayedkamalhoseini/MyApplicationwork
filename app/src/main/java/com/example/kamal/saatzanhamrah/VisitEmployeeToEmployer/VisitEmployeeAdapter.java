package com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.VisitLastDateEmployerToEmployee.VisitLastDateEmployerToEmployeeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamal on 12/24/2017.
 */

public class VisitEmployeeAdapter extends RecyclerView.Adapter<VisitEmployeeAdapter.RecycleViewHolder> {
    FragmentActivity activity;
    List<VisitEmployee> list = new ArrayList<>();
    PassDataEmployeeToEmployer passDataEmployeeToEmployer;
    VisitLastDateEmployerToEmployeeFragment visitLastDateFragment;
    String userUpdate,userMain;

    public VisitEmployeeAdapter(FragmentActivity activity, List<VisitEmployee> list) {
        this.activity = activity;
        this.list = list;
           }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_visit_employee, parent, false);
        return new RecycleViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, final int position) {
        VisitEmployee visitEmployee = new VisitEmployee();
        visitEmployee = list.get(position);
        holder.textViewUserNameEmployee.setText(visitEmployee.getUserNameEmployee());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitLastDateFragment = new VisitLastDateEmployerToEmployeeFragment();
                passDataEmployeeToEmployer = (PassDataEmployeeToEmployer) visitLastDateFragment;
                passDataEmployeeToEmployer.sendDataEmployeeToEmployer(list.get(position).getUserNameEmployeeMain(),list.get(position).getUserNameEmployee(), "employee");
                activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout_main_containerFragment, visitLastDateFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameEmployee, textViewUserNameEmployee;
        RelativeLayout relativeLayout;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            textViewUserNameEmployee = (TextView) itemView.findViewById(R.id.textView_contentVisitEmployeeAdapter_userName);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_visitEmployee_layoutRow);
        }
    }

    public interface PassDataEmployeeToEmployer {
        public void sendDataEmployeeToEmployer(String userMain, String userUpdate, String kind);
    }
}
