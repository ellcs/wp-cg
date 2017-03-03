package computergraphics.particle_system;

import computergraphics.framework.rendering.RenderVertex;

import java.util.List;

/**
 * Created by ellcs on 03.03.17.
 */
public interface IEmitter {

    List<RenderVertex> getRenderVertecies();

    void update(float deltaTime);
}
