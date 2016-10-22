package computergraphics.framework.mesh;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import computergraphics.datastructures.halfedge.HalfEdge;
import computergraphics.datastructures.halfedge.HalfEdgeTriangle;
import computergraphics.datastructures.halfedge.HalfEdgeVertex;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Texture;

/**
 * Created by alex on 10/21/16.
 */
public class HalfEdgeTriangleMesh implements ITriangleMesh<HalfEdgeVertex, HalfEdgeTriangle> {

  List<HalfEdgeVertex> vertices;

  List<HalfEdge> edges;

  List<HalfEdgeTriangle> triangles;

  public HalfEdgeTriangleMesh() {
    // clear to instantiate
    // instance variables
    clear();
  }

  @Override
  public int addVertex(Vector position) {
    this.vertices.add(new HalfEdgeVertex(position));
    return this.vertices.size()-1;
  }

  @Override
  public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
    HalfEdgeTriangle triangle = new HalfEdgeTriangle();

    HalfEdge h1 = new HalfEdge();
    HalfEdge h2 = new HalfEdge();
    HalfEdge h3 = new HalfEdge();

    h1.setStartVertex(this.vertices.get(vertexIndex1));
    h2.setStartVertex(this.vertices.get(vertexIndex2));
    h3.setStartVertex(this.vertices.get(vertexIndex3));

    h1.setNext(h2);
    h2.setNext(h3);
    h3.setNext(h1);

    h1.setFacet(triangle);
    h2.setFacet(triangle);
    h3.setFacet(triangle);

    triangle.setHalfEdge(h1);

    this.triangles.add(triangle);
  }

  @Override
  public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3,
                          int texCoordIndex1, int texCoordIndex2, int texCoordIndex3) {
    throw new NotImplementedException();
  }

  @Override
  public void addTextureCoordinate(Vector t) {
    throw new NotImplementedException();
  }

  @Override
  public void setTexture(Texture texture) {
    throw new NotImplementedException();
  }

  @Override
  public void clear() {
    this.vertices = new ArrayList<>();
    this.edges = new ArrayList<>();
    this.triangles = new ArrayList<>();
  }

  @Override
  public int getNumberOfTriangles() {
    return this.triangles.size();
  }

  @Override
  public int getNumberOfVertices() {
    return this.vertices.size();
  }

  @Override
  public HalfEdgeVertex getVertex(int index) {
    return this.vertices.get(index);
  }

  @Override
  public HalfEdgeTriangle getTriangle(int triangleIndex) {
    return this.triangles.get(triangleIndex);
  }

  @Override
  public Vector getTextureCoordinate(int index) {
    throw new NotImplementedException();
  }

  @Override
  public Texture getTexture() {
    throw new NotImplementedException();
  }

  @Override
  public void finishLoad() {
    // easiest way to set vertexes half edge :)
    for (HalfEdge edge : this.edges) {
      HalfEdgeVertex vertex = edge.getStartVertex();
      vertex.setHalfEgde(edge);
    }

    // we do have edge e1 and we're looking for e2.
    // find all edges (tmp) with source of e1.next.source and tmp.next.source == e1.source
    // then e2 is tmp
    for (HalfEdge edge : this.edges) {
      HalfEdge opposite = findOpposite(edge);
      edge.setOpposite(opposite);
    }
  }

  private HalfEdge findOpposite(HalfEdge halfEdge) {
    HalfEdgeVertex sourceVertex = halfEdge.getStartVertex();
    HalfEdgeVertex targetVertex = halfEdge.getNext().getStartVertex();
    for (HalfEdge edge : this.edges) {
      HalfEdgeVertex oppositeSource = edge.getStartVertex();
      HalfEdgeVertex oppositeTarget = edge.getNext().getStartVertex();
      if (sourceVertex.equals(oppositeTarget)
          && targetVertex.equals(oppositeSource)) {
        return edge;
      }
    }
    return null;
  }

  @Override
  public void computeTriangleNormals() {
    for (HalfEdgeTriangle triangle : this.triangles) {
      computeTriangleNormal(triangle);
    }
  }

  public void computeTriangleNormal(HalfEdgeTriangle triangle) {
    HalfEdge h = triangle.getHalfEdge();
    HalfEdgeVertex v1 = h.getStartVertex();
    h = h.getNext();
    HalfEdgeVertex v2 = h.getStartVertex();
    h = h.getNext();
    HalfEdgeVertex v3 = h.getStartVertex();

    Vector p0 = v1.getPosition();
    Vector p1 = v2.getPosition();
    Vector p2 = v3.getPosition();

    // calculate normal
    Vector u = p1.subtract(p0);
    Vector v = p2.subtract(p0);
    Vector n = (u.cross(v)).getNormalized();

    triangle.setNormal(n);
  }


  public void calculateVertecyNormals() {
    for (HalfEdgeVertex vertex : this.vertices) {
      computeVertexNormal(vertex);
    }
  }

  public void computeVertexNormal(HalfEdgeVertex vertex) {
    throw new NotImplementedException();
  }

  private Set<HalfEdgeTriangle> getFacettsAroundVertex(HalfEdgeVertex vertex) {
    throw new NotImplementedException();
  }

  @Override
  public void createShadowPolygons(Vector lightPosition, float extend, ITriangleMesh shadowPolygonMesh) {
    throw new NotImplementedException();
  }

  private void noImpl() {
    throw new NotImplementedException();
  }
}
