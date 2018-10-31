package ar.edu.itba.ss.entities;

import ar.edu.itba.ss.utils.other.Point;
import ar.edu.itba.ss.utils.other.Range;
import ar.edu.itba.ss.utils.other.Tuple;

public class Configuration {

    private static double BOX_HEIGHT = 20D;
    private static double BOX_WIDTH = 20D;
    private static double BOX_OPENING = 1.2;

    //IO
    private String outputDirectory;
    private String outputSimulationFile;
    private String outputSlidingFile;
    private String outputDischargeFile;
    private String inputDirectory;
    private String inputFilename;

    //Input
    private boolean generateInput;
    private int particleAmount;
    private int maxIterations;

    //General
    private double factor;
    private Point<Double> dimensions;
    private Tuple<Double, Range<Double>> opening;
    private Point<Range<Double>> particleVelocity;
    private Point<Range<Double>> particleAcceleration;
    private Range<Double> particleRadius;
    private Range<Double> particleMass;
    private Range<Double> particleDesiredVelocity;

    //Simulation
    private double dt;
    private double interactionRadius;

    //Constants
    private double kn;
    private double kt;
    private double tolerance;
    private double openingTolerance;
    private double a;
    private double b;
    private double T;

    //Animation
    private double compress;

    public Configuration() {
        //IO
        outputDirectory = "output";
        outputSimulationFile = "simulation.tsv";
        outputSimulationFile = "slidingWindow.tsv";
        outputDischargeFile = "dischargeCurve.tsv";
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
        particleDesiredVelocity = new Range<>(3.0, 0.0);

        //Constants
        kn = 1.2 * Math.pow(10, 5); //N/m
        kt = 2.4 * Math.pow(10, 5);
        tolerance = 0.01;
        openingTolerance = 0.10;
        a = 2000; //N
        b = 0.08; //m
        T = 0.5; //s

        //Simulation
        dt = 5.1E-5;
        interactionRadius = 2 * (particleRadius.getBase() + particleRadius.getOffset());

        //Animation
        compress = 0.1;
    }

    public Configuration(String outputDirectory, String outputSimulationFile, String outputSlidingFile, String outputDischargeFile, String inputDirectory, String inputFilename, boolean generateInput, int particleAmount, int maxIterations, double factor, Point<Double> dimensions, Tuple<Double, Range<Double>> opening, Point<Range<Double>> particleVelocity, Point<Range<Double>> particleAcceleration, Range<Double> particleRadius, Range<Double> particleMass, Range<Double> particleDesiredVelocity, double dt, double interactionRadius, double kn, double kt, double tolerance, double openingTolerance, double a, double b, double t, double compress) {
        this.outputDirectory = outputDirectory;
        this.outputSimulationFile = outputSimulationFile;
        this.outputSlidingFile = outputSlidingFile;
        this.outputDischargeFile = outputDischargeFile;
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

    public static void setBoxHeight(double boxHeight) {
        BOX_HEIGHT = boxHeight;
    }

    public static double getBoxWidth() {
        return BOX_WIDTH;
    }

    public static void setBoxWidth(double boxWidth) {
        BOX_WIDTH = boxWidth;
    }

    public static double getBoxOpening() {
        return BOX_OPENING;
    }

    public static void setBoxOpening(double boxOpening) {
        BOX_OPENING = boxOpening;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getOutputSimulationFile() {
        return outputSimulationFile;
    }

    public void setOutputSimulationFile(String outputSimulationFile) {
        this.outputSimulationFile = outputSimulationFile;
    }

    public String getOutputSlidingFile() {
        return outputSlidingFile;
    }

    public void setOutputSlidingFile(String outputSlidingFile) {
        this.outputSlidingFile = outputSlidingFile;
    }

    public String getOutputDischargeFile() {
        return outputDischargeFile;
    }

    public void setOutputDischargeFile(String outputDischargeFile) {
        this.outputDischargeFile = outputDischargeFile;
    }

    public String getInputDirectory() {
        return inputDirectory;
    }

    public void setInputDirectory(String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public void setInputFilename(String inputFilename) {
        this.inputFilename = inputFilename;
    }

    public boolean isGenerateInput() {
        return generateInput;
    }

    public void setGenerateInput(boolean generateInput) {
        this.generateInput = generateInput;
    }

    public int getParticleAmount() {
        return particleAmount;
    }

    public void setParticleAmount(int particleAmount) {
        this.particleAmount = particleAmount;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public Point<Double> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Point<Double> dimensions) {
        this.dimensions = dimensions;
    }

    public Tuple<Double, Range<Double>> getOpening() {
        return opening;
    }

    public void setOpening(Tuple<Double, Range<Double>> opening) {
        this.opening = opening;
    }

    public Point<Range<Double>> getParticleVelocity() {
        return particleVelocity;
    }

    public void setParticleVelocity(Point<Range<Double>> particleVelocity) {
        this.particleVelocity = particleVelocity;
    }

    public Point<Range<Double>> getParticleAcceleration() {
        return particleAcceleration;
    }

    public void setParticleAcceleration(Point<Range<Double>> particleAcceleration) {
        this.particleAcceleration = particleAcceleration;
    }

    public Range<Double> getParticleRadius() {
        return particleRadius;
    }

    public void setParticleRadius(Range<Double> particleRadius) {
        this.particleRadius = particleRadius;
    }

    public Range<Double> getParticleMass() {
        return particleMass;
    }

    public void setParticleMass(Range<Double> particleMass) {
        this.particleMass = particleMass;
    }

    public Range<Double> getParticleDesiredVelocity() {
        return particleDesiredVelocity;
    }

    public void setParticleDesiredVelocity(Range<Double> particleDesiredVelocity) {
        this.particleDesiredVelocity = particleDesiredVelocity;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }

    public void setInteractionRadius(double interactionRadius) {
        this.interactionRadius = interactionRadius;
    }

    public double getKn() {
        return kn;
    }

    public void setKn(double kn) {
        this.kn = kn;
    }

    public double getKt() {
        return kt;
    }

    public void setKt(double kt) {
        this.kt = kt;
    }

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public double getOpeningTolerance() {
        return openingTolerance;
    }

    public void setOpeningTolerance(double openingTolerance) {
        this.openingTolerance = openingTolerance;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getT() {
        return T;
    }

    public void setT(double t) {
        T = t;
    }

    public double getCompress() {
        return compress;
    }

    public void setCompress(double compress) {
        this.compress = compress;
    }
}
