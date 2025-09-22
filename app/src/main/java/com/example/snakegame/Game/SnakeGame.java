package com.example.snakegame.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import androidx.preference.PreferenceManager;

import com.example.snakegame.GameLogic.Mouse;
import com.example.snakegame.GameLogic.testSnake;
import com.example.snakegame.R;

import java.util.LinkedList;

public class SnakeGame {
    //surface fields
    private SurfaceHolder surfaceHolder;

    //game fields
    private int screenW, screenH; // screen width and height
    private char direction = 'r'; // starting direction for the snake
    private testSnake tSnake; // test snake
    private Mouse mouse;
    private double mouseR2; // mouse radius squared for x^2+y^2 <= r^2
    private int score;
    private int xH, yH; // head coordinates used for eating the mouse
    private LinkedList<Rect> tLinks; // same as links but for the test snake
    private LinkedList<Character> tLinksDirections; // directions corresponding to tLinks
    private Rect head;
    private boolean isPaused = false;
    private boolean gameOver = false;
    //paint fields

    private Paint snakeColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint background = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mouseColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    //Preferences

    private Context context;
    private SharedPreferences sharedPreferences;


    //initializes the surfaceHolder for future drawing and initializes the screen width and height

    public SnakeGame(SurfaceHolder surfaceHolder, int screenH, int screenW) {
        this.surfaceHolder = surfaceHolder;
        this.screenH = screenH;
        this.screenW = screenW;
    }
    //initializes the game's assets

    public void init(Context context) {

        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // depending on the settings, the features of the game will change appropriately
        if (sharedPreferences.getBoolean(context.getString(R.string.mouseColor), true)) {
            mouseColor.setColor(Color.RED);
        } else
            mouseColor.setColor(Color.GREEN);

        snakeColor.setColor(Color.WHITE);
        background.setColor(Color.BLACK);
        createNewAssets();
    }

    public void createNewAssets() {
        tSnake = new testSnake(new Rect(100, 100, 300, 112), 'r');
        mouse = new Mouse(screenW / 2, screenH / 2, 50);
        mouseR2 = Math.pow(50, 2.0);
        tLinks = tSnake.getSnakeLinks();
        tLinksDirections = tSnake.getSnakeDirections();
        gameOver = false;
        isPaused = false;
        direction = 'r';
        score = 0;
        updateScore();
        draw();
    }
    //will frequently update the game

    public void update() {
//        snake.slither();
        if (!isPaused && !gameOver) {
            if (snakeIsOnScreen() && !snakeTangled()) {
                tSnake.slither();
                tSnake.setDirection(direction);
                if (testEaten()) {
                    score++;
                    tSnake.grow();
                    testGenerateMouse();
                    updateScore();
                }

            }
        }
    }

    private boolean snakeIsOnScreen() {
        head = tLinks.getLast();

        if (head.right > screenW || head.top < 0 || head.left < 0 || head.bottom > screenH) {
            return false;
        }
        return true;
    }
    //draws the assets in the gameview

    public void draw() {

        //it locks the canvas so nothing else can draw on it
        //will return null if it cant lock it
        Canvas canvas = surfaceHolder.lockCanvas();

        if (canvas != null) {
            canvas.drawPaint(background);

            for (int i = 0; i < tLinks.size(); i++) {
                canvas.drawRect(tLinks.get(i), snakeColor);
            }

            canvas.drawCircle(mouse.getX(), mouse.getY(), mouse.getR(), mouseColor);

            //unlock the canvas if it is not null
            //making it available for other threads to draw on it
            surfaceHolder.unlockCanvasAndPost(canvas);
        }


    }


    public void testGenerateMouse() {
        boolean keepGenerating = true;
        int mX = 0;
        int mY = 0;
        int mR = mouse.getR();

        //this will generate new random coordinates for the mouse
        //it will keep looping until it is within the bounds of the game screen
        // and not spawned on the snake

        while (keepGenerating) {

            mX = (int) Math.floor((Math.random() * screenW));
            mY = (int) (Math.floor(Math.random() * screenH));
            keepGenerating = false;

            if (mouseIsOnScreen(mX, mY, mR)) {
                mouse.setNewBox(mX, mY);
                for (int i = 0; i < tLinks.size(); i++) {
                    if (Rect.intersects(tLinks.get(i), mouse.getBox())) { //perfect
                        keepGenerating = true;
                        break;
                    }
                }
            } else
                keepGenerating = true;
        }
        mouse.setX(mX);
        mouse.setY(mY);
    }


    private boolean testEaten() {
        head = tLinks.getLast();
        char headDir = tLinksDirections.getLast();

        if (headDir == 'u') {
            xH = (head.left + head.right) / 2;
            yH = head.top;
        } else if (headDir == 'r') {
            xH = head.right;
            yH = (head.top + head.bottom) / 2;
        } else if (headDir == 'd') {
            xH = (head.left + head.right) / 2;
            yH = head.bottom;
        } else {
            xH = head.left;
            yH = (head.top + head.bottom) / 2;
        }

        return Math.pow(xH - mouse.getX(), 2.0) +
                Math.pow(yH - mouse.getY(), 2.0) <= mouseR2;

    }
    // sets directions for the snake

    public void goUp() {
        direction = 'u';
    }

    public void goRight() {
        direction = 'r';
    }

    public void goDown() {
        direction = 'd';
    }

    public void goLeft() {
        direction = 'l';
    }

    public void Pause() {
        isPaused = !isPaused;
    }
    //updates the score


    public void updateScore() {
        //increment the score by 1

        //we had to make this class a subclass of appcompat to access certain methods
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (score > sharedPreferences.getInt(context.getString(R.string.HighScore), 0)) {
            editor.putInt(context.getString(R.string.HighScore), score);
        }
        editor.putInt(context.getString(R.string.score), score);
        editor.apply();
    }

    public boolean mouseIsOnScreen(int x, int y, int r) {

        //determines if the ball falls outside of the screen in any way
        if ((x + r) > screenW || (x - r) < 0 || (y + r) > screenH || (y - r) < 0)
            return false;
        return true;
    }

    private boolean snakeTangled() {
        head = tLinks.getLast();
        for (int i = 0; i < tLinks.size() - 2; i++) {
            if (Rect.intersects(head, tLinks.get(i))) {
                return true;
            }
        }
        return false;
    }
}
