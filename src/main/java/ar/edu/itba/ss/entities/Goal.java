package ar.edu.itba.ss.entities;

import ar.edu.itba.ss.utils.other.Point;

public class Goal {
    private Point<Double> position;
    private double radius;

    public Goal(Point<Double> position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    public Point<Double> getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }
}
