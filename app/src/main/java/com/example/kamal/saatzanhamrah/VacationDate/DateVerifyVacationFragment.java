package com.example.kamal.saatzanhamrah.VacationDate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kamal.saatzanhamrah.R;

import java.util.Map;


public class DateVerifyVacationFragment extends Fragment implements VacationDateFragment.Info_vacation_hour {
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
        View view = inflater.inflate(R.layout.fragment_verify_date_vacation, container, false);
        verifyHandStart = view.findViewById(R.id.verify_startHand);
        verifyHandStart.setTextColor(Color.BLUE);
        verifyHandStart.setText(params.get("dateStart"));
        return view;
    }

    @Override
    public void sendInfoHand(Map<String, String> params) {
        this.params = params;
    }
}
