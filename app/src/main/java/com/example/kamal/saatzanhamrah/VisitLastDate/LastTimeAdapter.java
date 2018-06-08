package com.example.kamal.saatzanhamrah.VisitLastDate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamal on 12/20/2017.
 */

public class LastTimeAdapter extends RecyclerView.Adapter<LastTimeAdapter.LastTimeViewHolder>{

    FragmentActivity activity;
    List<LastTime> list=new ArrayList<>();
    public PassDataDelete passDataDelete;
    LastTime lastTime=new LastTime();
    String user,kind;
    LastTimeAdapter lastTimeAdapter;
    VisitLastDateFragment visitLastDateFragment;


    public LastTimeAdapter(VisitLastDateFragment visitLastDateFragment, List<LastTime> list,String user,String kind) {
        this.activity = visitLastDateFragment.getActivity();
        this.list = list;
        this.user=user;
        this.kind=kind;
        this.visitLastDateFragment=visitLastDateFragment;
    }

    @Override
    public LastTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_last_time_adapter,parent,false);
        return new LastTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LastTimeViewHolder holder, final int position) {
        lastTime=list.get(position);
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
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(activity.getResources().getString(R.string.deleteMessage))
                        .setCancelable(true)
                        .setPositiveButton(activity.getString(R.string.deleteYes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(Share.check(activity)){
                                passDataDelete=(PassDataDelete)visitLastDateFragment;
                                passDataDelete.sendDataDelete(activity,position,LastTimeAdapter.this,holder.visitStartDateWork.getText().toString(),holder.visitStartTimeWork.getText().toString());
                                dialog.dismiss();}
                                else{
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
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LastTimeViewHolder extends RecyclerView.ViewHolder {
        TextView visitStartDateWork,visitStartTimeWork, visitEndDateWork,visitEndTimeWork,sumDate;
        RelativeLayout relativeLayout;

        public LastTimeViewHolder(View itemView) {
            super(itemView);
            visitStartDateWork =(TextView)itemView.findViewById(R.id.textView_contentLastTimeAdapter_startWorkDate);
            visitStartTimeWork =(TextView)itemView.findViewById(R.id.textView_contentLastTimeAdapter_startWorkTime);
            visitEndDateWork =(TextView)itemView.findViewById(R.id.textView_contentLastTimeAdapter_endWorkDate);
            visitEndTimeWork =(TextView)itemView.findViewById(R.id.textView_contentLastTimeAdapter_endWorkTime);
            sumDate=(TextView)itemView.findViewById(R.id.textView_contentLastTimeAdapter_sumDate);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relative_visitLastDate_layoutRow);
           }
    }

    public interface PassDataDelete {
        public void sendDataDelete(FragmentActivity activity1,int position,LastTimeAdapter adapter, String startDate, String startTime);
    }
}
