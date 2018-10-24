package ar.edu.itba.ss.managers;

import ar.edu.itba.ss.entities.Particle;
import ar.edu.itba.ss.utils.other.Point;
import ar.edu.itba.ss.utils.other.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;

public class ForcesManager {
    private static final Logger logger = LoggerFactory.getLogger(ForcesManager.class);

    private GridManager gridManager;

    private final double presionConstant;

    private final double a;
    private final double b;
    private final double kt;
    private final double kn;
    private final double gamma;
    private final double mu;
    private final double T;

    @Inject
    public ForcesManager(IOManager ioManager, GridManager gridManager) {
        this.a = ioManager.getConfiguration().getA();
        this.b = ioManager.getConfiguration().getB();
        this.kt = ioManager.getConfiguration().getKt();
        this.kn = ioManager.getConfiguration().getKn();
        this.gamma = ioManager.getConfiguration().getGamma();
        this.mu = ioManager.getConfiguration().getMu();
        this.T = ioManager.getConfiguration().getT();
        this.gridManager = gridManager;
        this.presionConstant = 2 * Math.PI * ioManager.getConfiguration().getInteractionRadius();
    }

    public Point<Double> updateAndCalculateForces(final Particle particle, final Point<Double> position,
                                                  final Point<Double> velocity) {
        final Tuple<Double, Point<Double>> result = calculateForces(position, velocity, particle.getDesiredVelocity(),
                particle.getNeighbours(), particle.getRadius(), particle.getMass(), particle.getCurrentGoal().getPosition());

        particle.setForces(result.getKey());

        return result.getValue();
    }

    public Tuple<Double, Point<Double>> calculateForces(final Point<Double> position, final Point<Double> velocity,
                                                        final double desireVelocity, final Collection<Particle> neighbours,
                                                        final double radius, final double mass, Point<Double> goal) {
        Point<Double> desireForce = calculateDesireForce(mass, velocity, desireVelocity, calculateEx(position,
                goal), calculateEy(position, goal));

        double forceX = desireForce.getX();
        double forceY = desireForce.getY();
        double fNTotal = 0D;

        for (Particle p2 : neighbours) {
            double eps = calculateEps(position, radius, p2.getPosition(), p2.getRadius());
            double eX = calculateEx(position, p2.getPosition());
            double eY = calculateEy(position, p2.getPosition());
            double fN = 0;
            double fT = 0;

            if (eps > 0) {
                Point<Double> relativeVelocity = relativeVelocity(p2.getVelocity(), velocity);
                fN = calculateSpringForce(eps);
                fT = calculateFrictionForce(relativeVelocity, eps, eX, eY);
            }
            else
                fN = calculateSocialForce(Math.abs(eps));

            forceX += fN * eX + fT * -eY;
            forceY += fN * eY + fT * eX;
            fNTotal += Math.abs(fN);
        }
        for (GridManager.WallType w : GridManager.WallType.values()) {
            if (w.equals(GridManager.WallType.BOTTOM) && gridManager.isParticleXBetweenOpening(position.getX(), radius))
                continue;

            Point<Double> collisionPoint = gridManager.collisionPoint(position, w);
            double eps = calculateEps(position, radius, collisionPoint, 0D);
            if (eps > 0) {
                double eX = calculateEx(position, collisionPoint);
                double eY = calculateEy(position, collisionPoint);
                Point<Double> relativeVelocity = relativeVelocity(new Point<>(0D, 0D), velocity);
                double fN = calculateSpringForce(eps);
                double fT = calculateFrictionForce(relativeVelocity, eps, eX, eY);
                forceX += fN * eX + fT * -eY;
                forceY += fN * eY + fT * eX;
                fNTotal += Math.abs(fN);
            }
        }
        return new Tuple<>(fNTotal / this.presionConstant, new Point<>(forceX, forceY));
    }

    private double calculateEy(final Point<Double> position, final Point<Double> position2) {
        return (position2.getY() - position.getY()) / Particle.getDistance(position2, position);
    }

    private double calculateEx(final Point<Double> position, final Point<Double> position2) {
        return (position2.getX() - position.getX()) / Particle.getDistance(position2, position);
    }

    private Point<Double> normalDirection(final double eX, final double eY) {
        return new Point<>(eX, eY);
    }

    private Point<Double> tangentialDirection(final double eX, final double eY) {
        return new Point<>(-1 * eY, eX);
    }

    private double calculateEps(final Point<Double> position, final double radius,
                                final Point<Double> position2, final double radius2) {
        return radius + radius2 - Particle.getDistance(position2, position);
    }

    private double calculateSpringForce(final double eps) {
        return -1 * kn * eps;
    }

    private double calculateFrictionForce(final Point<Double> relativeVelocity, final double eps,
                                          final double eX, final double eY) {
        return -1 * eps * kt * scalarProduct(relativeVelocity, tangentialDirection(eX, eY));
    }

    private double calculateSocialForce(final double overlap){
        return -1 * a * Math.exp(-overlap / b);
    }

    private Point<Double> calculateDesireForce(double mass, Point<Double> velocity,
                                               double desireVelocity, double eX, double eY) {
        return new Point<>((mass / T) * (desireVelocity * eX - velocity.getX()),
                (mass / T) * (desireVelocity * eY - velocity.getX()));
    }

    private Point<Double> relativeVelocity(Point<Double> velocity, Point<Double> velocity2) {
        return new Point<>(velocity2.getX() - velocity.getX(),
                velocity2.getY() - velocity.getY());
    }

    private double scalarProduct(Point<Double> v1, Point<Double> v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }
}