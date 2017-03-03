package computergraphics.particle_system;

import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;

/**
 * Created by ellcs on 03.03.17.
 */
public class Particle {

    /**
     * Contains position, normal and color.
     */
    public RenderVertex renderVertex;

    /**
     * Current direction of this particle.
     */
    public Vector direction;

    /**
     * Current speed of this particle.
     */
    public double speed;
    

}
