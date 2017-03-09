package computergraphics.particle_system.services;

import computergraphics.framework.math.Vector;
import computergraphics.particle_system.Range;

import java.util.Random;

/**
 * Created by ellcs on 06.03.17.
 */
public class VectorService {

    Random r;

    RandomService randomHelper;

    public VectorService(Random r, RandomService randomService) {
        this.r = r;
        this.randomHelper = randomService;
    }

    public Vector getRandomVectorInRange(Range range) {
        checkNotNull(range);

        Vector randomVector = getRandomVectorInRange(range.getSize());
        return randomVector.add(range.getPosition());
    }

    public Vector getRandomVectorInRange(Vector range) {
        checkNotNull(range);
        Vector v = getRandomVector();

        double x = range.x() == 0.0 ? 0 : v.x() % range.x();
        double y = range.y() == 0.0 ? 0 : v.y() % range.y();
        double z = range.z() == 0.0 ? 0 : v.z() % range.z();

        v.set(0, x);
        v.set(1, y);
        v.set(2, z);
        return v;
    }

    public Vector getRandomVector() {
        // nextFloat is between 0.0 and 1.0
        // we use nextInt instead and divide it by 1000f
        // so we get small values, which are bigger then one :)
        int bigPrime = 105929;
        float x = r.nextFloat() * bigPrime;
        float z = r.nextFloat() * bigPrime;
        float y = r.nextFloat() * bigPrime;
        Vector v = new Vector(x, y, z);
        return v;
    }

    public Vector getRandomVectorBetween(Vector min, Vector max) {
        checkNotNull(min);
        checkNotNull(max);
        if (min.getDimension() != max.getDimension()) {
            throw new IllegalArgumentException("Not same dimensions");
        }
        double x = randomHelper.randomBetween(min.x(), max.x());
        double y = randomHelper.randomBetween(min.y(), max.y());
        double z = randomHelper.randomBetween(min.z(), max.z());
        if (min.getDimension() == 4) {
            double w = randomHelper.randomBetween(min.w(), max.w());
            return new Vector(x,y,z,w);
        }
        return new Vector(x,y,z);
    }

    private void checkNotNull(Object vector) {
        if (vector == null) {
            throw new IllegalArgumentException("object is null.");
        }
    }
}
