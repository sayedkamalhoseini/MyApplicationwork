package com.example.kamal.saatzanhamrah.VisitEmployerToEmployee;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.VisitLastDate.VisitLastDateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamal on 12/24/2017.
 */

public class VisitEmployerAdapter extends RecyclerView.Adapter<VisitEmployerAdapter.RecycleViewHolder> {
    FragmentActivity activity;
    List<VisitEmployer> list = new ArrayList<>();
    PassDataEmployeeToEmployer passDataEmployeeToEmployer;
    VisitLastDateFragment visitLastDateFragment;

    public VisitEmployerAdapter(FragmentActivity activity, List<VisitEmployer> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_visit_employer, parent, false);
        return new RecycleViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, int position) {
        VisitEmployer visitEmployer = new VisitEmployer();
        visitEmployer = list.get(position);
        holder.textViewUserNameEmployer.setText(visitEmployer.getUserNameEmployer());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameEmployer, textViewUserNameEmployer;
        RelativeLayout relativeLayout;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            textViewUserNameEmployer = (TextView) itemView.findViewById(R.id.textView_contentVisitEmployerAdapter_userName);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_visitEmployer_layoutRow);
        }
    }

    public interface PassDataEmployeeToEmployer {
        public void sendDataEmployeeToEmployer(String user, String kind);
    }
}
