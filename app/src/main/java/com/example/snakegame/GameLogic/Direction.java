package com.example.snakegame.GameLogic;

//enum for the possible directions the snake can have
public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    //returns the opposite direction
    public Direction getOppositeDirection() {
        switch (this) {
            case UP -> {
                return Direction.DOWN;
            }
            case RIGHT -> {
                return Direction.LEFT;
            }
            case DOWN -> {
                return Direction.UP;
            }
            default -> {
                return Direction.RIGHT;
            }
        }
    }
}
