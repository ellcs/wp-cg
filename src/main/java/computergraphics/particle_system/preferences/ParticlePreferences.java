package computergraphics.particle_system.preferences;

import computergraphics.framework.math.Vector;

/**
 * Created by ellcs on 03.03.17.
 */
public class ParticlePreferences {

    public Creation creation;
    public Life life;
    public Dead dead;

    public ParticlePreferences() {
        this.creation = new Creation();
        this.life = new Life();
        this.dead = new Dead();
    }

    public static class Creation {
        public Vector startForce;

        public Vector startColor;
    }

    public static class Life {

        public float speed;

        public Vector forceBoxPosition;

        /**
         * This VectorHelper does not represent a static force. It gives the ranges for x,y and z. The emitter
         * creates a random force within these ranges.
         *
         * In order to create a static force for all particles, set it to (1,1,1).
         */
        public Vector forceBoxSize;
    }

    public static class Dead {
        public long minimumLifetimeInMilliSec;
        public long maximumLifetimeInMilliSec;
        public Vector deadColor;
    }

}
