package computergraphics.particle_system.preferences;

import computergraphics.framework.math.Vector;

/**
 * Created by ellcs on 03.03.17.
 */
public class ParticlePreferences {

    public static class Creation {
        public Double startSpeed;
        public Vector startDirection;
        public Vector startForce;

        public Vector startColor;
    }

    public static class Life {

        public Vector forceBoxPosition;

        /**
         * This vector does not represent a static force. It gives the ranges for x,y and z. The emitter
         * creates a random force within these ranges.
         *
         * In order to create a static force for all particles, set it to (1,1,1).
         */
        public Vector forceBox;
    }

    public static class Dead {
        public Integer maximumLifetime;
        public Vector deadColor;
    }

}
