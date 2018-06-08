package com.example.kamal.saatzanhamrah.ForgotPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;


public class ForgotView extends FrameLayout implements View.OnClickListener {
    ForgotPresenter presenter;
    Context cotext;
    EditText editTextEmail;
    Button buttonSendEmail;
    String url="http://kamalroid.ir/sendEmail.php";

    public ForgotView(@NonNull Context context) {
        super(context);
        this.cotext=context;
        View view=inflate(context,R.layout.activity_forgot,this);
        editTextEmail=(EditText)view.findViewById(R.id.editText_forgot_email);
        buttonSendEmail=(Button)view.findViewById(R.id.button_forgot_sendEmail);
        buttonSendEmail.setOnClickListener(this);
    }

    public void setPresenter(ForgotPresenter forgotPresenter) {
        this.presenter=forgotPresenter;
    }

    @Override
    public void onClick(View v) {
        if(Share.check(cotext)) {
            presenter.sendEmailPresenter(url, editTextEmail.getText().toString());
        }
            else{
                Toast.makeText(cotext, getResources().getString(R.string.noInternet), Toast.LENGTH_LONG).show();
            }
    }

    public void resultEmailView(String result) {
        switch (result){
            case "Mail Sent Successfully":
                Toast.makeText(cotext, "رمز عبور به ایمیل فرستاده شد", Toast.LENGTH_LONG).show();
                break;
            case "Mail Not Sent":
                Toast.makeText(cotext, "ایمیل ثبت نشده است", Toast.LENGTH_LONG).show();
        }
    }
}
