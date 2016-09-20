/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;

/**
 * Translate all child nodes.
 * 
 * @author Philipp Jenke
 */
public class RotationNode extends InnerNode {

  private double angle;
  private Matrix matrix;

  public RotationNode(Vector vector, double angle) {
    this.angle = angle;
    this.matrix = Matrix.getRotationMatrix3(vector, angle);
  }

  public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
      super.traverse(gl, mode, matrix.multiply(modelMatrix));
  }

  public void timerTick(int counter) {
    super.timerTick(counter);
  }

}
