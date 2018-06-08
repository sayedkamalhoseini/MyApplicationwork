package com.example.kamal.saatzanhamrah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutUsFragment extends Fragment {

    TextView textTitle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about_us, container, false);
        textTitle = (TextView) getActivity().findViewById(R.id.textView_toolbar_title);
        textTitle.setText(getString(R.string.aboutUs));
        return view;



    }

}
