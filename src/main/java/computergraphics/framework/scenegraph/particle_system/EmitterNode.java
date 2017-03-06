package computergraphics.framework.scenegraph.particle_system;

import com.jogamp.opengl.GL2;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.rendering.VertexBufferObject;
import computergraphics.framework.scenegraph.LeafNode;
import computergraphics.framework.scenegraph.TimerTickable;
import computergraphics.particle_system.DeltaTimeCalculator;
import computergraphics.particle_system.IEmitter;

/**
 * Created by ellcs on 06.03.17.
 */
public class EmitterNode extends LeafNode implements TimerTickable{

    IEmitter emitter;

    VertexBufferObject vbo;

    DeltaTimeCalculator timeCalculator;

    public EmitterNode(IEmitter emitter) {
        this.emitter = emitter;
        this.vbo = new VertexBufferObject();
        this.timeCalculator = new DeltaTimeCalculator();
        createVbo();
    }

    private void createVbo() {
        vbo.Setup(emitter.getRenderVerticies(), GL2.GL_POINTS);
    }

    @Override
    public void timerTick(int counter) {
        timeCalculator.tick();
        emitter.update(timeCalculator.getDeltaTime());
    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        createVbo();
        gl.glPointSize(3);
        if (mode == RenderMode.REGULAR) {
            vbo.draw(gl);
        }
    }

}
