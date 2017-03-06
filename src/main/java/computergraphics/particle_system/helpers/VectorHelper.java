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
        float x = r.nextFloat();
        float z = r.nextFloat();
        float y = r.nextFloat();
        Vector v = new Vector(x, y, z);
        return v;
    }
}
