package ar.edu.itba.ss.managers;

import ar.edu.itba.ss.entities.Goal;
import ar.edu.itba.ss.entities.Particle;
import ar.edu.itba.ss.entities.SerializableParticle;
import ar.edu.itba.ss.schemas.Schema;
import ar.edu.itba.ss.utils.io.OutputWriter;
import ar.edu.itba.ss.utils.other.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulationManager {
    private static final Logger logger = LoggerFactory.getLogger(SimulationManager.class);

    private final Schema schema;
    private final IOManager ioManager;
    private final ParticleManager particleManager;
    private final GridManager gridManager;
    private final OutputWriter outputWriter;

    @Inject
    public SimulationManager(Schema schema, ParticleManager particleManager, IOManager ioManager,
                             GridManager gridManager, OutputWriter outputWriter) {
        this.schema = schema;
        this.ioManager = ioManager;
        this.particleManager = particleManager;
        this.gridManager = gridManager;
        this.outputWriter = outputWriter;
    }

    public void simulate() {
        double elapsed = 0.0;

        particleManager.clear();


        final Point<Double> position0 = new Point<>(ioManager.getConfiguration().getOpening().getValue().getBase(),
                ioManager.getConfiguration().getOpening().getKey());
        final Point<Double> position1 = new Point<>(ioManager.getConfiguration().getOpening().getValue().getBase() + ioManager.getConfiguration().getOpening().getValue().getOffset(),
                ioManager.getConfiguration().getOpening().getKey());

        particleManager.addParticle(new Particle(0, position0, new Point<>(0.0, 0.0), new Point<>(0.0, 0.0),
                ioManager.getConfiguration().getParticleMass().getBase(), (ioManager.getConfiguration().getParticleRadius().getBase() + ioManager.getConfiguration().getParticleRadius().getBase()) / 100.0, 0, null));

        particleManager.addParticle(new Particle(1, position1, new Point<>(0.0, 0.0), new Point<>(0.0, 0.0),
                ioManager.getConfiguration().getParticleMass().getBase(), (ioManager.getConfiguration().getParticleRadius().getBase() + ioManager.getConfiguration().getParticleRadius().getBase()) / 100.0, 0, null));

        outputWriter.remove();
        outputWriter.removeParticlesOverOpeningFile();
        outputWriter.removeDischargeCurve();

        double avgRadius = ioManager.getConfiguration().getParticleRadius().getBase() + ioManager.getConfiguration().getParticleRadius().getOffset() / 2;

        logger.debug("Adding particles");
        for (SerializableParticle p : ioManager.getInputData().getParticles()) {
            if (p.isVerified()) {
                gridManager.addParticle(p, false);
                particleManager.addParticle(new Particle(p.getId(), p.getPosition(), p.getVelocity(), p.getAcceleration(),
                        p.getMass(), p.getRadius(), p.getDesiredVelocity(), calculateGoalsForParticle(p.getPosition(), p.getRadius(),
                        ioManager.getConfiguration().getOpening().getValue().getBase(),
                        ioManager.getConfiguration().getOpening().getValue().getBase() + ioManager.getConfiguration().getOpening().getValue().getOffset(),
                        ioManager.getConfiguration().getDimensions().getY(),
                        ioManager.getConfiguration().getOpeningTolerance(),
                        ioManager.getConfiguration().getOpening().getKey())));

            } else {
                gridManager.addParticle(p);
                particleManager.addParticle(p);
            }
        }

        schema.init();

        Map<Particle, Double> particlesExited = new HashMap<>();

        double counter = 1;
        long prev = System.currentTimeMillis();
        long current;
        int completed;
        int oldCompleted = 0;

        logger.info("Starting simulation");

        while (!particleManager.isEmpty()) {

            for (final Particle p : particleManager.getParticles())
                gridManager.addParticle(p, false);

            particleManager.clearNeighbours();
            gridManager.calculateNeighbours();
            elapsed += schema.updateParticles();
            current = System.currentTimeMillis();

            calculateParticlesOverOpening(particlesExited, elapsed);

            gridManager.clear();

            if (Double.compare(elapsed, counter * ioManager.getConfiguration().getCompress()) >= 0) {
                counter++;
                try {
                    outputWriter.write();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                completed = ioManager.getConfiguration().getParticleAmount() - (particleManager.getParticles().size() - 2);
                double aux = (1.0 * completed / ioManager.getConfiguration().getParticleAmount() * 100);
                completed = (int) aux;

                if (completed != oldCompleted) {
                    logger.info("Simulation completed: {}% ({} ms)", completed, current - prev);
                    prev = current;
                    oldCompleted = completed;
                }
            }
        }
        try {
            List<Double> exitTimes = outputWriter.writeParticlesOverOpening(particlesExited);
            outputWriter.writeDischargeCurve(exitTimes);
        } catch (Exception e) {
        }
        logger.info("Simulation duration: {} seconds", elapsed);
    }

    private void calculateParticlesOverOpening(Map<Particle, Double> particlesExited, double elapsed) {
        for (Particle p : particleManager.getParticles()) {
            if (p.getPosition().getX() > ioManager.getConfiguration().getOpening().getValue().getBase()
                    && p.getPosition().getX() < ioManager.getConfiguration().getOpening().getValue().getBase() +
                    ioManager.getConfiguration().getOpening().getValue().getOffset()
                    && Math.abs(p.getPosition().getY() - ioManager.getConfiguration().getOpening().getKey()) < 0.005) {
                particlesExited.put(p, elapsed);
            }
        }
    }

    public static List<Goal> calculateGoalsForParticle(final Point<Double> particlePosition, final double radius,
                                                       final double startOpening, final double finalOpening,
                                                       final double openingHeight, final double openingTolerance,
                                                       final double finalYPoint) {
        List<Goal> goals = new LinkedList<>();
        if (particlePosition.getX() + radius < startOpening) {
            goals.add(new Goal(new Point<>(startOpening + radius,
                    openingHeight),
                    startOpening - openingTolerance,
                    finalOpening + openingTolerance,
                    openingHeight - radius * 2.1,
                    openingHeight + radius * 2.1));
        } else if (particlePosition.getX() + radius > finalOpening) {
            goals.add(new Goal(new Point<>(finalOpening - radius, openingHeight),
                    startOpening - openingTolerance,
                    finalOpening + openingTolerance,
                    openingHeight - radius * 2.1,
                    openingHeight + radius * 2.1));
        } else {
            goals.add(new Goal(new Point<>(particlePosition.getX(), openingHeight),
                    startOpening - openingTolerance,
                    finalOpening + openingTolerance,
                    openingHeight - radius * 2.1,
                    openingHeight + radius * 2.1));
        }

        goals.add(new Goal(new Point<>(particlePosition.getX(), finalYPoint), 0.01, 0.01, 0.01, 0.01));

        return goals;
    }
}
