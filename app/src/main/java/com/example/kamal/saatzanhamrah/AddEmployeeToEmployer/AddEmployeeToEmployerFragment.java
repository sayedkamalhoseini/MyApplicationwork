package com.example.kamal.saatzanhamrah.AddEmployeeToEmployer;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

public class AddEmployeeToEmployerFragment extends Fragment implements View.OnClickListener, MainActivity.PassData {
    private EditText editTextAddEmployeeToEmployer;
    private Button buttonAddEmployeeToEmployer;
    private AddEmployeeToEmployerPresenter presenter;
    private String addEmployeeUrl = "http://kamalroid.ir/add_employee_to_employer.php";
    private String user;
    private TextView textTitle;
    private CoordinatorLayout coordinatorLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new AddEmployeeToEmployerPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_employee_to_employer, container, false);
        editTextAddEmployeeToEmployer = (EditText) view.findViewById(R.id.editText_fragmentAddEmployeeToEmployer_addEmployeeToEmployer);
        buttonAddEmployeeToEmployer = (Button) view.findViewById(R.id.button_fragmentAddEmployeeToEmployer_addEmployeeToEmployer);
        buttonAddEmployeeToEmployer.setOnClickListener(this);
        textTitle = (TextView) getActivity().findViewById(R.id.textView_toolbar_title);
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordinate_fragmentAddEmployeeToEmployer);
        textTitle.setText(getString(R.string.addEmployer));
        return view;
    }


    @Override
    public void onClick(View v) {
        presenter.presenterAddEmployee(editTextAddEmployeeToEmployer.getText().toString(), addEmployeeUrl, user);
    }

    public void resultAddEmployeeView(String result) {
        switch (result) {
            case "done":
                Share.showSnackBar(getActivity(),coordinatorLayout,getActivity().getResources().getString(R.string.addEmployerSuccessfully));
                break;
            case "fail":
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.errorAddEmployeeToEmployer), Toast.LENGTH_LONG).show();
                break;
            case "fail_in_connect":
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.registerError), Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void sendData(String user, String kind,String userUpdate) {
        this.user = user;
    }
}
