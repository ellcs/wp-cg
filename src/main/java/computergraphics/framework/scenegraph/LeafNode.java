package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.framework.rendering.ShaderAttributes;

/**
 * A leaf node allows to draw OpenGl content.
 */
public abstract class LeafNode extends INode {

  protected Vector color =  new Vector(0.25, 0.75, 0.25, 1);

  @Override
  public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    ShaderAttributes.getInstance().setModelMatrixParameter(gl, modelMatrix);
    ShaderAttributes.getInstance().setViewMatrixParameter(gl,
        getRootNode().getCamera().getViewMatrix());
    drawGL(gl, mode, modelMatrix);
  }

  @Override
  public void timerTick(int counter) {
  }

  /**
   * Draw GL content.
   */
  public abstract void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix);
}
