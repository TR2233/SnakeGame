package com.example.snakegame.GameLogic;

import android.graphics.Rect;

import java.util.LinkedList;

//snake shall be ten pixels in width

public class testSnake {
    private LinkedList<Rect> snakeLinks; // the head will be the last in the list

    private LinkedList<Character> snakeDirections; // directions for the snakeLinks list

    private Rect head;
    private Rect tail;

    private char hDirection, tDirection; // head and tail direction

    //creates the snake with one link and sets the direction

    public testSnake(Rect firstRect, char direction) {
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

    public LinkedList<Character> getSnakeDirections() {
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

        if (tDirection == 'u') {
            tail.set(tail.left, tail.top, tail.right, tail.bottom - 12);
        } else if (tDirection == 'r') {
            tail.set(tail.left + 12, tail.top, tail.right, tail.bottom);
        } else if (tDirection == 'd') {
            tail.set(tail.left, tail.top + 12, tail.right, tail.bottom);
        } else {
            tail.set(tail.left, tail.top, tail.right - 12, tail.bottom);
        }

    }

    private void movehead() {
        //get head coordinates
        hDirection = snakeDirections.getLast();
        head = snakeLinks.getLast();

        if (hDirection == 'u') {
            head.set(head.left, head.top - 12, head.right, head.bottom);
        } else if (hDirection == 'r') {

            head.set(head.left, head.top, head.right + 12, head.bottom);
        } else if (hDirection == 'd') {
            head.set(head.left, head.top, head.right, head.bottom + 12);
        } else {
            head.set(head.left - 12, head.top, head.right, head.bottom);
        }
    }

    //if it's not in the same direction or the opposite direction go into the if statement
    public void setDirection(char newDirection) {
        hDirection = snakeDirections.getLast();
        tDirection = snakeDirections.getFirst();
        head = snakeLinks.getLast();

        // for any direction we must take into account which direction the snake is already moving
        // in order to perform the caclulations correctly

        //to-do: right now the new direction is a one dimensional line, make it a square
        // so that when the turn completes it works out. Bad explanation
        if (hDirection != newDirection && newDirection != getODirection(hDirection)) {

            if (hDirection == 'u') {
                if (newDirection == 'r') {
                    snakeLinks.add(new Rect(head.left, head.top, head.right, head.top + 12));
                } else {
                    snakeLinks.add(new Rect(head.left, head.top, head.right, head.top + 12));
                }
            } else if (hDirection == 'r') {
                if (newDirection == 'u') {
                    snakeLinks.add(new Rect(head.right - 12, head.top, head.right, head.bottom));
                } else {
                    snakeLinks.add(new Rect(head.right - 12, head.top, head.right, head.bottom));
                }

            } else if (hDirection == 'd') {
                if (newDirection == 'r') {
                    snakeLinks.add(new Rect(head.left, head.bottom - 12, head.right, head.bottom));
                } else {
                    snakeLinks.add(new Rect(head.left, head.bottom - 12, head.right, head.bottom));
                }

            } else {
                if (newDirection == 'u') {
                    snakeLinks.add(new Rect(head.left, head.top, head.left + 12, head.bottom));
                } else {
                    snakeLinks.add(new Rect(head.left, head.top, head.left + 12, head.bottom));
                }

            }
            snakeDirections.add(newDirection);
        }
    }

    //returns the opposite direction the snake is heading
    private char getODirection(char D) {
        if (D == 'u') return 'd';
        else if (D == 'r') return 'l';
        else if (D == 'd') return 'u';
        else return 'r';
    }

    // makes the snake grow a certain amount once the mouse is eaten
    public void grow() {

        head = snakeLinks.getLast();
        hDirection = snakeDirections.getLast();
        if (hDirection == 'u') {
            head.set(head.left, head.top - 16, head.right, head.bottom);
        } else if (hDirection == 'r') {
            head.set(head.left, head.top, head.right + 16, head.bottom);
        } else if (hDirection == 'd') {
            head.set(head.left, head.top, head.right, head.bottom + 16);
        } else {
            head.set(head.left - 16, head.top, head.right, head.bottom);
        }
    }
}
