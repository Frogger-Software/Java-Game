package edu.csc413.tankgame;

public class Pair {
    private final double left;
    private final String right;

    public Pair(double lhs, String rhs){
        left = lhs;
        right = rhs;
    }

    public double getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
}
