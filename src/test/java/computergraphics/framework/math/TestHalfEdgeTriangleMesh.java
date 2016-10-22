package computergraphics.framework.math;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.halfedge.HalfEdgeTriangle;
import computergraphics.framework.mesh.HalfEdgeTriangleMesh;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by alex on 10/21/16.
 */
public class TestHalfEdgeTriangleMesh {

  HalfEdgeTriangleMesh halfEdgeTriangleMesh;

  @Before
  public void setUp() {
    halfEdgeTriangleMesh = new HalfEdgeTriangleMesh();

    Vector vec1 = new Vector(0, 0, 1);
    Vector vec2 = new Vector(1, 0, 1);
    Vector vec3 = new Vector(1, 0, 0);
    Vector vec4 = new Vector(-1, 0, -1);

    halfEdgeTriangleMesh.addVertex(vec1);
    halfEdgeTriangleMesh.addVertex(vec2);
    halfEdgeTriangleMesh.addVertex(vec3);
    halfEdgeTriangleMesh.addVertex(vec4);

    halfEdgeTriangleMesh.addTriangle(0, 1, 2);
    halfEdgeTriangleMesh.addTriangle(3, 4, 5);
  }

  @Test
  public void testVertexCount() {
    assertEquals(halfEdgeTriangleMesh.getNumberOfVertices(), 6);
  }

  @Test
  public void testTriangleCount() {
    assertEquals(halfEdgeTriangleMesh.getNumberOfTriangles(), 2);
  }

  @Test
  public void testCalculateTriangleNormal() {
    HalfEdgeTriangle triangle = halfEdgeTriangleMesh.getTriangle(0);
    halfEdgeTriangleMesh.calculateTriangleNormal(triangle);

    Vector expectedNormal = new Vector(0, 1, 0);
    assertEquals(triangle.getNormal(), expectedNormal);
  }

  @Test
  public void testBuildHalfEdgeStructure() {
  }
}
