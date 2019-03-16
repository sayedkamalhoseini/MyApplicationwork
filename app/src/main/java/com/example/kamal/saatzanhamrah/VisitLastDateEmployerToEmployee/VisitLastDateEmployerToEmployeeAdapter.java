package com.example.kamal.saatzanhamrah.VisitLastDateEmployerToEmployee;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

import java.util.List;

/**
 * Created by kamal on 12/20/2017.
 */

public class VisitLastDateEmployerToEmployeeAdapter extends RecyclerView.Adapter<VisitLastDateEmployerToEmployeeAdapter.LastTimeViewHolder> {

    FragmentActivity activity;
    List<LastTimeConfirm> list;

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }


    PassDataCheck passDataCheck;
    String user, kind;
    VisitLastDateEmployerToEmployeeFragment visitLastDateFragment;


    public VisitLastDateEmployerToEmployeeAdapter(VisitLastDateEmployerToEmployeeFragment visitLastDateFragment, List<LastTimeConfirm> list, String user, String kind) {
        this.activity = visitLastDateFragment.getActivity();
        this.list = list;
        this.user = user;
        this.kind = kind;
        this.visitLastDateFragment = visitLastDateFragment;
    }

    @Override
    public LastTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_last_time_adapter, parent, false);
        return new LastTimeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final LastTimeViewHolder holder, final int position) {
        final LastTimeConfirm lastTimeConfirm = list.get(position);
        holder.visitStartDateWork.setText(lastTimeConfirm.getStartWorkDate());
        holder.visitStartTimeWork.setText(lastTimeConfirm.getStartWorkTime());
        holder.visitEndDateWork.setText(lastTimeConfirm.getEndWorkDate());
        holder.visitEndTimeWork.setText(lastTimeConfirm.getEndWorkTime());
        holder.sumDate.setText(lastTimeConfirm.getWorkTime());
        holder.visitStartDateWork.setTextColor(Color.BLUE);
        holder.visitStartTimeWork.setTextColor(Color.BLUE);
        holder.visitEndDateWork.setTextColor(Color.RED);
        holder.visitEndTimeWork.setTextColor(Color.RED);
        holder.sumDate.setTextColor(Color.BLACK);
        holder.checkBox.setChecked(lastTimeConfirm.getSelected());
        holder.checkBox.setTag(position);


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Share.check(activity)) {
                    Integer pos=(Integer)holder.checkBox.getTag();
                    if (!list.get(pos).getSelected()) {
                        passDataCheck = (PassDataCheck) visitLastDateFragment;
                        passDataCheck.sendDataCheck(activity, VisitLastDateEmployerToEmployeeAdapter.this, holder.visitStartDateWork.getText().toString(), holder.visitStartTimeWork.getText().toString(), list.get(pos),pos,holder.checkBox, 1);

                    } else {
                        passDataCheck = (PassDataCheck) visitLastDateFragment;
                        passDataCheck.sendDataCheck(activity, VisitLastDateEmployerToEmployeeAdapter.this, holder.visitStartDateWork.getText().toString(), holder.visitStartTimeWork.getText().toString(), list.get(pos), pos,holder.checkBox,0);
                    }
                } else {
                    Toast.makeText(activity, activity.getResources().getString(R.string.noInternet), Toast.LENGTH_LONG).show();
                    if(holder.checkBox.isChecked()){
                        holder.checkBox.setChecked(false);
                    }else{
                        holder.checkBox.setChecked(true);
                    }

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LastTimeViewHolder extends RecyclerView.ViewHolder {
        TextView visitStartDateWork, visitStartTimeWork, visitEndDateWork, visitEndTimeWork, sumDate;
        RelativeLayout relativeLayout;
        CheckBox checkBox;

        public LastTimeViewHolder(View itemView) {
            super(itemView);
            visitStartDateWork = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_startWorkDate);
            visitStartTimeWork = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_startWorkTime);
            visitEndDateWork = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_endWorkDate);
            visitEndTimeWork = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_endWorkTime);
            sumDate = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_sumDate);
            checkBox = itemView.findViewById(R.id.confirm_employer);
        }

       }


    public interface PassDataCheck {
        public void sendDataCheck(FragmentActivity activity1, VisitLastDateEmployerToEmployeeAdapter adapter, String startDate, String startTime, LastTimeConfirm lastTimeConfirm,int pos,CheckBox checkBox, int check);
    }
}
