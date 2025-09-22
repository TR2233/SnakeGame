package com.example.snakegame.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameSnakeLooper gameSnakeLooper;

    private SnakeGame snakeGame;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        //make sure we know when application is destroyed so we don't try drawing
        //on something that doesn't exist
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    //called when the surface is displayed
    // we need to go into the manifest and set the activity to portrait
    // so we dont have to worry about the game switching to landscape
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Log.e("TSR", "Changed!");
        snakeGame = new SnakeGame(surfaceHolder, getHeight(), getWidth());
        gameSnakeLooper = new GameSnakeLooper(snakeGame, getContext());
        gameSnakeLooper.start();
    }

    //called if the surface is resized
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.e("TSR", "Changed!");
    }

    //called when the surface is destroyed or goes into the background
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.e("TSR", "Changed!");
        if (gameSnakeLooper != null) {
            //wait for it shutdown
            // we dont want it to go to another app without shutting down
            //the drawing code may continue to draw
            gameSnakeLooper.shutdown();

            while (gameSnakeLooper != null) {
                //this waits for the thread to terminate
                try {
                    gameSnakeLooper.join();
                    gameSnakeLooper = null;
                    Log.e("TSR", "Shutdown successful");
                } catch (InterruptedException e) {
                    Log.e("TSR", "Game was interrupted while shutting down");
                }

                //this will keep looping until the try part is executed without throwing an
                //exception and then gameSnakeLooper is false
            }
        }
    }


    //controls the snake's movements
    public void up() {
        if (snakeGame != null) {
            snakeGame.goUp();
        }
    }

    public void right() {
        if (snakeGame != null) {
            snakeGame.goRight();
        }
    }

    public void down() {
        if (snakeGame != null) {
            snakeGame.goDown();
        }
    }

    public void left() {
        if (snakeGame != null) {
            snakeGame.goLeft();
        }
    }

    public void pause() {
        if (snakeGame != null) {
            snakeGame.Pause();

        }
    }

    public void restart() {
        if (snakeGame != null) {
            if (!gameSnakeLooper.isAlive()) {
                gameSnakeLooper.start();
            } else {
                snakeGame.createNewAssets();
            }
        }
    }


}
