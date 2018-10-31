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

    public static Goal calculateGoalForParticle(final Point<Double> particlePosition, final double radius,
                                         final double startOpening, final double finalOpening,
                                         final double openingHeight, final double openingTolerance) {
        if (particlePosition.getX() < startOpening) {
            return new Goal(new Point<>(startOpening + radius, openingHeight),
                    startOpening + openingTolerance,
                    finalOpening - openingTolerance,
                    openingHeight - radius * 2.1,
                    openingHeight + radius * 2.1);
        } else if (particlePosition.getX() > finalOpening) {
            return new Goal(new Point<>(finalOpening - radius, openingHeight),
                    startOpening + openingTolerance,
                    finalOpening - openingTolerance,
                    openingHeight - radius * 2.1,
                    openingHeight + radius * 2.1);
        } else {
            return new Goal(new Point<>(particlePosition.getX(), openingHeight),
                    startOpening + openingTolerance,
                    finalOpening - openingTolerance,
                    openingHeight - radius * 2.1,
                    openingHeight + radius * 2.1);
        }
    }

    public static Goal calculateFinalGoalForParticle(final Point<Double> particlePosition, final double finalYPoint) {
        return new Goal(new Point<>(particlePosition.getX(), finalYPoint), 0.01, 0.01, 0.01, 0.01);
    }
}
