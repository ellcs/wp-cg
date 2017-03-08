package computergraphics.particle_system.helpers;

import computergraphics.framework.math.Vector;

import java.util.Random;

/**
 * Created by ellcs on 06.03.17.
 */
public class VectorHelper {

    Random r;

    public VectorHelper(Random r) {
        this.r = r;
    }

    public Vector getRandomVectorInRange(Vector range) {
        if (range == null) {
            throw new IllegalArgumentException("'range' can not be null.");
        }
        Vector v = getRandomVector();
        v.set(0, v.x() % range.x());
        v.set(1, v.y() % range.y());
        v.set(2, v.z() % range.z());
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
}
