package com.example.snakegame.Game;

import android.content.Context;
import android.util.Log;

public class GameSnakeLooper extends Thread {

    //is volatile because threads might cash your variables
    private volatile boolean running = true;
    private SnakeGame snakeGame;
    private Context context;

    public GameSnakeLooper(SnakeGame game, Context context) {
        snakeGame = game;
        this.context = context;
    }

    public void run() {

        snakeGame.init(context);
        while (running) {
            //draw stuff and update the game
            try {
                snakeGame.update();
                Thread.sleep(5);
                snakeGame.draw();
            } catch (InterruptedException e) {
                Log.e("TSR", "Game interrupted");
                running = false;
            }
        }
    }

    //stop the game from looping
    //if it wasnt volatile then it may not be called from the other thread
    public void shutdown() {
        running = false;
    }

}



