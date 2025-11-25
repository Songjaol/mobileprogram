package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CustomButton extends FrameLayout {
    private TextView[] memo = new TextView[9];
    private TextView textView;
    private int row;
    private int col;
    private int value;

    public CustomButton(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View memoview = layoutInflater.inflate(R.layout.layout_memo, null);
        addView(memoview);
        TableLayout table = memoview.findViewById(R.id.tableLayout);
        int k = 0;
        for (int i = 0; i < 3; i++) {
            TableRow rowView = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                memo[k] = (TextView) rowView.getChildAt(j);
                k++;
            }
        }
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

    public TextView[] getMemo() {
        return memo;
    }
}
