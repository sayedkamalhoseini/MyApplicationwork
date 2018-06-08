package com.example.kamal.saatzanhamrah;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by kamal on 2/3/2018.
 */

public class CustumButton extends android.support.v7.widget.AppCompatButton {
    public CustumButton(Context context) {
        super(context);
    }

    public CustumButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/iransans.ttf");
        this.setTypeface(face);

    }

    public CustumButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/iransans.ttf");
        this.setTypeface(face);

    }

}
