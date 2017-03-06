package computergraphics.particle_system.preferences;

import computergraphics.framework.math.Vector;
import computergraphics.particle_system.Emitter;

/**
 * Created by ellcs on 03.03.17.
 */
public class EmitterPreferences {

    public EmitterPreferences() {
        this.spawnRate = new ParticleSpawnRate();
    }

    /**
     * The maximum amount of particles, which might be created by the
     * emitter. When maximum reached, no other particles will be spawned.
     */
    public Integer maximumParticles;

    /**
     * The frequency in which the particles are spawned per second.
     * The minimum spawn rate has to be smaller then the maximum.
     */
    public static class ParticleSpawnRate {
        public Float minPerMilliSec;
        public Float maxPerMilliSec;
    }

    public ParticleSpawnRate spawnRate;

    /**
     * Within these ranges, the particles are created.
     */
    public Vector emitterSize;

    public boolean drawEmitterBox = false;

    /**
     * All particles are always in that box. When a particle leaves the
     * partileBoxSize, it dies.
     */
    public Vector particleBoxSize;

}
