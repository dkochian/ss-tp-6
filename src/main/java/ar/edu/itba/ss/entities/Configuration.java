package ar.edu.itba.ss.entities;

import ar.edu.itba.ss.utils.other.Point;
import ar.edu.itba.ss.utils.other.Range;
import ar.edu.itba.ss.utils.other.Tuple;

public class Configuration {

    private static final double BOX_HEIGHT = 20D;
    private static final double BOX_WIDTH = 20D;
    private static final double BOX_OPENING = 1.2;

    //IO
    private final String outputDirectory;
    private final String outputSimulationFile;
    private final String inputDirectory;
    private final String inputFilename;

    //Input
    private final boolean generateInput;
    private final int particleAmount;
    private final int maxIterations;

    //General
    private final double factor;
    private final Point<Double> dimensions;
    private final Tuple<Double, Range<Double>> opening;
    private final Point<Range<Double>> particleVelocity;
    private final Point<Range<Double>> particleAcceleration;
    private final Range<Double> particleRadius;
    private final Range<Double> particleMass;
    private final Range<Double> particleDesiredVelocity;

    //Simulation
    private final double dt;
    private final double interactionRadius;

    //Constants
    private final double kn;
    private final double kt;
    private final double tolerance;
    private final double openingTolerance;
    private final double a;
    private final double b;
    private final double T;

    //Animation
    private final double compress;

    public Configuration() {
        //IO
        outputDirectory = "output";
        outputSimulationFile = "simulation.tsv";
        inputDirectory = "input";
        inputFilename = "input.json";

        //Input
        generateInput = true;
        particleAmount = 200;
        maxIterations = 100;

        //General
        factor = 10D;
        opening = new Tuple<>(BOX_HEIGHT, new Range<>(BOX_WIDTH / 2 - BOX_OPENING / 2, BOX_OPENING));
        dimensions = new Point<>(BOX_WIDTH, opening.getKey() + opening.getKey() / factor);//FUll simulation dimensions: not silo dimensions
        particleVelocity = new Point<>(new Range<>(0D, 0D), new Range<>(0D, 0D));
        particleAcceleration = new Point<>(new Range<>(0D, 0D), new Range<>(0D, 0D));
        particleRadius = new Range<>(0.25, 0.04); // m
        particleMass = new Range<>(80D, 0D); //kg
        particleDesiredVelocity = new Range<>(0.8, 5.2);

        //Constants
        kn = 2.4 * Math.pow(10, 5); //N/m
        kt = 1.0 * Math.pow(10, 5);
        tolerance = 0.01;
        openingTolerance = 0.10;
        a = 2000; //N
        b = 0.08; //m
        T = 0.5; //s

        //Simulation
        dt = 5.1E-5;
        interactionRadius = 2 * (particleRadius.getBase() + particleRadius.getOffset());

        //Animation
        compress = 1.0E-2;
    }

    public Configuration(String outputDirectory, String outputSimulationFile, String inputDirectory,
                         String inputFilename, boolean generateInput, int particleAmount, int maxIterations,
                         double factor, Point<Double> dimensions, Tuple<Double, Range<Double>> opening,
                         Point<Range<Double>> particleVelocity, Point<Range<Double>> particleAcceleration,
                         Range<Double> particleRadius, Range<Double> particleMass, Range<Double> particleDesiredVelocity,
                         double dt, double interactionRadius, double kn, double kt, double tolerance, double openingTolerance,
                         double a, double b, double t, double compress) {
        this.outputDirectory = outputDirectory;
        this.outputSimulationFile = outputSimulationFile;
        this.inputDirectory = inputDirectory;
        this.inputFilename = inputFilename;
        this.generateInput = generateInput;
        this.particleAmount = particleAmount;
        this.maxIterations = maxIterations;
        this.factor = factor;
        this.dimensions = dimensions;
        this.opening = opening;
        this.particleVelocity = particleVelocity;
        this.particleAcceleration = particleAcceleration;
        this.particleRadius = particleRadius;
        this.particleMass = particleMass;
        this.particleDesiredVelocity = particleDesiredVelocity;
        this.dt = dt;
        this.interactionRadius = interactionRadius;
        this.kn = kn;
        this.kt = kt;
        this.tolerance = tolerance;
        this.openingTolerance = openingTolerance;
        this.a = a;
        this.b = b;
        T = t;
        this.compress = compress;
    }

    public static double getBoxHeight() {
        return BOX_HEIGHT;
    }

    public static double getBoxWidth() {
        return BOX_WIDTH;
    }

    public static double getBoxOpening() {
        return BOX_OPENING;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getOutputSimulationFile() {
        return outputSimulationFile;
    }

    public String getInputDirectory() {
        return inputDirectory;
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public boolean isGenerateInput() {
        return generateInput;
    }

    public int getParticleAmount() {
        return particleAmount;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getFactor() {
        return factor;
    }

    public Point<Double> getDimensions() {
        return dimensions;
    }

    public Tuple<Double, Range<Double>> getOpening() {
        return opening;
    }

    public Point<Range<Double>> getParticleVelocity() {
        return particleVelocity;
    }

    public Point<Range<Double>> getParticleAcceleration() {
        return particleAcceleration;
    }

    public Range<Double> getParticleRadius() {
        return particleRadius;
    }

    public Range<Double> getParticleMass() {
        return particleMass;
    }

    public Range<Double> getParticleDesiredVelocity() {
        return particleDesiredVelocity;
    }

    public double getDt() {
        return dt;
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }

    public double getKn() {
        return kn;
    }

    public double getKt() {
        return kt;
    }

    public double getTolerance() {
        return tolerance;
    }

    public double getOpeningTolerance() { return openingTolerance; }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getT() {
        return T;
    }

    public double getCompress() {
        return compress;
    }
}