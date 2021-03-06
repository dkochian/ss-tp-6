package ar.edu.itba.ss.managers;

import ar.edu.itba.ss.entities.Particle;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class ParticleManager {

    private final List<Particle> particles = new ArrayList<>();

    boolean addParticle(final Particle p) {
        return particles.add(p);
    }

    public List<Particle> getParticles() {
        return Collections.unmodifiableList(particles);
    }

    public boolean removeParticle(final Particle p) {
        return particles.remove(p);
    }

    public boolean removeParticles(final List<Particle> toRemove) {
        return particles.removeAll(toRemove);
    }

    public void clearNeighbours() {
        for(final Particle p : particles)
            p.clearNeighbours();
    }

    public void clear(){
        particles.clear();
    }

    public boolean isEmpty() { return particles.size() - 2 == 0; }

}
