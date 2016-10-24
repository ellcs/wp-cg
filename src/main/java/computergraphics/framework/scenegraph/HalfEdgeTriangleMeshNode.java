package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.datastructures.halfedge.HalfEdge;
import computergraphics.datastructures.halfedge.HalfEdgeTriangle;
import computergraphics.datastructures.halfedge.HalfEdgeVertex;
import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.HalfEdgeTriangleMesh;
import computergraphics.framework.mesh.ITriangleMesh;
import computergraphics.framework.mesh.Vertex;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;

enum NormalType {
  triangle,
  vertex
}

/**
 * Created by alex on 10/21/16.
 */
public class HalfEdgeTriangleMeshNode extends LeafNode {

  /**
   * VBO.
   */
  private VertexBufferObject vbo = new VertexBufferObject();

  private ITriangleMesh<HalfEdgeVertex, HalfEdgeTriangle> triangleMesh;

  private NormalType normalType;

  private Vector color;

  public HalfEdgeTriangleMeshNode(HalfEdgeTriangleMesh triangleMesh) {
    this(triangleMesh, NormalType.vertex, Colors.gray);
  }

  public HalfEdgeTriangleMeshNode(HalfEdgeTriangleMesh triangleMesh, NormalType normalType, Vector color) {
    if (triangleMesh == null) {
      throw new IllegalArgumentException("Given TriangleMesh is null.");
    }
    this.color = color;
    this.normalType = normalType;
    this.triangleMesh = triangleMesh;
    createVbo();
  }

  private void createVbo() {
    List<RenderVertex> renderVertices = new ArrayList<>();

    int triangleIndex = 0;
    while(triangleIndex < this.triangleMesh.getNumberOfTriangles()) {
      HalfEdgeTriangle triangle = this.triangleMesh.getTriangle(triangleIndex);
      HalfEdgeVertex v0 = getTriangleVertexByIndex(triangle, 0);
      HalfEdgeVertex v1 = getTriangleVertexByIndex(triangle, 1);
      HalfEdgeVertex v2 = getTriangleVertexByIndex(triangle, 2);

      Vector p0 = v0.getPosition();
      Vector p1 = v1.getPosition();
      Vector p2 = v2.getPosition();

      if (this.normalType.equals(NormalType.triangle)) {
        renderVertices.add(new RenderVertex(p0, triangle.getNormal(), this.color));
        renderVertices.add(new RenderVertex(p1, triangle.getNormal(), this.color));
        renderVertices.add(new RenderVertex(p2, triangle.getNormal(), this.color));
      } else if (this.normalType.equals(NormalType.vertex)) {
        renderVertices.add(new RenderVertex(p0, v0.getNormal(), this.color));
        renderVertices.add(new RenderVertex(p1, v1.getNormal(), this.color));
        renderVertices.add(new RenderVertex(p2, v2.getNormal(), this.color));
      } else {
        throw new IllegalArgumentException("Unknown NormalType: " + this.normalType + ".");
      }

      triangleIndex++;
    }
    vbo.Setup(renderVertices, GL2.GL_TRIANGLES);
  }

  /**
   * Fetches position as a vector, of given triangle. The vertex is specified trough index.
   * However, given index is one of the triangle points. {0, 1, 2}
   */
  private HalfEdgeVertex getTriangleVertexByIndex(HalfEdgeTriangle triangle, int index) {
    HalfEdge edge = triangle.getHalfEdge();
    for (int i = 0; i < index; i++) {
      edge = edge.getNext();
    }
    return edge.getStartVertex();
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
      vbo.draw(gl);
    }
  }
}
