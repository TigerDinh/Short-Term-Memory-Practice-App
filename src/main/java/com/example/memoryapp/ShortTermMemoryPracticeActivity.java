package com.example.memoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class ShortTermMemoryPracticeActivity extends AppCompatActivity {

    private static final int DEFAULT_NUM_ROW = 5;
    private static final int DEFAULT_NUM_COLUMN = 5;
    private static final int MIN = 0;
    private static int chosenNumberOfOccurrences;
    private static long timeToRemember;
    private static ArrayList<Integer> chosenShapes;

    private ArrayList<Integer> randomPlacement;
    private ArrayList<Integer> exactShapeShown;

    public static Intent makeIntent(Context context, int desiredNumberOfOccurrences,
                                    long desiredTime, ArrayList<Integer> arrOfShapes) {
        chosenNumberOfOccurrences = desiredNumberOfOccurrences;
        timeToRemember = desiredTime;
        chosenShapes = arrOfShapes;
        return new Intent(context, ShortTermMemoryPracticeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_term_memory_practice);

        placeShapesRandomly();
        createBoard();
        startTimer();
    }

    private void placeShapesRandomly() {
        randomPlacement = new ArrayList<>();
        exactShapeShown = new ArrayList<>();
        ArrayList<Integer> positionArr = new ArrayList<>();

        for (int i = 0; i < DEFAULT_NUM_COLUMN*DEFAULT_NUM_ROW; i++){
            positionArr.add(i);
        }

        for (int i = 0; i < chosenNumberOfOccurrences; i++){
            Random newRandom = new Random();
            IntStream randomNumberAsIntStream = newRandom.ints(MIN, positionArr.size());
            int randomNumber = randomNumberAsIntStream.findFirst().getAsInt();

            randomPlacement.add(positionArr.get(randomNumber));
            positionArr.remove(randomNumber);
        }
    }

    private void createBoard() {
        int numOfRows = DEFAULT_NUM_ROW;
        int numOfColumns = DEFAULT_NUM_COLUMN;
        TableLayout board = findViewById(R.id.boardOfPlacement);

        for (int row = 0; row < numOfRows; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            board.addView(tableRow);

            for (int col = 0; col < numOfColumns; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                int position = FINAL_COL + (FINAL_ROW*numOfColumns);

                Button btn = new Button(this);
                btn.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                btn.setPadding(0, 0, 0, 0);
                btn.setBackgroundColor(Color.TRANSPARENT);
                btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.transparent));

                checkIfShapeIsHere(btn, position);
                tableRow.addView(btn);
            }
        }
    }

    private void checkIfShapeIsHere(Button btn, int position) {
        for (int i = 0; i < randomPlacement.size(); i++){
            if (position == randomPlacement.get(i)){
                placeShapeHere(btn);
                randomPlacement.remove(i);
                return;
            }
        }
        btn.setText("");
    }

    private void placeShapeHere(Button btn) {
        Random newRandom = new Random();
        IntStream randomNumberAsIntStream = newRandom.ints(MIN, chosenShapes.size());
        int shapeIndex = randomNumberAsIntStream.findFirst().getAsInt();
        int shape = chosenShapes.get(shapeIndex);

        if (shape == 1){
            btn.setText(getString(R.string.symbol_1));
        }
        else if (shape == 2){
            btn.setText(getString(R.string.symbol_2));
        }
        else if (shape == 3){
            btn.setText(getString(R.string.symbol_3));
        }
        else if (shape == 4){
            btn.setText(getString(R.string.symbol_4));
        }
        else if (shape == 5){
            btn.setText(getString(R.string.symbol_5));
        }
        else{
            btn.setText(getString(R.string.symbol_6));
        }

        exactShapeShown.add(shape);
        btn.setTextSize(30);
    }

    private void startTimer() {
        CountDownTimer timer = new CountDownTimer(timeToRemember, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent newIntent = TestUserMemoryActivity.makeIntent(ShortTermMemoryPracticeActivity.this,
                        chosenNumberOfOccurrences,
                        timeToRemember,
                        chosenShapes,
                        exactShapeShown
                );

                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(
                        ShortTermMemoryPracticeActivity.this,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                ).toBundle();

                startActivity(newIntent, bundle);
                finish();
            }
        };
        timer.start();
    }
}