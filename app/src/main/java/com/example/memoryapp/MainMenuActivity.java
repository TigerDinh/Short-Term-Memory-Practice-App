package com.example.memoryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    public static final int EASY_MODE = 7500;
    public static final int MEDIUM_MODE = 5000;
    public static final int HARD_MODE = 2500;

    private static int previous_settings;
    private static int previousNumberOfOccurrence;
    private static long previousDifficulty;
    private static ArrayList<Integer> previousChosenShape;

    private int desiredNumberOfOccurrence;
    private Button chosenDifficulty;
    private ArrayList<Integer> shapeChosenArrayList;

    public static Intent makeIntent(Context context) {
        previous_settings = 0;
        return new Intent(context, MainMenuActivity.class);
    }

    public static Intent makeIntentWithPreviousSettings(
            Context context,
            int numberOfOccurrences,
            long desiredTime,
            ArrayList<Integer> arrOfShapes)
    {
        previous_settings = 1;
        previousNumberOfOccurrence = numberOfOccurrences;
        previousDifficulty = desiredTime;
        previousChosenShape = arrOfShapes;

        return new Intent(context, MainMenuActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        detectSliderNumber();
        detectDifficultyLevel();
        setupShapeSelection();
        setupStartButton();
        setupPreviousSettings();
    }

    private void detectSliderNumber() {
        Slider numOfOccurrence = findViewById(R.id.numOfLetterSlider);
        TextView chosenNumber = findViewById(R.id.txtChosenNumberOfLetters);
        desiredNumberOfOccurrence = (int)numOfOccurrence.getValue();

        numOfOccurrence.addOnChangeListener((slider, value, fromUser) -> {
            chosenNumber.setText(String.valueOf((int)numOfOccurrence.getValue()));
            desiredNumberOfOccurrence = (int)numOfOccurrence.getValue();
        });
    }

    private void detectDifficultyLevel() {
        chosenDifficulty = findViewById(R.id.btnEasy);
        if (previous_settings == 0){
            chosenDifficulty.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
        }

        Button setupBtnEasy = findViewById(R.id.btnEasy);
        Button setupBtnMedium = findViewById(R.id.btnMedium);
        Button setupBtnHard = findViewById(R.id.btnHard);

        buttonColorChanger(setupBtnEasy);
        buttonColorChanger(setupBtnMedium);
        buttonColorChanger(setupBtnHard);
    }

    private void setupShapeSelection() {
        Button setupMusicNote = findViewById(R.id.btnMusicNote);
        Button setupOmega = findViewById(R.id.btnOmega);
        Button setupFlower = findViewById(R.id.btnFlowerShape);
        Button setupFilledDiamond = findViewById(R.id.btnFilledDiamond);
        Button setupSquare = findViewById(R.id.btnSquare);
        Button setupTriangle = findViewById(R.id.btnTriangle);

        shapeChosenArrayList = new ArrayList<>();

        tileColorChanger(setupMusicNote, 1);
        tileColorChanger(setupOmega, 2);
        tileColorChanger(setupFlower, 3);
        tileColorChanger(setupFilledDiamond, 4);
        tileColorChanger(setupTriangle, 5);
        tileColorChanger(setupSquare, 6);
    }

    private void setupStartButton() {
        Button btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener((v)->{
            if (shapeChosenArrayList.size() > 1){
                Intent newIntent = ShortTermMemoryPracticeActivity.makeIntent(
                        this,
                        desiredNumberOfOccurrence,
                        findOutDifficultyChosen(),
                        shapeChosenArrayList
                );
                startActivity(newIntent);
                finish();
            }
            else{
                Toast.makeText(
                        this,
                        "Please select at least two shapes to remember",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void buttonColorChanger(Button btnClicked){
        btnClicked.setOnClickListener((v)->{
            chosenDifficulty.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.transparent));
            btnClicked.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
            chosenDifficulty = btnClicked;
        });
    }

    private void tileColorChanger(Button btnClicked, int shapeID){
        btnClicked.setOnClickListener((v)->{

            // From track_color -> white
            if (btnClicked.getBackgroundTintList() == ContextCompat.getColorStateList(this, R.color.track_color)){
                btnClicked.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.transparent));
                shapeChosenArrayList.remove((Integer) shapeID);
            }

            // From white -> track_color
            else{
                btnClicked.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                shapeChosenArrayList.add(shapeID);
            }
        });
    }

    private long findOutDifficultyChosen() {
        long result;

        if (chosenDifficulty.getText() == getString(R.string.Easy)){
            result = EASY_MODE;
        }
        else if (chosenDifficulty.getText() == getString(R.string.Medium)){
            result = MEDIUM_MODE;
        }
        else{
            result = HARD_MODE;
        }

        return result;
    }

    private void setupPreviousSettings() {
        if (previous_settings == 1){

            // Previous slider chosen
            Slider numOfOccurrence = findViewById(R.id.numOfLetterSlider);
            numOfOccurrence.setValue((float) previousNumberOfOccurrence);
            desiredNumberOfOccurrence = (int)numOfOccurrence.getValue();

            // Previous difficulty chosen
            if (previousDifficulty == EASY_MODE){
                Button btnClicked = findViewById(R.id.btnEasy);
                btnClicked.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                chosenDifficulty = btnClicked;
            }
            else if (previousDifficulty == MEDIUM_MODE){
                Button btnClicked = findViewById(R.id.btnMedium);
                btnClicked.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                chosenDifficulty = btnClicked;
            }
            else{
                Button btnClicked = findViewById(R.id.btnHard);
                btnClicked.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                chosenDifficulty = btnClicked;
            }

            // Previous shape chosen
            shapeChosenArrayList = previousChosenShape;
            Button setup8PointedStar = findViewById(R.id.btnMusicNote);
            Button setup5PointedStar = findViewById(R.id.btnOmega);
            Button setupFlower = findViewById(R.id.btnFlowerShape);
            Button setupFilledDiamond = findViewById(R.id.btnFilledDiamond);
            Button setupDiamond = findViewById(R.id.btnSquare);
            Button setupSnowflake = findViewById(R.id.btnTriangle);

            for (int i = 0; i < shapeChosenArrayList.size(); i++){
                if (shapeChosenArrayList.get(i) == 1){
                    setup8PointedStar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                }
                else if (shapeChosenArrayList.get(i) == 2){
                    setup5PointedStar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                }
                else if (shapeChosenArrayList.get(i) == 3){
                    setupFlower.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                }
                else if (shapeChosenArrayList.get(i) == 4){
                    setupFilledDiamond.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                }
                else if (shapeChosenArrayList.get(i) == 5){
                    setupSnowflake.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                }
                else{
                    setupDiamond.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.track_color));
                }
            }
        }
    }
}