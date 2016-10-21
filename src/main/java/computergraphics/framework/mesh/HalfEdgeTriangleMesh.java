package computergraphics.framework.mesh;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import computergraphics.datastructures.halfedge.HalfEdge;
import computergraphics.datastructures.halfedge.HalfEdgeTriangle;
import computergraphics.datastructures.halfedge.HalfEdgeVertex;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Texture;

/**
 * Created by alex on 10/21/16.
 */
public class HalfEdgeTriangleMesh implements ITriangleMesh {

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

    HalfEdgeVertex v1 = this.vertices.get(vertexIndex1);
    HalfEdgeVertex v2 = this.vertices.get(vertexIndex2);
    HalfEdgeVertex v3 = this.vertices.get(vertexIndex3);

    h1.setStartVertex(v1);
    h2.setStartVertex(v2);
    h3.setStartVertex(v3);

    h1.setNext(h2);
    h2.setNext(h3);
    h3.setNext(h1);

    h1.setFacet(triangle);
    h2.setFacet(triangle);
    h3.setFacet(triangle);

    triangle.setHalfEdge(h1);
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
  public Vertex getVertex(int index) {
    HalfEdgeVertex vertex = this.vertices.get(index);
    return new Vertex(vertex.getPosition(), vertex.getNormal());
  }

  @Override
  public Triangle getTriangle(int triangleIndex) {
    HalfEdgeTriangle triangle = this.triangles.get(triangleIndex);
    HalfEdge h = triangle.getHalfEdge();
    HalfEdgeVertex v1 = h.getStartVertex();
    h = h.getNext();
    HalfEdgeVertex v2 = h.getStartVertex();
    h = h.getNext();
    HalfEdgeVertex v3 = h.getStartVertex();
//    Triangle triangle1 = new Triangle();
    return null;
  }

  @Override
  public Vector getTextureCoordinate(int index) {
    throw new NotImplementedException();
  }

  @Override
  public Texture getTexture() {
    throw new NotImplementedException();
  }

  private void finishHalfEdges() {
    // set
    for (HalfEdge edge : this.edges) {
      HalfEdgeVertex vertex = edge.getStartVertex();
      vertex.setHalfEgde(edge);
    }
  }

  @Override
  public void computeTriangleNormals() {

  }

  private void calculateTriangleNormal(HalfEdgeTriangle triangle) {
    
  }


  @Override
  public void createShadowPolygons(Vector lightPosition, float extend, ITriangleMesh shadowPolygonMesh) {

  }
}
