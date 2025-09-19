package com.example.snakegame.GameLogic;

import android.graphics.Rect;

public class Mouse {

    private int x, y, r;
    private Rect box;


    public void setNewBox(int newX, int newY) {
        box.set(newX - r, newY - r, newX + r, newY + r);
    }

    public Mouse(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        box = new Rect(x - r, y - r, x + r, y + r);
    }

    // gets mouse's radius
    public int getR() {
        return r;
    }

    // gets mouse's x position
    public int getX() {
        return x;
    }

    // gets mouse's y position
    public int getY() {
        return y;
    }

    //sets mouse's x position
    public void setX(int x) {
        this.x = x;
    }

    //sets mouse's y position
    public void setY(int y) {
        this.y = y;
    }

    //returns a square enclosing the mouse
    // this is used to determine if mouse spawns on the snake
    public Rect getBox() {
        return box;
    }
}
