package computergraphics.particle_system.preferences;

import computergraphics.framework.math.Vector;

/**
 * Created by ellcs on 03.03.17.
 */
public class EmitterPreferences {

    /**
     * The maximum amount of particles, which might be created by the
     * emitter.
     */
    public int maximumParticles;

    /**
     * The frequency in which the particles are spawned.
     * The minimum spawn rate has to be smaller then the maximum.
     */
    public static class ParticleSpawnRate {
        public int min;
        public int max;
    }

    /**
     * Within these ranges, the particles are created.
     */
    public Vector emitterSize;

    /**
     * All particles are always in that box. When a particle leaves the
     * partileBoxSize, it dies.
     */
    public Vector particleBoxSize;

}
