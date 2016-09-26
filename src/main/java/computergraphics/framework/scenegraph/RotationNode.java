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
 * Rotate all child nodes.
 *
 */
public class RotationNode extends InnerNode {

  private double angle;
  private Vector vector;
  private Matrix matrix;

  public RotationNode(Vector vector, double angle) {
    this.angle = angle;
    this.vector = vector;
  }

  public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    this.matrix = Matrix.getRotationMatrix4(vector, angle);
    super.traverse(gl, mode, matrix.multiply(modelMatrix));
  }

  public void timerTick(int counter) {
    this.angle += 0.005;
    // prevent over/under flow
    this.angle = this.angle % (Math.PI*2);
  }

}
