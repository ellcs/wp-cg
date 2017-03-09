package computergraphics.framework.scenegraph.particle_system;

import com.jogamp.opengl.GL2;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.VertexBufferObject;
import computergraphics.framework.scenegraph.LeafNode;
import computergraphics.framework.scenegraph.TimerTickable;
import computergraphics.particle_system.Range;
import computergraphics.particle_system.services.DeltaTimeCalculatorService;
import computergraphics.particle_system.IEmitter;

import static com.jogamp.opengl.GL.GL_LINES;

/**
 * Created by ellcs on 06.03.17.
 */
public class EmitterNode extends LeafNode implements TimerTickable {

    IEmitter emitter;

    VertexBufferObject vbo;

    DeltaTimeCalculatorService timeCalculator;

    public EmitterNode(IEmitter emitter) {
        this.emitter = emitter;
        this.vbo = new VertexBufferObject();
        this.timeCalculator = new DeltaTimeCalculatorService();
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
            Range emitterRange = this.emitter.getEmitterRange();
            Vector position = emitterRange.getPosition();
            Vector sum = emitterRange.getSum();

            gl.glLineWidth(2.5f);
            gl.glColor3f((float) this.color.get(0), (float) this.color.get(1), (float) this.color.get(2));
            gl.glBegin(GL_LINES);

            // side one:
            gl.glVertex3f((float) position.x(), (float) position.y(), (float) position.z());
            gl.glVertex3f((float) sum.x(), (float) position.y(), (float) position.z());

            gl.glVertex3f((float) sum.x(), (float) position.y(), (float) position.z());
            gl.glVertex3f((float) sum.x(), (float) sum.y(), (float) position.z());

            gl.glVertex3f((float) sum.x(), (float) sum.y(), (float) position.z());
            gl.glVertex3f((float) position.x(), (float) sum.y(), (float) position.z());

            gl.glVertex3f((float) position.x(), (float) sum.y(), (float) position.z());
            gl.glVertex3f((float) position.x(), (float) position.y(), (float) position.z());

            // side two:
            gl.glVertex3f((float) position.x(), (float) position.y(), (float) sum.z());
            gl.glVertex3f((float) sum.x(), (float) position.y(), (float) sum.z());

            gl.glVertex3f((float) sum.x(), (float) position.y(), (float) sum.z());
            gl.glVertex3f((float) sum.x(), (float) sum.y(), (float) sum.z());

            gl.glVertex3f((float) sum.x(), (float) sum.y(), (float) sum.z());
            gl.glVertex3f((float) position.x(), (float) sum.y(), (float) sum.z());

            gl.glVertex3f((float) position.x(), (float) sum.y(), (float) sum.z());
            gl.glVertex3f((float) position.x(), (float) position.y(), (float) sum.z());

            // connection of sides
            gl.glVertex3f((float) position.x(), (float) position.y(), (float) position.z());
            gl.glVertex3f((float) position.x(), (float) position.y(), (float) sum.z());

            gl.glVertex3f((float) sum.x(), (float) position.y(), (float) position.z());
            gl.glVertex3f((float) sum.x(), (float) position.y(), (float) sum.z());

            gl.glVertex3f((float) sum.x(), (float) sum.y(), (float) position.z());
            gl.glVertex3f((float) sum.x(), (float) sum.y(), (float) sum.z());

            gl.glVertex3f((float) position.x(), (float) sum.y(), (float) position.z());
            gl.glVertex3f((float) position.x(), (float) sum.y(), (float) sum.z());

            gl.glEnd();

        }
    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        createVbo();
        gl.glPointSize(this.emitter.getParticleSize());
//        gl.glEnable(GL2.GL_POINT_SMOOTH);
        if (mode == RenderMode.REGULAR) {
            vbo.draw(gl);
            drawEmitterBox(gl);
        }
    }

}
