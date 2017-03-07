package computergraphics.particle_system.helpers;

import java.util.Random;

/**
 * Created by ellcs on 06.03.17.
 */
public class RandomHelper {

    Random r;

    public RandomHelper(Random r) {
        this.r = r;
    }

    public long randomBetween(long min, long max) {
        return (this.r.nextLong() + min) % max;
    }

    public double randomBetween(double min, double max) {
        return (this.r.nextDouble() + min) % max;
    }
}
