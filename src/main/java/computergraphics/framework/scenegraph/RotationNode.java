/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.framework.math.Axis;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;

/**
 * Rotate all child nodes.
 *
 */
public class RotationNode extends InnerNode {

  private double angle;
  private Vector vector;
  private Axis   axis;
  private Matrix matrix;

  public RotationNode(Axis axis, double angle) {
    this.axis  = axis;
    this.angle = angle;
//    this.vector = vector;

  }

  public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    this.matrix = Matrix.getRotationMatrix4(axis, angle);
    super.traverse(gl, mode, matrix.multiply(modelMatrix));
  }

  public void inc() {
    this.angle += 0.1;
  }

  public void timerTick(int counter) {
    super.timerTick(counter);
  }

}
