package com.example.memoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class TestUserMemoryActivity extends AppCompatActivity {

    private static final int MAX_SHAPES = 6;
    private static final int DEFAULT_ANSWER = 0;
    private static int chosenNumberOfOccurrences;
    private static long timeToRemember;
    private static ArrayList<Integer> chosenShapes;
    private static ArrayList<Integer> exactShapeShown;

    private ArrayList<Integer> userAnswer;

    public static Intent makeIntent(Context context, int desiredNumberOfOccurrences,
                                    long desiredTime, ArrayList<Integer> arrOfShapes,
                                    ArrayList<Integer> exactAnswer
    ) {
        chosenNumberOfOccurrences = desiredNumberOfOccurrences;
        timeToRemember = desiredTime;
        chosenShapes = arrOfShapes;
        exactShapeShown = exactAnswer;
        return new Intent(context, TestUserMemoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_user_memory);

        setupUserAnswerTracker();
        detectUserAnswer();
        setupCheckAnswerBtn();
    }

    private void setupUserAnswerTracker() {
        userAnswer = new ArrayList<>();
        for (int i = 0; i < MAX_SHAPES; i++){
            userAnswer.add(DEFAULT_ANSWER);
        }
    }

    private void detectUserAnswer() {
        Slider sliderShape1 = findViewById(R.id.shape1Slider);
        Slider sliderShape2 = findViewById(R.id.shape2Slider);
        Slider sliderShape3 = findViewById(R.id.shape3Slider);
        Slider sliderShape4 = findViewById(R.id.shape4Slider);
        Slider sliderShape5 = findViewById(R.id.shape5Slider);
        Slider sliderShape6 = findViewById(R.id.shape6Slider);

        recordUserAnswer(sliderShape1, 1);
        recordUserAnswer(sliderShape2, 2);
        recordUserAnswer(sliderShape3, 3);
        recordUserAnswer(sliderShape4, 4);
        recordUserAnswer(sliderShape5, 5);
        recordUserAnswer(sliderShape6, 6);
    }

    private void recordUserAnswer(Slider sliderShape, int index) {
        sliderShape.addOnChangeListener((slider, value, fromUser) -> {
            userAnswer.set(index-1,(int)sliderShape.getValue());
        });
    }

    private void setupCheckAnswerBtn() {
        Button checkAnswerBtn = findViewById(R.id.btnCheckAnswer);
        checkAnswerBtn.setOnClickListener((v)->{
            Intent newIntent = AnswerActivity.makeIntent(TestUserMemoryActivity.this,
                    chosenNumberOfOccurrences,
                    timeToRemember,
                    chosenShapes,
                    exactShapeShown,
                    userAnswer
            );

            startActivity(newIntent);
            finish();
        });
    }
}