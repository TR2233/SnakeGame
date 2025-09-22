package com.example.snakegame.GameLogic;

import android.graphics.Rect;

import java.util.LinkedList;

//snake shall be ten pixels in width

public class testSnake {
    private LinkedList<Rect> snakeLinks; // the head will be the last in the list

    private LinkedList<Direction> snakeDirections; // directions for the snakeLinks list

    private Rect head;
    private Rect tail;

    private Direction hDirection, tDirection; // head and tail direction

    //creates the snake with one link and sets the direction

    public testSnake(Rect firstRect, Direction direction) {
        snakeLinks = new LinkedList<>();
        snakeDirections = new LinkedList<>();
        snakeLinks.add(new Rect());
        snakeLinks.getFirst().set(firstRect);
        snakeDirections.add(direction);
    }
    // returns the snake's links

    public LinkedList<Rect> getSnakeLinks() {
        return snakeLinks;
    }

    public LinkedList<Direction> getSnakeDirections() {
        return snakeDirections;
    }

    // responsible for moving the snake
    public void slither() {
        //tests whether the tail has reached its turn
        tail = snakeLinks.getFirst();
        if (Math.abs(tail.bottom - tail.top) <= 12 && Math.abs(tail.left - tail.right) <= 12) {
            snakeLinks.remove();
            snakeDirections.remove();
        }
        movetail();
        movehead();
    }

    private void movetail() {

        //get head coordinates
//        tDirection = snakeLinks.getFirst().getTailDirection();
        tDirection = snakeDirections.getFirst();
        tail = snakeLinks.getFirst();

        switch (tDirection) {
            case UP -> tail.set(tail.left, tail.top, tail.right, tail.bottom - 12);
            case RIGHT -> tail.set(tail.left + 12, tail.top, tail.right, tail.bottom);
            case DOWN -> tail.set(tail.left, tail.top + 12, tail.right, tail.bottom);
            default -> tail.set(tail.left, tail.top, tail.right - 12, tail.bottom);

        }
    }

    private void movehead() {
        //get head coordinates
        hDirection = snakeDirections.getLast();
        head = snakeLinks.getLast();

        switch (hDirection) {
            case UP -> head.set(head.left, head.top - 12, head.right, head.bottom);
            case RIGHT -> head.set(head.left, head.top, head.right + 12, head.bottom);
            case DOWN -> head.set(head.left, head.top, head.right, head.bottom + 12);
            default -> head.set(head.left - 12, head.top, head.right, head.bottom);
        }
    }

    //if it's not in the same direction or the opposite direction go into the if statement
    public void setDirection(Direction newDirection) {
        hDirection = snakeDirections.getLast();
        tDirection = snakeDirections.getFirst();
        head = snakeLinks.getLast();

        // for any direction we must take into account which direction the snake is already moving
        // in order to perform the calculations correctly

        if (!hDirection.equals(newDirection) && !newDirection.equals(hDirection.getOppositeDirection())) {

            switch (hDirection) {
                case UP -> snakeLinks.add(new Rect(head.left, head.top, head.right, head.top + 12));
                case RIGHT ->
                        snakeLinks.add(new Rect(head.right - 12, head.top, head.right, head.bottom));
                case DOWN ->
                        snakeLinks.add(new Rect(head.left, head.bottom - 12, head.right, head.bottom));
                default ->
                        snakeLinks.add(new Rect(head.left, head.top, head.left + 12, head.bottom));
            }
            snakeDirections.add(newDirection);
        }
    }

    // makes the snake grow a certain amount once the mouse is eaten
    public void grow() {

        head = snakeLinks.getLast();
        hDirection = snakeDirections.getLast();

        switch (hDirection) {
            case UP -> head.set(head.left, head.top - 16, head.right, head.bottom);
            case RIGHT -> head.set(head.left, head.top, head.right + 16, head.bottom);
            case DOWN -> head.set(head.left, head.top, head.right, head.bottom + 16);
            default -> head.set(head.left - 16, head.top, head.right, head.bottom);
        }
    }
}
