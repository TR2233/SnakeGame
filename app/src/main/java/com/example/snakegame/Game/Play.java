package com.example.snakegame.Game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.snakegame.MainScreen.HighScoreReceiver;
import com.example.snakegame.R;

public class Play extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    //UI buttons for snake control
    Button up, down, left, right, pause, restart;

    //surface view for our game
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        up = findViewById(R.id.Up);
        down = findViewById(R.id.Down);
        left = findViewById(R.id.Left);
        right = findViewById(R.id.Right);
        pause = findViewById(R.id.pause);
        restart = findViewById(R.id.restart);

        gameView = findViewById(R.id.thegame);

        //register the onSharedPreferenceChangeListener
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    //controls the snake. I'm sure there's a better practice at interfacing
    //the controls, but this is the best I've got for now

    public void moveUp(View view) {
        gameView.up();
    }

    public void moveDown(View view) {
        gameView.down();
    }

    public void moveLeft(View view) {
        gameView.left();
    }

    public void moveRight(View view) {
        gameView.right();
    }

    public void pause(View view) {
        gameView.pause();
    }

    public void restart(View view) {
        gameView.restart();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        //textview for updating the score

        final String scoreText;

        if (key.equals("Score")) {
            scoreText = "Highscore: " + sharedPreferences.getInt("HighScore", 0)
                    + " Score: " + sharedPreferences.getInt(key, 0);
        } else {
            scoreText = "Highscore: " + sharedPreferences.getInt(key, 0)
                    + " Score: " + sharedPreferences.getInt("Score", 0);
        }

        if (sharedPreferences.getInt("Score", 0) > 5) {
            Intent intent = new Intent(Play.this, HighScoreReceiver.class);
            sendBroadcast(intent);
        }

        // we must update the UI with Android's Handler
        final TextView myTextView = findViewById(R.id.score);

        final Handler myHandler = new Handler();

        myHandler.post(new Runnable() {
            @Override
            public void run() {
                myTextView.setText(scoreText);
            }
        });

    }
}