package com.example.kamal.saatzanhamrah.VisitLastVacation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamal on 12/20/2017.
 */

public class LastVacationAdapter extends RecyclerView.Adapter<LastVacationAdapter.LastTimeViewHolder> {

    FragmentActivity activity;
    List<LastVacation> list = new ArrayList<>();
    public PassDataDelete passDataDelete;
    LastVacation lastTime = new LastVacation();
    String user, kind;
    LastVacationAdapter lastTimeAdapter;
    VisitLastVacationFragment visitLastDateFragment;
    LinearLayout linearLayout;
    TextView view_confirm;
    String vac_date = "";


    public LastVacationAdapter(VisitLastVacationFragment visitLastDateFragment, List<LastVacation> list, String user, String kind) {
        this.activity = visitLastDateFragment.getActivity();
        this.list = list;
        this.user = user;
        this.kind = kind;
        this.visitLastDateFragment = visitLastDateFragment;
    }

    public LastVacationAdapter(VisitLastVacationFragment visitLastDateFragment, List<LastVacation> list, String user, String kind, String vac_date) {
        this.activity = visitLastDateFragment.getActivity();
        this.list = list;
        this.user = user;
        this.kind = kind;
        this.visitLastDateFragment = visitLastDateFragment;
        this.vac_date = vac_date;
    }

    @Override
    public LastTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_last_time_adapter, parent, false);
        return new LastTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LastTimeViewHolder holder, final int position) {
        lastTime = list.get(position);
        holder.visitStartDateWork.setText(lastTime.getStartWorkDate());
        holder.visitStartTimeWork.setText(lastTime.getStartWorkTime());
        holder.visitEndDateWork.setText(lastTime.getEndWorkDate());
        holder.visitEndTimeWork.setText(lastTime.getEndWorkTime());
        holder.sumDate.setText(lastTime.getWorkTime());
        holder.visitStartDateWork.setTextColor(Color.BLUE);
        holder.visitStartTimeWork.setTextColor(Color.BLUE);
        holder.visitEndDateWork.setTextColor(Color.RED);
        holder.visitEndTimeWork.setTextColor(Color.RED);
        holder.sumDate.setTextColor(Color.BLACK);
        holder.checkBox.setTag(position);
        holder.checkBox.setClickable(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(false);
                } else if (!holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(true);
                }
                Toast.makeText(activity, "تایید کارکرد توسط کارفرمای شما تغییر می کند", Toast.LENGTH_LONG).show();
            }
        });
//        holder.checkBox.setEnabled(false);
//        if (flag == 0) {
//            holder.visitStartDateWork.setText(R.string.startTime);
//            holder.visitEndDateWork.setText(R.string.endTime);
//            holder.sumDate.setText(R.string.sumTitle);
//            flag=1;
//        }


        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.checkBox.isChecked()) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.noDelete), Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage(activity.getResources().getString(R.string.deleteMessage))
                            .setCancelable(true)
                            .setPositiveButton(activity.getString(R.string.deleteYes), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (Share.check(activity)) {
                                        passDataDelete = (PassDataDelete) visitLastDateFragment;
                                        if (vac_date.equals(""))
                                            passDataDelete.sendDataDelete(activity, position, LastVacationAdapter.this, holder.visitStartDateWork.getText().toString(), holder.visitStartTimeWork.getText().toString());
                                        else
                                            passDataDelete.sendDataDeleteVacDate(activity, position, LastVacationAdapter.this, holder.visitStartDateWork.getText().toString());

                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(activity, activity.getResources().getString(R.string.noInternet), Toast.LENGTH_LONG).show();
                                    }

                                }
                            })
                            .setNegativeButton(activity.getString(R.string.deleteNo), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
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
        CardView cardView;
        CheckBox checkBox;
        View view_confirm;
        LinearLayout linearLayoutAdapter;


        public LastTimeViewHolder(View itemView) {
            super(itemView);
            visitStartDateWork = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_startWorkDate);
            visitStartTimeWork = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_startWorkTime);
            visitEndDateWork = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_endWorkDate);
            visitEndTimeWork = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_endWorkTime);
            sumDate = (TextView) itemView.findViewById(R.id.textView_contentLastTimeAdapter_sumDate);
            checkBox = itemView.findViewById(R.id.confirm_employer);
            cardView = (CardView) itemView.findViewById(R.id.cardView_visitLastDate_layoutRow);
            view_confirm = itemView.findViewById(R.id.confirm_view);
            view_confirm.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
            linearLayoutAdapter = itemView.findViewById(R.id.linear_visitLastDate_layout);
            checkBox.setVisibility(View.GONE);
            view_confirm.setVisibility(View.GONE);
            linearLayoutAdapter.setWeightSum((float) 3.5);
        }
    }

    public interface PassDataDelete {
        public void sendDataDelete(FragmentActivity activity1, int position, LastVacationAdapter adapter, String startDate, String startTime);

        public void sendDataDeleteVacDate(FragmentActivity activity1, int position, LastVacationAdapter adapter, String startDate);
    }
}
