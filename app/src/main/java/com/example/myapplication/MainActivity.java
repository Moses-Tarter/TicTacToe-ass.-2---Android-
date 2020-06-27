package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int turn = 0;
    int playCount = 0;
    int[][] matrix = new int[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrix = new int[3][3];
        playCount = 0;
        setTurn();
        listen();
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn() {
        ImageView board = (ImageView)findViewById(R.id.main_console);
        if(turn == 1) {
            turn = 0;
            board.setImageResource(R.drawable.oplay);
        }
        else {
            turn = 1;
            board.setImageResource(R.drawable.xplay);
        }
    }

    //sets the listeners for the button matrix
    public void listen() {
        for(int i=1; i<=9; i++) {
            int id = getResources().getIdentifier("main_btn_"+i, "id", getPackageName());
            final ImageButton test = (ImageButton) findViewById(id);
            final int finalI = i;
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkForWin() == false) {
                        if (getTurn() == 1) {
                            test.setImageResource(R.drawable.x);
                        } else {
                            test.setImageResource(R.drawable.o);
                        }
                        test.setClickable(false);
                        playCount++;
                        setTurn();
                        track(finalI);
                        checkForWin();
                    }
                }
            });
        }
    }

    //tracks the clicks positions in a matrix
    private void track(int index) {
        switch (index) {
            case 1 : if(getTurn() == 1) {matrix[0][0] = 1;} else {matrix[0][0] = 2;} break;
            case 2 : if(getTurn() == 1) {matrix[0][1] = 1;} else {matrix[0][1] = 2;} break;
            case 3 : if(getTurn() == 1) {matrix[0][2] = 1;} else {matrix[0][2] = 2;} break;
            case 4 : if(getTurn() == 1) {matrix[1][0] = 1;} else {matrix[1][0] = 2;} break;
            case 5 : if(getTurn() == 1) {matrix[1][1] = 1;} else {matrix[1][1] = 2;} break;
            case 6 : if(getTurn() == 1) {matrix[1][2] = 1;} else {matrix[1][2] = 2;} break;
            case 7 : if(getTurn() == 1) {matrix[2][0] = 1;} else {matrix[2][0] = 2;} break;
            case 8 : if(getTurn() == 1) {matrix[2][1] = 1;} else {matrix[2][1] = 2;} break;
            case 9 : if(getTurn() == 1) {matrix[2][2] = 1;} else {matrix[2][2] = 2;} break;
        }
    }

    //iterate clicked status matrix to check for a win/lose/tie
    private boolean checkForWin() {
        if((matrix[0][0] != 0 && matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2])) {
            gameOverFlow(1, "mark");
            return true;
        }
        if(matrix[0][2] != 0 && matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0]) {
            gameOverFlow(2, "mark");
            return true;
        }
        for(int i=0; i<3; i++) {
            if(matrix[i][0] != 0 && matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2]) {
                gameOverFlow(i, "row");
                return true;
            }
            if(matrix[0][i] != 0 && matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i]) {
                gameOverFlow(i,"col");
                return true;
            }
        }
        if(playCount == 9)
            gameOverFlow(0,"end");
        return false;

    }

    private void gameOverFlow(int index, String rowOrCol) {
        ImageView console = (ImageView) findViewById(R.id.main_console);
        if(!(rowOrCol.compareTo("end") == 0)) {
            int id = getResources().getIdentifier(rowOrCol + index, "drawable", getPackageName());
            ImageView winningRow = (ImageView) findViewById(R.id.main_win);
            winningRow.setImageResource(id);
            if (getTurn() == 1)
                console.setImageResource(R.drawable.owin);
            else
                console.setImageResource(R.drawable.xwin);
        }
        if(playCount == 9 && rowOrCol.compareTo("end") == 0) {
            console.setImageResource(R.drawable.nowin);
        }
        finish();
        startActivity(getIntent());
    }
}