package computergraphics.particle_system.services;

import java.util.Random;

/**
 * Created by ellcs on 06.03.17.
 */
public class RandomService {

    Random r;

    public RandomService(Random r) {
        this.r = r;
    }

    public long randomBetween(long min, long max) {
        return ((this.r.nextLong() % max)  + min) % max;
    }

    public double randomBetween(double min, double max) {
        return ((this.r.nextDouble() % max)  + min) % max;
    }

    public int getRandomIndex(int max) {
        return Math.abs(r.nextInt(max));
    }
}
