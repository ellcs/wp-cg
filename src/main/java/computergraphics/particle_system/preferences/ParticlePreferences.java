package computergraphics.particle_system.preferences;

import computergraphics.framework.math.Vector;

/**
 * Created by ellcs on 03.03.17.
 */
public class ParticlePreferences {

    public Creation creation;

    public Life life;

    public Dead dead;

    /**
     * Default is 3f.
     */
    public float particleSize = 3;

    public ParticlePreferences() {
        this.creation = new Creation();
        this.life = new Life();
        this.dead = new Dead();
    }

    public static class Creation {
        public Vector startSpeed;
        public Vector startColor;
    }

    public static class Life {

        /**
         * The amount of forces created in the <code>forceBoxSize</code>.
         * Default: <code>0</code>
         */
        public int amountOfForces = 0;

        /**
         * Default: <code>Vector.zero()</code>
         */
        public Vector forceBoxPosition = Vector.zero();

        public float forceLength = 0.0001f;
        /**
         * This Vector does not represent a static force. It gives the ranges for x,y and z. The emitter
         * creates a random force within these ranges.
         *
         * In order to create a static force for all particles, set it to (1,1,1).
         *
         * Default: <code>(1,1,1)</code>
         */
        public Vector forceBoxSize = new Vector(1,1,1);
    }

    public static class Dead {
        public long minimumLifetimeInMilliSec;
        public long maximumLifetimeInMilliSec;
        public Vector deadColor;
    }

}
