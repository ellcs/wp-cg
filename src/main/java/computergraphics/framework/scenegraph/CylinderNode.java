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

import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;

/**
 * Geometry node for a sphere with arbitary radius, centered at the origin.
 * 
 * @author Philipp Jenke
 */
public class CylinderNode extends LeafNode {

  /**
   * Sphere radius.
   */
  private double radius;

  /**
   * Cylinder height.
   */
  private double height;

  /**
   * Resolution (in one dimension) of the mesh.
   * Higher resolution means more quads.
   */
  private int resolution;

  /**
   * VBO.
   */
  private VertexBufferObject vbo;

  /**
   * Constructor.
   */
  public CylinderNode(double radius, double height, int resolution) {
    this(radius, height, resolution, new Vector(0.128,0,0,1));
  }

  public CylinderNode(double radius, double height, int resolution, Vector color) {
    this.radius = radius;
    this.resolution = resolution;
    this.height = height;
    this.color = color;
    vbo = new VertexBufferObject();
    createVbo();
  }

  private void createVbo() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

    float dPhi = (float) (Math.PI * 2.0 / resolution);
    float dH = (float) (height / resolution);

    // hs stands for height step
    for (int hs = 0; hs < resolution; hs++) {
      // cs stands for circle step
      for (int cs = 0; cs < resolution; cs++) {
        Vector p1 = evaluateCylinderPoint(dPhi * cs,        hs * dH);
        Vector p2 = evaluateCylinderPoint(dPhi * cs,       (hs + 1) * dH);
        Vector p3 = evaluateCylinderPoint(dPhi * (1 + cs), (hs + 1) * dH);
        Vector p4 = evaluateCylinderPoint(dPhi * (1 + cs),  hs * dH);

        Vector u = p4.subtract(p1);
        Vector t1 = p2.subtract(p1);
        Vector t2 = p4.subtract(p3);
        Vector normal;
        if (t1.getNorm() < 1e-5) {
          normal = u.cross(t2).getNormalized();
        } else {
          normal = u.cross(t1).getNormalized();
        }
        AddSideVertices(renderVertices, p1, p2, p3, p4, normal, this.color);
      }
    }
    vbo.Setup(renderVertices, GL2.GL_QUADS);
  }



  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
      vbo.draw(gl);
    }
  }

  /**
   * Compute a surface point for given sphere coordinates.
   */
  private Vector evaluateCylinderPoint(float theta, float z) {
    float x = (float) (radius * Math.cos(theta));
    float y = (float) (radius * Math.sin(theta));
    return new Vector(x, y, z);
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
}
