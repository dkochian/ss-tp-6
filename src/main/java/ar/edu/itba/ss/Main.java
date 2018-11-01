package ar.edu.itba.ss;

import ar.edu.itba.ss.entities.Configuration;
import ar.edu.itba.ss.managers.IOManager;
import ar.edu.itba.ss.managers.InjectorManager;
import ar.edu.itba.ss.managers.SimulationManager;
import ar.edu.itba.ss.utils.other.Point;
import ar.edu.itba.ss.utils.other.Range;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.krb5.Config;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final Injector injector = InjectorManager.getInjector();
        final IOManager ioManager = injector.getInstance(IOManager.class);
        Configuration configuration = ioManager.getConfiguration();

        final List<Run> desiredVelocities = new ArrayList<>();
        //Mati runStart = 0, juan = 3, kochi = 7, special = 11
        final int runStart = 10;
        int runEnd;

        if(runStart == 10)
            runEnd = runStart + 1;
        else
            runEnd = runStart + 3;

        desiredVelocities.add(0, new Run(0.8, 3, 0));
        desiredVelocities.add(1, new Run(1.6, 3, 0));
        desiredVelocities.add(2, new Run(2.4, 2, 0));
        desiredVelocities.add(3, new Run(2.4, 1, 2));
        desiredVelocities.add(4, new Run(3.2, 3, 0));
        desiredVelocities.add(5, new Run(4.0, 3, 0));
        desiredVelocities.add(6, new Run(4.8, 1, 0));
        desiredVelocities.add(7, new Run(4.8, 2, 1));
        desiredVelocities.add(8, new Run(5.6, 3, 0));
        desiredVelocities.add(9, new Run(6.0, 3, 0));
        desiredVelocities.add(10, new Run(2.0, 3, 0));

        for (int i = runStart; i < runEnd; i++) {
            final double desiredVelocity = desiredVelocities.get(i).getDesiredVelocity();
            final int runs = desiredVelocities.get(i).getRuns();
            configuration.setParticleDesiredVelocity(new Range<>(desiredVelocity, 0.0));
            configuration.setParticleAmount(200);
            for (int j = 0; j < runs; j++) {
                final int id = desiredVelocities.get(i).getId() + j;
                configuration.setOutputSimulationFile("simulation-vel-" + desiredVelocity + "-iter-" + id + ".tsv");
                configuration.setOutputSlidingFile("sliding-vel-" + desiredVelocity + "-iter-" + id + ".tsv");
                configuration.setOutputDischargeFile("discharge-vel-" + desiredVelocity + "-iter-" + id + ".tsv");
                logger.info("Running program");
                long start = System.currentTimeMillis();
                final SimulationManager simulationManager = injector.getInstance(SimulationManager.class);
                simulationManager.simulate();
                logger.info("Program finished in {} ms", System.currentTimeMillis() - start);
                ioManager.reloadInput();
            }
        }
    }

    private static class Run {

        private final double desiredVelocity;

        private final int runs;

        private final int id;

        public Run(double desiredVelocity, int runs, int id) {
            this.desiredVelocity = desiredVelocity;
            this.runs = runs;
            this.id = id;
        }

        public double getDesiredVelocity() {
            return desiredVelocity;
        }

        public int getRuns() {
            return runs;
        }

        public int getId() {
            return id;
        }
    }
}