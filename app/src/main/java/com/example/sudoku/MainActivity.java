package com.example.sudoku;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TableLayout num = (TableLayout) findViewById(R.id.num);
        View dimView = findViewById(R.id.dimView);
        num.setVisibility(View.INVISIBLE);
        dimView.setVisibility(View.INVISIBLE);

        TableLayout table =(TableLayout) findViewById(R.id.tableLayout);
        CustomButton [][] buttons = new CustomButton[9][9];
        BoardGenerator board = new BoardGenerator();
        for(int i=0;i<9;i++) {
            TableRow tableRow = new TableRow(this);
            TableLayout.LayoutParams rowParams =
                    new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            0,
                            1f
                    );
            if(i==2||i==5) {
                rowParams.setMargins(4, 4, 4, 40);
            }
            else {
                rowParams.setMargins(4, 4, 4, 4);
            }
            tableRow.setLayoutParams(rowParams);

            for(int j=0;j<9;j++)
            {
                buttons[i][j]= new CustomButton(this,i,j);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        num.setVisibility(View.VISIBLE);
                        dimView.setVisibility(View.VISIBLE);

                    }
                });
                double r= Math.random();
                if(r<0.7) {
                    buttons[i][j].set(board.get(i, j));
                }
                else {
                    buttons[i][j].set(0);
                }
                TableRow.LayoutParams layoutParams =
                        new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                if(j==2||j==5) {
                    layoutParams.setMargins(4, 4, 40, 4);
                }
                else {
                    layoutParams.setMargins(4, 4, 4, 4);
                }

                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);
            }
            table.addView(tableRow);
        }
    }
}