package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ITriangleMesh;
import computergraphics.framework.mesh.Triangle;
import computergraphics.framework.mesh.TriangleMesh;
import computergraphics.framework.mesh.Vertex;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;

/**
 * Created by alex on 10/4/16.
 */
public class TriangleMeshNode extends LeafNode {

  /**
   * VBO.
   */
  private VertexBufferObject vbo = new VertexBufferObject();

  private ITriangleMesh<Vertex, Triangle> triangleMesh;

  private Vector color;

  public TriangleMeshNode(ITriangleMesh triangleMesh) {
    this(triangleMesh, Colors.gray);
  }

  public TriangleMeshNode(ITriangleMesh triangleMesh, Vector color) {
    if (triangleMesh == null) {
      throw new IllegalArgumentException("Given TriangleMesh is null.");
    }
    this.color = color;
    this.triangleMesh = triangleMesh;
    createVbo();
  }

  private void createVbo() {
    List<RenderVertex> renderVertices = new ArrayList<>();

    int triangleIndex = 0;
    while(triangleIndex < this.triangleMesh.getNumberOfTriangles()) {
      Triangle triangle = this.triangleMesh.getTriangle(triangleIndex);
      Vector p0 = getTriangleVectorByIndex(triangle, 0);
      Vector p1 = getTriangleVectorByIndex(triangle, 1);
      Vector p2 = getTriangleVectorByIndex(triangle, 2);

      renderVertices.add(new RenderVertex(p0, triangle.getNormal(), this.color));
      renderVertices.add(new RenderVertex(p1, triangle.getNormal(), this.color));
      renderVertices.add(new RenderVertex(p2, triangle.getNormal(), this.color));
      triangleIndex++;
    }
    vbo.Setup(renderVertices, GL2.GL_TRIANGLES);
  }

  /**
   * Fetches position as a VectorService, of given triangle. The vertex is specified trough index.
   * However, given index is one of the triangle points. {0, 1, 2}
   */
  private Vector getTriangleVectorByIndex(Triangle triangle, int index) {
    Vertex vertex = this.triangleMesh.getVertex(triangle.getVertexIndex(index));
    return vertex.getPosition();
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
      vbo.draw(gl);
    }
  }
}
