package com.example.memoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeScreenActivity extends AppCompatActivity {

    private static final long TIME_TO_TRANSITION = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        setupAnimation();
        setupTransitionToMainMenu();
    }

    private void setupAnimation() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(TIME_TO_TRANSITION);

        AnimationSet fadeInAnimation = new AnimationSet(false);
        fadeInAnimation.addAnimation(fadeIn);

        TextView txtAuthor = findViewById(R.id.txtCreatorTitle);
        txtAuthor.setAnimation(fadeInAnimation);

        ImageView welcomeImage = findViewById(R.id.WelcomeScreenLogo);
        welcomeImage.setAnimation(fadeInAnimation);
    }

    private void setupTransitionToMainMenu() {
        CountDownTimer timerToTransition = new CountDownTimer(
                TIME_TO_TRANSITION + 1500,
                1000
        ) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                // Sets up animation transition to the next activity
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(
                        WelcomeScreenActivity.this,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                ).toBundle();

                startActivity(MainMenuActivity.makeIntent(WelcomeScreenActivity.this),bundle);
                finish();
            }
        };

        timerToTransition.start();
    }
}