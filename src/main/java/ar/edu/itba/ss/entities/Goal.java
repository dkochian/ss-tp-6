package ar.edu.itba.ss.entities;

import ar.edu.itba.ss.managers.GridManager;
import ar.edu.itba.ss.utils.other.Point;
import ar.edu.itba.ss.utils.other.Tuple;

public class Goal {
    private final Point<Double> position;

    private final double x1;
    private final double x2;
    private final double y1;
    private final double y2;

    public Goal(Point<Double> position, double x1, double x2, double y1, double y2) {
        this.position = position;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public Point<Double> getPosition() {
        return position;
    }

    public boolean isReached(Point<Double> position, double radius) {
        return GridManager.isParticleBetweenBounds(position.getX(), radius, new Tuple<>(x1, x2))
        && GridManager.isParticleBetweenBounds(position.getY(), radius, new Tuple<>(y1, y2));
    }
}
