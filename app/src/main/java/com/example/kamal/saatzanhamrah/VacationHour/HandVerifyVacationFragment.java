package com.example.kamal.saatzanhamrah.VacationHour;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

import java.util.Map;


public class HandVerifyVacationFragment extends Fragment implements VacationFragment.Info_vacation_hour {
    private Map<String, String> params;
    private String sumTime ;
    private TextView verifyHandStart, verifyHandEnd, verifyHandSum;
    private String mHour,mMinute,mSecond;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_hour_vacation, container, false);
        verifyHandStart = view.findViewById(R.id.verify_startHand);
        verifyHandEnd = view.findViewById(R.id.verify_endHand);
        verifyHandSum = view.findViewById(R.id.verify_sumHand);
        verifyHandStart.setTextColor(Color.BLUE);
        verifyHandEnd.setTextColor(Color.RED);
        verifyHandStart.setText(params.get("timeStart")+"   "+params.get("dateStart"));
        verifyHandEnd.setText(params.get("timeEnd")+"   "+params.get("dateEnd"));
        verifyHandSum.setText(sumTime);
        return view;
    }

    @Override
    public void sendInfoHand(Map<String, String> params, String _workTime) {
        this.params = params;
        int workTime = Integer.parseInt(_workTime);

        sumTime = Share.changeTime(workTime);
    }
}
