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
    private long lifetime = 0;

    public Particle(Vector position) {
        this.renderVertex = new RenderVertex(position, Vector.zero(), Colors.darkGreen);
    }

    public void update(long deltaTime) {
        this.lifetime+= deltaTime;
    }

    public RenderVertex getRenderVertex() {
        return this.renderVertex;
    }


}
