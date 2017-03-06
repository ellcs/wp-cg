package computergraphics.particle_system.factories;

import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.particle_system.helpers.RandomHelper;

/**
 * Created by ellcs on 03.03.17.
 *
 * All arguments are package private, so the Factory can set the values :)
 */
public class Particle {

    private RandomHelper randomHelper;

    /**
     * Contains position, normal and color.
     */
    RenderVertex renderVertex;

    Vector actualForce;

    /**
     * Lifetime in milliseconds.
     */
    long lifetime = 0;

    long ownMaximumLifetime;

    public void update(long deltaTime) {
        this.lifetime+= deltaTime;
        Vector movementInDeltaTime = this.actualForce.multiply(deltaTime);
        this.renderVertex.position = this.renderVertex.position.add(movementInDeltaTime);
    }

    public RenderVertex getRenderVertex() {
        return this.renderVertex;
    }

    public boolean lifetimeExceeded() {
        return this.lifetime > this.ownMaximumLifetime;
    }

}