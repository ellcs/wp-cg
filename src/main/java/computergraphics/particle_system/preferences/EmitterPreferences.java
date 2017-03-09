package computergraphics.particle_system.preferences;

import computergraphics.framework.math.Vector;
import computergraphics.particle_system.Range;

/**
 * Created by ellcs on 03.03.17.
 */
public class EmitterPreferences {

    public EmitterPreferences() {
        this.spawnRate = new ParticleSpawnRate();
    }

    /**
     * The maximum amount of particles, which might be created by the
     * emitterRange. When maximum reached, no other particles will be spawned.
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

    public Range emitterRange;

    public boolean drawEmitterBox = false;

}
