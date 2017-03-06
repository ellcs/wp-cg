package computergraphics.particle_system;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;

/**
 * Created by ellcs on 03.03.17.
 */
public class Particle {

    /**
     * Contains position, normal and color.
     */
    private RenderVertex renderVertex;

    /**
     * Current direction of this particle.
     */
    private Vector direction;

    /**
     * Current speed of this particle.
     */
    private double speed;

    /**
     * Lifetime in milliseconds.
     */
    private float lifetime = 0f;

    public Particle(Vector position) {
        this.renderVertex = new RenderVertex(position, Vector.zero(), Colors.darkGreen);
    }

    public void update(float deltaTime) {
        this.lifetime+= deltaTime;
    }

    public RenderVertex getRenderVertex() {
        return this.renderVertex;
    }


}
