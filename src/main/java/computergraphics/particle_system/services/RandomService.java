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
        isValid(min, max);
        return ((this.r.nextLong() % max) + min) % max;
    }

    public double randomBetween(double min, double max) {
        isValid(min, max);
        return ((this.r.nextDouble() % max)  + min) % max;
    }

    public int getRandomIndex(int max) {
        return Math.abs(r.nextInt(max));
    }

    private void isValid(long min, long max) {
        if (min > max) {
            throw new IllegalArgumentException("Min is bigger then Max");
        } else if (min <= 0 || max <= 0) {
            throw new IllegalArgumentException("Min or max is negative.");
        }
    }

    private void isValid(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Min is bigger then Max");
        } else if (min < 0 || max < 0) {
            throw new IllegalArgumentException("Min or max is negative.");
        }
    }
}
