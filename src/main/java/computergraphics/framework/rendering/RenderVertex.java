package computergraphics.framework.rendering;

import computergraphics.framework.math.Vector;

/**
 * Helping data structure to represent a render vertex in a @VertexBufferObject.
 * 
 * @author Philipp Jenke
 */
public class RenderVertex {

  public RenderVertex(Vector position, Vector normal, Vector color) {
    if (color.getDimension() != 4) {
      throw new IllegalArgumentException("Color needs four values.");
    }
    this.position = position;
    this.normal = normal;
    this.color = color;
  }

  /**
   * 3D position.
   */
  public Vector position;

  /**
   * 3D normal.
   */
  public Vector normal;

  /**
   * 4D color.
   */
  public Vector color;

}
