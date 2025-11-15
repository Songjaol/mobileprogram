package com.example.sudoku;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    CustomButton Clikedbutton;
    CustomButton [][] buttons = new CustomButton[9][9];
    boolean gameFinished = false;
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

        Button resetbutton = (Button) findViewById(R.id.buttonreset);
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                // 새로 시작 (Intent 재실행)
                startActivity(getIntent());

                // 전환 애니메이션 제거 (선택 사항, 깜빡임 방지)
                overridePendingTransition(0, 0);
            }
        });


        TableLayout table =(TableLayout) findViewById(R.id.tableLayout);

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

                double r= Math.random();
                if(r<0.7) {
                    buttons[i][j].set(board.get(i, j));

                }
                else {
                    buttons[i][j].set(0);
                    buttons[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Clikedbutton= (CustomButton)v;
                            num.setVisibility(View.VISIBLE);
                            dimView.setVisibility(View.VISIBLE);

                        }
                    });
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

    public void changenum(View v)
    {
        Button button = (Button) v;
        TableLayout num = (TableLayout) findViewById(R.id.num);
        View dimView = findViewById(R.id.dimView);
        if(button==(Button) findViewById(R.id.button11))  //DEL버튼
        {
            Clikedbutton.set(0);

        }
        else if(button==(Button) findViewById(R.id.button10))  //CANCEL버튼
        {

        }
        else {
            Clikedbutton.set(Integer.parseInt(button.getText().toString())); //숫자패드

        }
        num.setVisibility(View.INVISIBLE);
        dimView.setVisibility(View.INVISIBLE);
        updateConflicts();
    }
    private void setConflict(CustomButton btn) {
        btn.setBackgroundColor(Color.RED);
    }
    private void unsetConflict(CustomButton btn) {
        btn.setBackgroundResource(R.drawable.button_selector);
    }
    private boolean checkConflict(int row, int col) {
        int val = buttons[row][col].getValue();
        if (val == 0) return false;

        // 같은 행
        for (int j = 0; j < 9; j++)
            if (j != col && buttons[row][j].getValue() == val)
                return true;

        // 같은 열
        for (int i = 0; i < 9; i++)
            if (i != row && buttons[i][col].getValue() == val)
                return true;

        // 같은 3x3 블록
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++)
            for (int j = startCol; j < startCol + 3; j++)
                if (!(i == row && j == col) && buttons[i][j].getValue() == val)
                    return true;

        return false;
    }
    private void updateConflicts() {
        boolean hasConflict = false;
        boolean allFilled = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int val = buttons[i][j].getValue();
                if (val == 0) allFilled = false;

                if (checkConflict(i, j)) {
                    hasConflict = true;
                    setConflict(buttons[i][j]);
                } else {
                    unsetConflict(buttons[i][j]);
                }
            }
        }

        if (!hasConflict && allFilled && !gameFinished) {
            gameFinished = true;
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    buttons[i][j].setClickable(false);

            Toast.makeText(this, "🎉 게임이 끝났습니다! 🎉", Toast.LENGTH_LONG).show();
        }
    }

}