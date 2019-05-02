package com.example.kamal.saatzanhamrah;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kamal.saatzanhamrah.TimeEmploy.HandDateFragment;

import java.util.Map;


public class VerifyFragment extends Fragment implements HandDateFragment.Info_hand_date {
    private Map<String, String> params;
    private String sumTime ;
    private TextView verifyHandStart, verifyHandEnd, verifyHandSum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify, container, false);
        verifyHandStart = view.findViewById(R.id.verify_startHand);
        verifyHandEnd = view.findViewById(R.id.verify_endHand);
        verifyHandSum = view.findViewById(R.id.verify_sumHand);
        verifyHandStart.setTextColor(Color.BLUE);
        verifyHandEnd.setTextColor(Color.RED);
        verifyHandStart.setText(params.get("dateStart")+"\t"+params.get("timeStart"));
        verifyHandEnd.setText(params.get("dateEnd")+"\t"+params.get("timeEnd"));
        verifyHandSum.setText(sumTime);
        return view;
    }

    @Override
    public void sendInfoHand(Map<String, String> params, String _workTime) {
        this.params = params;
        int workTime = Integer.parseInt(_workTime);
        String _workTimeHour = workTime / 3600 + "";
        String _workTimeMinute = workTime % 3600 / 60 + "";
        String _workTimeSecond = workTime % 3600 % 60 + "";
        sumTime = _workTimeHour + ":" + _workTimeMinute + ":" + _workTimeSecond;
    }
}
