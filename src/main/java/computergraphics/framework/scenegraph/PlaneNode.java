/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;

/**
 * Representation of a cuboid with different dimensions in x-, y- and
 * z-direction.
 *
 * @author Philipp Jenke
 */
public class PlaneNode extends LeafNode {

  /**
   * Cube side length
   */
  private double sideLength;

  /**
   * VBO.
   */
  private VertexBufferObject vbo = new VertexBufferObject();

  /**
   * Constructor.
   */
  public PlaneNode(double sideLength) {
    this(sideLength, new Vector(0.25, 0.25, 0.75, 1));
  }

  public PlaneNode(double sideLength, Vector color) {
    this.sideLength = sideLength;
    this.color = color;
    createVbo();
  }

  private void createVbo() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

    double halfSideLength = sideLength / 2;
    Vector p0 = new Vector(-halfSideLength, 0.0, halfSideLength);
    Vector p1 = new Vector(halfSideLength, 0.0, halfSideLength);
    Vector p2 = new Vector(halfSideLength, 0.0, -halfSideLength);
    Vector p3 = new Vector(-halfSideLength, 0.0, -halfSideLength);
    Vector n0 = new Vector(0, -1, 0);
    
    AddSideVertices(renderVertices, p0, p1, p2, p3, n0, this.color);

    vbo.Setup(renderVertices, GL2.GL_QUADS);
  }

  /**
   * Add 4 vertices to the array
   */
  private void AddSideVertices(List<RenderVertex> renderVertices, Vector p0,
      Vector p1, Vector p2, Vector p3, Vector normal, Vector color) {
    renderVertices.add(new RenderVertex(p3, normal, color));
    renderVertices.add(new RenderVertex(p2, normal, color));
    renderVertices.add(new RenderVertex(p1, normal, color));
    renderVertices.add(new RenderVertex(p0, normal, color));
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
      vbo.draw(gl);
    }
  }
}
