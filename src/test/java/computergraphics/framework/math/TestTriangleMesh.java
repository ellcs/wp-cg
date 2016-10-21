package computergraphics.framework.math;

import org.junit.Before;
import org.junit.Test;

import computergraphics.framework.mesh.TriangleMesh;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by alex on 10/4/16.
 */
public class TestTriangleMesh {

  TriangleMesh triangleMesh;

  @Before
  public void setUp() {
    triangleMesh = new TriangleMesh();
    // add vertices
    triangleMesh.addVertex(new Vector(0, 0, 0));
    triangleMesh.addVertex(new Vector(0, 0, 0));
    triangleMesh.addVertex(new Vector(0, 0, 0));
    triangleMesh.addVertex(new Vector(0, 0, 0));
    // add triangles
    triangleMesh.addTriangle(0, 1, 2);
    triangleMesh.addTriangle(0, 1, 2);
    triangleMesh.addTriangle(0, 1, 2);
  }

  @Test
  public void testVertexCount() {
    assertEquals(triangleMesh.getNumberOfVertices(), 4);
  }

  @Test
  public void testTriangleCount() {
    assertEquals(triangleMesh.getNumberOfTriangles(), 3);
  }
}
