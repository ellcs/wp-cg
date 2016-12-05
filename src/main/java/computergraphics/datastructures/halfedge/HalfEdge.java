package computergraphics.datastructures.halfedge;

import computergraphics.framework.math.Vector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A half edge has references to the next edge within the current facet, the
 * opposite edge, its start vertex and the facet it belongs to.
 * 
 * @author Philipp Jenke
 *
 */
public class HalfEdge {

  /**
   * Reference to the next edge in the mesh.
   */
  private HalfEdge next;

  /**
   * Reference to the opposite edge in the mesh.
   */
  private HalfEdge opposite;

  /**
   * Start vertex of the half edge.
   */
  private HalfEdgeVertex startVertex;

  /**
   * The half edge belongs to this facet.
   */
  private HalfEdgeTriangle facet;

  public HalfEdge getNext() {
    return next;
  }

  public void setNext(HalfEdge next) {
    this.next = next;
  }

  public HalfEdge getOpposite() {
    return opposite;
  }

  public void setOpposite(HalfEdge opposite) {
    this.opposite = opposite;
  }

  public HalfEdgeVertex getStartVertex() {
    return startVertex;
  }

  public void setStartVertex(HalfEdgeVertex startVertex) {
    this.startVertex = startVertex;
  }

  public HalfEdgeTriangle getFacet() {
    return facet;
  }

  public boolean hasOpposite() {
    return this.opposite != null;
  }

  /**
   * Calculates if edge is a orthogonal edge to given light source.
   * @param lightPosition of light source.
   * @return true if and only if it is orthogonal.
   */
  public boolean isSilhouetteEdge(Vector lightPosition) {
    HalfEdgeTriangle triangle1 = getFacet();
    HalfEdgeTriangle triangle2 = getOpposite().getFacet();
    Vector n1 = triangle1.getNormal();
    
    throw new NotImplementedException();
  }

  public void setFacet(HalfEdgeTriangle facet) {
    this.facet = facet;
  }

  @Override
  public String toString() {
    return "Half Edge";
  }
}
