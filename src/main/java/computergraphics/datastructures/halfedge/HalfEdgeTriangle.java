package computergraphics.datastructures.halfedge;

import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.Vertex;

/**
 * A facet has a reference to one of its half edges. This datastructure
 * represents a general mesh (triangle, quad, ...). However, we only use
 * triangle meshes here.
 * 
 * @author Philipp Jenke
 *
 */
public class HalfEdgeTriangle {

  /**
   * One of the half edges around the facet.
   */
  private HalfEdge halfEdge;

  /**
   * Facet normal
   */
  private Vector normal;

  public HalfEdge getHalfEdge() {
    return halfEdge;
  }

  public void setHalfEdge(HalfEdge halfEdge) {
    this.halfEdge = halfEdge;
  }

  @Override
  public String toString() {
    return "Triangular Facet";
  }

  public Vector getNormal() {
    return normal;
  }

  public void setNormal(Vector normal) {
    this.normal = normal;
  }

  /**
   * Compute the area of the facet. Area of the facet.
   * 
   * @return Area of the triangle.
   */
  public double getArea() {
    Vector v0 = halfEdge.getStartVertex().getPosition();
    Vector v1 = halfEdge.getNext().getStartVertex().getPosition();
    Vector v2 = halfEdge.getNext().getNext().getStartVertex().getPosition();
    return v1.subtract(v0).cross(v2.subtract(v0)).getNorm() / 2.0;
  }

  /**
   * Checks if given lightPosition points to given light source.
   *
   * n = normal of facet.
   * x = any point on facet.
   *
   */
  public boolean pointsTo(Vector lightPosition) {
    Vector n = this.getNormal();
    Vector x = getHalfEdge().getStartVertex().getPosition();
    return 0 < (x.multiply(n) - n.multiply(lightPosition));
  }

  /**
   * Compute the centroid (center of mass) of the triangle.
   * 
   * @return Centroid of the triangle.
   */
  public Vector getCentroid() {
    Vector v0 = halfEdge.getStartVertex().getPosition();
    Vector v1 = halfEdge.getNext().getStartVertex().getPosition();
    Vector v2 = halfEdge.getNext().getNext().getStartVertex().getPosition();
    return (v0.add(v1).add(v2)).multiply(1.0 / 3.0);
  }

  public int getVertexIndex(int i) {
    throw new IllegalArgumentException(
        "Invalid operation for half edge datastructure");
  }

  public int getTexCoordIndex(int i) {
    throw new IllegalArgumentException("Not implemented yet.");
  }
}
