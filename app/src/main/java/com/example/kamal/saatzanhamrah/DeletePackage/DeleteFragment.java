package com.example.kamal.saatzanhamrah.DeletePackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTime;
import com.example.kamal.saatzanhamrah.VisitLastDate.LastTimeAdapter;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;


public class DeleteFragment extends Fragment implements View.OnClickListener , MainActivity.PassData{
    private ImageButton imageButtonDateDelete;
    private EditText editTextDateDelete;
    private Button buttonShowList;
    private RecyclerView recyclerView;
    private String mDay,mMonth,_Date,user,kind;
    private DeletePresenter presenter;
    private String url="http://kamalroid.ir/list_delete.php";
    private LastTimeAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new DeletePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        buttonShowList=(Button)view.findViewById(R.id.button_delete_showListDelete);
        imageButtonDateDelete=(ImageButton)view.findViewById(R.id.imageButton_delete_setDate);
        editTextDateDelete=(EditText)view.findViewById(R.id.editText_delete_setDate);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView_delete_listTime);
        imageButtonDateDelete.setOnClickListener(this);
        editTextDateDelete.setOnClickListener(this);
        buttonShowList.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton_delete_setDate:
                calender(editTextDateDelete);
                break;
            case R.id.editText_delete_setDate:
                calender(editTextDateDelete);
                break;
            case R.id.button_delete_showListDelete:
                presenter.getListDeletePrsenter(url,editTextDateDelete.getText().toString(),user,kind);
                break;
        }
    }

    public void calender(final EditText setDate) {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                             @Override
                                                                             public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                                                                 setDate.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
                                                                                 Toast.makeText(getActivity(), "" + year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                                                                             }
                                                                         }, now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay());

        datePickerDialog.setThemeDark(true);
        datePickerDialog.show(getActivity().getFragmentManager(), "tpd");
    }

    public void passListView(List<LastTime> deleteList) {
      //  adapter = new VisitLastDateEmployerToEmployeeAdapter(getActivity(),deleteList,user,kind );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void sendData(String user, String kind,String userUpdate) {
        this.user=user;
        this.kind=kind;
    }



}
