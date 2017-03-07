package computergraphics.particle_system.factories;

import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;

/**
 * Created by ellcs on 03.03.17.
 *
 * All arguments are package private, so the Factory can set the values :)
 */
public class Particle {

    /**
     * Contains position, normal and color.
     */
    RenderVertex renderVertex;

    Vector colorDifferenceInMilliSec;

    Vector actualSpeed;

    Vector[] forces;

    /**
     * Lifetime in milliseconds.
     */
    long lifetime = 0;

    long ownMaximumLifetime;

    public void update(long deltaTime) {
        this.lifetime+= deltaTime;
//        updateSpeed(deltaTime);
        updateLocation(deltaTime);
        updateColor(deltaTime);
    }

    private void updateColor(long deltaTime) {
        Vector color = this.renderVertex.color;
        Vector colorDifference = colorDifferenceInMilliSec.multiply(deltaTime);
        this.renderVertex.color = color.add(colorDifference);
    }

    private void updateLocation(long deltaTime) {
        Vector movementInDeltaTime = this.actualSpeed.multiply(deltaTime);
        this.renderVertex.position.addSelf(movementInDeltaTime);
    }

    /**
     * Updates the general speed.
     *
     * There are multiple forces:
     *
     *     F = l / t*t
     *
     * By multiplying <code>deltaTime</code> as t, we modify the speed.
     */
    private void updateSpeed(long deltaTime) {
        for (Vector force : this.forces) {
            force = force.subtract(this.renderVertex.position);
            this.actualSpeed.addSelf(force.multiply(deltaTime));
        }
    }

    public RenderVertex getRenderVertex() {
        return this.renderVertex;
    }

    public boolean lifetimeExceeded() {
        return this.lifetime > this.ownMaximumLifetime;
    }

}
