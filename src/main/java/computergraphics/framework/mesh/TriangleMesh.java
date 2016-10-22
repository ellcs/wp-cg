package computergraphics.framework.mesh;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Texture;

/**
 * Created by alex on 10/4/16.
 */
public class TriangleMesh implements ITriangleMesh {

  private List<Vertex> verticies;

  private List<Triangle> triangles;

  public TriangleMesh() {
    // clear triangles to
    clear();
  }

  @Override
  public int addVertex(Vector position) {
    this.verticies.add(new Vertex(position));
    return this.verticies.size()-1;
  }

  @Override
  public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
    this.triangles.add(new Triangle(vertexIndex1, vertexIndex2, vertexIndex3));
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
    this.verticies = new ArrayList<>();
    this.triangles = new ArrayList<>();
  }

  @Override
  public int getNumberOfTriangles() {
    return this.triangles.size();
  }

  @Override
  public int getNumberOfVertices() {
    return this.verticies.size();
  }

  @Override
  public Vertex getVertex(int index) {
    return this.verticies.get(index);
  }

  @Override
  public Triangle getTriangle(int triangleIndex) {
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
    computeTriangleNormals();
  }

  @Override
  public void computeTriangleNormals() {
    for (Triangle triangle : this.triangles) {
      computeNormal(triangle);
    }
    // this.triangles.stream().forEach(this::computeNormal);
  }

  @Override
  public void createShadowPolygons(Vector lightPosition, float extend,
                                   ITriangleMesh shadowPolygonMesh) {
    throw new NotImplementedException();
  }

  /**
   * Computes and sets normal for given triangle.
   */
  private void computeNormal(Triangle triangle) {
    Vector p0 = getTriangleVectorByIndex(triangle, 0);
    Vector p1 = getTriangleVectorByIndex(triangle, 1);
    Vector p2 = getTriangleVectorByIndex(triangle, 2);

    Vector u = p1.subtract(p0);
    Vector v = p2.subtract(p0);
    Vector n = u.cross(v);
//    triangle.setNormal(n);
    triangle.setNormal(n.getNormalized());
  }

  public Vector getTriangleVectorByIndex(Triangle triangle, int index) {
    Vertex vertex = this.verticies.get(triangle.getVertexIndex(index));
    return vertex.getPosition();
  }

}
