package com.example.snakegame.GameLogic;

import android.graphics.Rect;

public class Mouse {

    private int x; // x coordinate
    private int y; // y coordinate
    private final int mouseRadius;
    private final Rect box;


    public void setNewBox(int newX, int newY) {
        box.set(newX - mouseRadius, newY - mouseRadius, newX + mouseRadius, newY + mouseRadius);
    }

    public Mouse(int x, int y, int mouseRadius) {
        this.x = x;
        this.y = y;
        this.mouseRadius = mouseRadius;
        box = new Rect(x - mouseRadius, y - mouseRadius, x + mouseRadius, y + mouseRadius);
    }

    // gets mouse's radius
    public int getMouseRadius() {
        return mouseRadius;
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
