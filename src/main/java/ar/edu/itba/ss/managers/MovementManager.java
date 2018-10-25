package ar.edu.itba.ss.managers;

import ar.edu.itba.ss.entities.Goal;
import ar.edu.itba.ss.entities.Particle;
import ar.edu.itba.ss.utils.other.Point;
import ar.edu.itba.ss.utils.other.RandomUtils;
import ar.edu.itba.ss.utils.other.Tuple;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class MovementManager {

    private final IOManager ioManager;
    private final ParticleManager particleManager;

    @Inject
    public MovementManager(IOManager ioManager, ParticleManager particleManager) {
        this.ioManager = ioManager;
        this.particleManager = particleManager;
    }

    public boolean updatePosition(final Particle p, final Point<Double> position, List<Particle> toRemove) {
        final Tuple<Double, Double> bottom = new Tuple<>(
                ioManager.getConfiguration().getDimensions().getY() - ioManager.getConfiguration().getTolerance(),
                ioManager.getConfiguration().getDimensions().getY() + ioManager.getConfiguration().getTolerance());

        p.setPosition(new Point<>(position.getX(), position.getY()));

        Goal currentGoal = p.getCurrentGoal();

        if (GridManager.isBetweenBounds(position.getY() + p.getRadius(), bottom)) {
            toRemove.add(p);
            return true;
        }
        else if (currentGoal.isReached(position, p.getRadius()))
            p.removeGoal();

        return false;
    }
}
