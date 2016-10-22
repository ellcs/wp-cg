/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.framework.mesh;

import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Texture;

/**
 * Shared interface for all triangle mesh implementations.
 * 
 * @author Philipp Jenke
 */
public interface ITriangleMesh<V, T> {

  /**
   * Add a new vertex (given by position) to the vertex list. The new vertex is
   * appended to the end of the list.
   */
  int addVertex(Vector position);

  /**
   * Add a new triangle to the mesh with the vertex indices a, b, c. The index
   * of the first vertex is 0.
   */
  void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3);

  /**
   * Add triangle by vertex indices and corresponding texture coordinate
   * indices.
   */
  void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3,
      int texCoordIndex1, int texCoordIndex2, int texCoordIndex3);

  /**
   * Add texture coordinate to mesh.
   */
  void addTextureCoordinate(Vector t);

  /**
   * Set a texture object for the mesh.
   */
  void setTexture(Texture texture);

  /**
   * Clear mesh - remove all triangles and vertices.
   */
  void clear();

  int getNumberOfTriangles();

  int getNumberOfVertices();

  V getVertex(int index);

  T getTriangle(int triangleIndex);

  Vector getTextureCoordinate(int index);

  Texture getTexture();

  /**
   * When mesh is fully loaded, call finishLoad().
   */
  void finishLoad();

  /**
   * Compute the triangles normals.
   */
  void computeTriangleNormals();

  /**
   * Create a mesh of the shadow polygons.
   * 
   * lightPosition: Position of the light source. extend: Length of the polygons
   * shadowPolygonMesh: Result is put in there
   */
  void createShadowPolygons(Vector lightPosition, float extend,
      ITriangleMesh shadowPolygonMesh);
}
