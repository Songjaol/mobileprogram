package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CustomButton extends FrameLayout {
    private TextView textView;
    private int row;
    private int col;
    private int value;

    public CustomButton(Context context, int row, int col) {
        super(context);

        this.row = row;
        this.col = col;
        textView = new TextView(context);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        addView(textView);
        setClickable(true);
        setBackgroundResource(R.drawable.button_selector);
    }


    public void set(int a)
   {

       if (a == 0) {
           this.value=0;
           textView.setText("");
       } else if (a >= 1 && a <= 9) {
           this.value=a;
           textView.setText(String.valueOf(a));
       }

   }

    public TextView getTextView() {
        return textView;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

}
