package computergraphics.particle_system.preferences;

import computergraphics.framework.math.Vector;
import computergraphics.particle_system.Range;

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
        public double startSpeed;
        public Range startDirectionRange;
        public Vector[] startColors;
    }

    public static class Life {

        /**
         * In order to change the color of particles over time, set these
         * values. When set to (0,0,0) and (0,0,0) the color won't change.
         *
         * Also, when minimum.equals(maximum), the change will be static.
         *
         * Default: <code>Vector(0,0,0)</code>
         */
        public Vector minimumColorDifferenceInMilliSec;
        public Vector maximumColorDifferenceInMilliSec;

        /**
         * The amount of forces created in the <code>forceBoxSize</code>.
         * Default: <code>0</code>
         */
        public int amountOfForces = 0;


        public float weight = 1f;

        /**
         *
         */
        public Range forceRange;
    }

    public static class Dead {
        public long minimumLifetimeInMilliSec;
        public long maximumLifetimeInMilliSec;
    }

}
