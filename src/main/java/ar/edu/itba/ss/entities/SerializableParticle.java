package ar.edu.itba.ss.entities;

import ar.edu.itba.ss.utils.other.Point;

import java.util.List;

public class SerializableParticle extends Particle {

    private boolean verified;

    public SerializableParticle(int id, Point<Double> position, Point<Double> velocity, Point<Double> acceleration,
                                double mass, double radius, double desiredVelocity) {
        this(id, position, velocity, acceleration, mass, radius, desiredVelocity, null, false);
    }

    public SerializableParticle(int id, Point<Double> position, Point<Double> velocity, Point<Double> acceleration,
                                double mass, double radius, double desiredVelocity, Goal goal,
                                boolean verified) {
        super(id, position, velocity, acceleration, mass, radius, desiredVelocity, goal);
        this.verified = verified;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
