package com.example.memoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity {

    private static int chosenNumberOfOccurrences;
    private static long timeToRemember;
    private static ArrayList<Integer> chosenShapes;
    private static ArrayList<Integer> exactShapeShown;
    private static ArrayList<Integer> userAnswer;

    private int numOfShape1;
    private int numOfShape2;
    private int numOfShape3;
    private int numOfShape4;
    private int numOfShape5;
    private int numOfShape6;

    public static Intent makeIntent(Context context, int desiredNumberOfOccurrences,
                                    long desiredTime, ArrayList<Integer> arrOfShapes,
                                    ArrayList<Integer> exactAnswer, ArrayList<Integer> userAnswers
    ) {
        chosenNumberOfOccurrences = desiredNumberOfOccurrences;
        timeToRemember = desiredTime;
        chosenShapes = arrOfShapes;
        exactShapeShown = exactAnswer;
        userAnswer = userAnswers;

        return new Intent(context, AnswerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        checkUserAnswers();
        setupRetryButton();
        setupMainMenuBtn();
    }

    private void checkUserAnswers() {
        TextView result = findViewById(R.id.txtResult);
        TextView answer1 = findViewById(R.id.txtAnswer1);
        TextView answer2 = findViewById(R.id.txtAnswer2);
        TextView answer3 = findViewById(R.id.txtAnswer3);
        TextView answer4 = findViewById(R.id.txtAnswer4);
        TextView answer5 = findViewById(R.id.txtAnswer5);
        TextView answer6 = findViewById(R.id.txtAnswer6);

        countExactShapeShown();

        String congratulationMsg = "Your score: " + checkAccuracy() + "/" + exactShapeShown.size();
        String result1 = "" + userAnswer.get(0) + "/" + numOfShape1;
        String result2 = "" + userAnswer.get(1) + "/" + numOfShape2;
        String result3 = "" + userAnswer.get(2) + "/" + numOfShape3;
        String result4 = "" + userAnswer.get(3) + "/" + numOfShape4;
        String result5 = "" + userAnswer.get(4) + "/" + numOfShape5;
        String result6 = "" + userAnswer.get(5) + "/" + numOfShape6;

        result.setText(congratulationMsg);
        answer1.setText(result1);
        answer2.setText(result2);
        answer3.setText(result3);
        answer4.setText(result4);
        answer5.setText(result5);
        answer6.setText(result6);
    }

    private void setupRetryButton() {
        Button btnRetry = findViewById(R.id.btnTryAgain);
        btnRetry.setOnClickListener((v)->{
            Intent newIntent = ShortTermMemoryPracticeActivity.makeIntent(
                    AnswerActivity.this,
                    chosenNumberOfOccurrences,
                    timeToRemember,
                    chosenShapes
            );
            startActivity(newIntent);
            finish();
        });
    }

    private void setupMainMenuBtn(){
        Button btnMainMenu = findViewById(R.id.btnBackToMainMenu);
        btnMainMenu.setOnClickListener((v)->{
            Intent newIntent = MainMenuActivity.makeIntentWithPreviousSettings(
                    this,
                    chosenNumberOfOccurrences,
                    timeToRemember,
                    chosenShapes
            );
            startActivity(newIntent);
            finish();
        });
    }

    private void countExactShapeShown() {
        numOfShape1 = 0;
        numOfShape2 = 0;
        numOfShape3 = 0;
        numOfShape4 = 0;
        numOfShape5 = 0;
        numOfShape6 = 0;

        for (int shapeIndex = 0; shapeIndex < exactShapeShown.size(); shapeIndex++){
            int shape = exactShapeShown.get(shapeIndex);

            if (shape == 1){
                numOfShape1++;
            }
            else if (shape == 2){
                numOfShape2++;
            }
            else if (shape == 3){
                numOfShape3++;
            }
            else if (shape == 4){
                numOfShape4++;
            }
            else if (shape == 5){
                numOfShape5++;
            }
            else{
                numOfShape6++;
            }
        }
    }

    private int checkAccuracy() {
        int totalPoints = exactShapeShown.size();

        // Calculation for total points. Any errors subtract total points by 1
        totalPoints = totalPoints - Math.abs(userAnswer.get(0) - numOfShape1);
        totalPoints = totalPoints - Math.abs(userAnswer.get(1) - numOfShape2);
        totalPoints = totalPoints - Math.abs(userAnswer.get(2) - numOfShape3);
        totalPoints = totalPoints - Math.abs(userAnswer.get(3) - numOfShape4);
        totalPoints = totalPoints - Math.abs(userAnswer.get(4) - numOfShape5);
        totalPoints = totalPoints - Math.abs(userAnswer.get(5) - numOfShape6);

        if (totalPoints < 0){
            totalPoints = 0;
        }

        return totalPoints;
    }
}