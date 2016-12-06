package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import computergraphics.datastructures.halfedge.HalfEdge;
import computergraphics.datastructures.halfedge.HalfEdgeTriangle;
import computergraphics.datastructures.halfedge.HalfEdgeVertex;
import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ShadowTriangleMesh;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;

public class JenkeTriangleMeshNode extends LeafNode {

  public enum NormalType {
    triangle,
    vertex
  }

  private NormalType normalType = NormalType.triangle;

  /**
   * Contained triangle mesh.
   */
  private ShadowTriangleMesh mesh;

  /**
   * This node is used to render the shadow polygon mesh
   */
  private JenkeTriangleMeshNode shadowPolygonNode = null;


  /**
   * Position of light source.
   */
  private Vector lightPosition;

  /**
   * Color used for rendering (RGBA)
   */
  private Vector color = new Vector(0.75, 0.25, 0.25, 1);

  /**
   * Debugging: Show normals.
   */
  private boolean showNormals = false;

  private boolean showSilhouette = false;
  /**
   * VBOs
   */
  private VertexBufferObject vbo = new VertexBufferObject();
  private VertexBufferObject vboSilhouette = new VertexBufferObject();
  private VertexBufferObject vboNormals = new VertexBufferObject();

  public JenkeTriangleMeshNode(ShadowTriangleMesh mesh, Vector lightPosition, boolean showSilhouette) {
    this.mesh = mesh;
    this.lightPosition = lightPosition;
    this.showSilhouette = showSilhouette;
    vbo.Setup(createRenderVertices(), GL2.GL_TRIANGLES);
    if (this.showSilhouette) {
      vboSilhouette.Setup(createSilhouette(), GL2.GL_LINES);
    }

//    vboNormals.Setup(createRenderVerticesNormals(), GL2.GL_LINES);
  }

  private List<RenderVertex> createSilhouette() {
    List<RenderVertex> renderVertices = new ArrayList<>();
    List<HalfEdge> silhouetteEdges = mesh.getSilhouetteEdges(this.lightPosition);
    for (HalfEdge silhouetteEdge : silhouetteEdges) {
      Vector start = silhouetteEdge.getStartVertex().getPosition();
      Vector end = silhouetteEdge.getOpposite().getStartVertex().getPosition();
      renderVertices.add(new RenderVertex(start, new Vector(1,1,1), Colors.black));
      renderVertices.add(new RenderVertex(end, new Vector(1,1,1), Colors.black));
    }
    return renderVertices;
  }

  /**
   * Create vbo data for mesh rendering
   */
  private List<RenderVertex> createRenderVertices() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();
    int triangleIndex = 0;
    while(triangleIndex < this.mesh.getNumberOfTriangles()) {
      HalfEdgeTriangle triangle = this.mesh.getTriangle(triangleIndex);
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
    return renderVertices;
  }

  /**
   * Create vbo data for normal rendering.
   */
  private List<RenderVertex> createRenderVerticesNormals() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();
    double normalScale = 0.1;
    Vector color = new Vector(0.5, 0.5, 0.5, 1);
    for (int i = 0; i < mesh.getNumberOfTriangles(); i++) {
      HalfEdgeTriangle t = mesh.getTriangle(i);
      Vector p = mesh.getVertex(t.getVertexIndex(0)).getPosition()
          .add(mesh.getVertex(t.getVertexIndex(1)).getPosition())
          .add(mesh.getVertex(t.getVertexIndex(2)).getPosition())
          .multiply(1.0 / 3.0);
      renderVertices.add(new RenderVertex(p, t.getNormal(), color));
      renderVertices.add(new RenderVertex(
          p.add(t.getNormal().multiply(normalScale)), t.getNormal(), color));
    }
    return renderVertices;
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    // Use texture if texture object != null
    if (mesh.getTexture() != null) {
      if (!mesh.getTexture().isLoaded()) {
        mesh.getTexture().load(gl);
      }
      mesh.getTexture().bind(gl);
    }

    // Compute transformed light position
    Matrix invertedTransformation = modelMatrix.getInverse();
    invertedTransformation = invertedTransformation.getTransposed();
    Vector light4 = new Vector(getRootNode().getLightPosition().x(),
        getRootNode().getLightPosition().y(),
        getRootNode().getLightPosition().z(), 1);
    Vector transformedLight = invertedTransformation.multiply(light4);
    Vector lightPosition =
        transformedLight.xyz().multiply(1.0f / transformedLight.w());

    gl.glLineWidth(3.5f);
    if (mode == RenderMode.REGULAR) {
      if (this.showSilhouette) {
        vboSilhouette.draw(gl);
      }
      drawRegular(gl);
    } else if (mode == RenderMode.DEBUG_SHADOW_VOLUME) {
      drawShadowVolume(gl, modelMatrix, lightPosition);
    } else if (mode == RenderMode.SHADOW_VOLUME) {
      drawShadowVolume(gl, modelMatrix, lightPosition);
    }
  }

  /**
   * Draw mesh regularly.
   */
  public void drawRegular(GL2 gl) {
    vbo.draw(gl);
    if (showNormals) {
      vboNormals.draw(gl);
    }
  }

  /**
   * Render the shadow volumes.
   */
  public void drawShadowVolume(GL2 gl, Matrix modelMatrix,
      Vector lightPosition) {
    ShadowTriangleMesh shadowPolygonMesh = new ShadowTriangleMesh();
    mesh.createShadowPolygons(lightPosition, 300, shadowPolygonMesh);
    if (shadowPolygonNode == null) {
      shadowPolygonMesh.computeVerticesNormals();
      shadowPolygonMesh.computeTriangleNormals();
      shadowPolygonNode = new JenkeTriangleMeshNode(shadowPolygonMesh, lightPosition, false);
      shadowPolygonNode.setParentNode(this);
      shadowPolygonNode.setColor(new Vector(0.25, 0.25, 0.75, 0.25));
      shadowPolygonNode.vbo.Setup(shadowPolygonNode.createRenderVertices(), GL2.GL_TRIANGLES);
    }
    shadowPolygonNode.traverse(gl, RenderMode.REGULAR, modelMatrix);
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

  public void setColor(Vector color) {
    this.color = color;
    vbo.Setup(createRenderVertices(), GL2.GL_TRIANGLES);
  }

  public void setShowNormals(boolean showNormals) {
    this.showNormals = showNormals;
  }
}
