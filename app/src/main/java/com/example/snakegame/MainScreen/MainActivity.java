package com.example.snakegame.MainScreen;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.snakegame.Game.Play;
import com.example.snakegame.R;


public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button settingsButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the start button and set the on-click listener
        startButton = findViewById(R.id.startB);
        settingsButton = findViewById(R.id.main_settings);

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Play.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        });

        //create the shared preferences for score and HighScore if they don't already exist
        Context context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sharedPreferences.contains(getString(R.string.score))) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.score), 0);
            editor.putInt(getString(R.string.HighScore), 0);
            editor.apply();
        }
    }

    //register the receiver at the beginning of the application
    @Override
    public void onStart() {
        super.onStart();
    }

    //make sure the receiver is unregistered when the app is closing
    @Override
    public void onStop() {
        super.onStop();
    }


    public void resetHighScore(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.HighScore), 0);
        editor.apply();
        Toast.makeText(this, getString(R.string.ScoreReset), Toast.LENGTH_SHORT).show();
    }
}