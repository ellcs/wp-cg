package computergraphics.particle_system;

import computergraphics.framework.math.Vector;

/**
 * Created by ellcs on 09.03.17.
 */
public class Range {

    Vector position;
    Vector size;

    public Range(Vector position, Vector range) {
        checkAllPositive(range);
        this.position = position;
        this.size = range;
    }

    public Vector getPosition() {
        return position.dublicate();
    }

    public Vector getSize() {
        return size.dublicate();
    }

    /**
     * @return sum of position and size;
     */
    public Vector getSum() {
        return getPosition().add(getSize());
    }

    private void checkAllPositive(Vector v) {
        if (isNegative(v.x()) ||
                isNegative(v.y()) ||
                isNegative(v.z())) {
            throw new IllegalArgumentException("One is negative: " + v);
        }
    }

    private boolean isNegative(double value) {
        return value < 0.0;
    }
}
