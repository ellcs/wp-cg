/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Matrix;
import computergraphics.math.Vector;

/**
 * Rotate all child nodes.
 *
 */
public class RotationNode extends InnerNode implements TimerTickable {

  private double angle;
  private double animationSpeed;
  private Vector vector;
  private Matrix matrix;

  public RotationNode(Vector vector, double angle) {
    this(vector, angle, 0.001);
  }

  public RotationNode(Vector vector, double angle, double animationSpeed) {
    this.angle = angle;
    this.vector = vector;
    this.animationSpeed = animationSpeed;
  }

  public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    this.matrix = Matrix.getRotationMatrix4(vector, angle);
    super.traverse(gl, mode, matrix.multiply(modelMatrix));
  }

  @Override
  public void timerTick(int counter) {
    this.angle += animationSpeed;
  }

}
