package computergraphics.framework.scenegraph.particle_system;

import com.jogamp.opengl.GL2;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.VertexBufferObject;
import computergraphics.framework.scenegraph.LeafNode;
import computergraphics.framework.scenegraph.TimerTickable;
import computergraphics.particle_system.DeltaTimeCalculator;
import computergraphics.particle_system.IEmitter;

import static com.jogamp.opengl.GL.GL_LINES;

/**
 * Created by ellcs on 06.03.17.
 */
public class EmitterNode extends LeafNode implements TimerTickable {

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

    /**
     * Please find a proper way to draw the lines in the VBO :)
     */
    private void drawEmitterBox(GL2 gl) {
        if (this.emitter.drawEmitterBox()) {
            Vector emitterBox = this.emitter.getEmitterBox();

            gl.glLineWidth(2.5f);
            gl.glColor3f((float) this.color.get(0), (float) this.color.get(1), (float) this.color.get(2));
            gl.glBegin(GL_LINES);

            // side one:
            gl.glVertex3f((float) 0, (float) 0, (float) 0);
            gl.glVertex3f((float) emitterBox.x(), (float) 0, (float) 0);

            gl.glVertex3f((float) emitterBox.x(), (float) 0, (float) 0);
            gl.glVertex3f((float) emitterBox.x(), (float) emitterBox.y(), (float) 0);

            gl.glVertex3f((float) emitterBox.x(), (float) emitterBox.y(), (float) 0);
            gl.glVertex3f((float) 0, (float) emitterBox.y(), (float) 0);

            gl.glVertex3f((float) 0, (float) emitterBox.y(), (float) 0);
            gl.glVertex3f((float) 0, (float) 0, (float) 0);

            // side two:
            gl.glVertex3f((float) 0, (float) 0, (float) emitterBox.z());
            gl.glVertex3f((float) emitterBox.x(), (float) 0, (float) emitterBox.z());

            gl.glVertex3f((float) emitterBox.x(), (float) 0, (float) emitterBox.z());
            gl.glVertex3f((float) emitterBox.x(), (float) emitterBox.y(), (float) emitterBox.z());

            gl.glVertex3f((float) emitterBox.x(), (float) emitterBox.y(), (float) emitterBox.z());
            gl.glVertex3f((float) 0, (float) emitterBox.y(), (float) emitterBox.z());

            gl.glVertex3f((float) 0, (float) emitterBox.y(), (float) emitterBox.z());
            gl.glVertex3f((float) 0, (float) 0, (float) emitterBox.z());

            // connection of sides
            gl.glVertex3f((float) 0, (float) 0, (float) 0);
            gl.glVertex3f((float) 0, (float) 0, (float) emitterBox.z());

            gl.glVertex3f((float) emitterBox.x(), (float) 0, (float) 0);
            gl.glVertex3f((float) emitterBox.x(), (float) 0, (float) emitterBox.z());

            gl.glVertex3f((float) emitterBox.x(), (float) emitterBox.y(), (float) 0);
            gl.glVertex3f((float) emitterBox.x(), (float) emitterBox.y(), (float) emitterBox.z());

            gl.glVertex3f((float) 0, (float) emitterBox.y(), (float) 0);
            gl.glVertex3f((float) 0, (float) emitterBox.y(), (float) emitterBox.z());

            gl.glEnd();

        }
    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        createVbo();
        gl.glPointSize(this.emitter.getParticleSize());
        if (mode == RenderMode.REGULAR) {
            vbo.draw(gl);
            drawEmitterBox(gl);
        }
    }

}
