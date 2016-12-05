package computergraphics.projects.trianglemesh;

import computergraphics.datastructures.mesh.ITriangleMesh;
import computergraphics.datastructures.mesh.Triangle;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.VertexBufferObject;
import computergraphics.scenegraph.LeafNode;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class TriangleMeshNode extends LeafNode {
  /**
   * Contained triangle mesh.
   */
  private ITriangleMesh mesh;

  /**
   * This node is used to render the shadow polygon mesh
   */
  private TriangleMeshNode shadowPolygonNode = null;

  /**
   * This is the shadow polygon mesh.
   */
  private TriangleMesh shadowPolygonMesh = new TriangleMesh();

  /**
   * Color used for rendering (RGBA)
   */
  private Vector color = new Vector(0.75, 0.25, 0.25, 1);

  /**
   * Debugging: Show normals.
   */
  private boolean showNormals = false;

  /**
   * VBOs
   */
  private VertexBufferObject vbo = new VertexBufferObject();
  private VertexBufferObject vboNormals = new VertexBufferObject();

  public TriangleMeshNode(ITriangleMesh mesh) {
    this.mesh = mesh;
    vbo.Setup(createRenderVertices(), GL2.GL_TRIANGLES);
    vboNormals.Setup(createRenderVerticesNormals(), GL2.GL_LINES);
  }

  /**
   * Create vbo data for mesh rendering
   */
  private List<RenderVertex> createRenderVertices() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();
    for (int i = 0; i < mesh.getNumberOfTriangles(); i++) {
      Triangle t = mesh.getTriangle(i);
      for (int j = 0; j < 3; j++) {
        RenderVertex renderVertex = null;
        if ( t.getTexCoordIndex(j) >= 0 ){
          renderVertex = new RenderVertex(mesh.getVertex(t.getVertexIndex(j)).getPosition(),
              t.getNormal(), color, mesh.getTextureCoordinate(t.getTexCoordIndex(j))  );
        } else {
          renderVertex = new RenderVertex(mesh.getVertex(t.getVertexIndex(j)).getPosition(),
              t.getNormal(), color);
        }
        renderVertices.add(renderVertex);
      }
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
      Triangle t = mesh.getTriangle(i);
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

    if (mode == RenderMode.REGULAR) {
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
    mesh.createShadowPolygons(lightPosition, 500, shadowPolygonMesh);
    if (shadowPolygonNode == null) {
      shadowPolygonNode = new TriangleMeshNode(shadowPolygonMesh);
      shadowPolygonNode.setParentNode(this);
      shadowPolygonNode.setColor(new Vector(0.25, 0.25, 0.75, 0.25));
      shadowPolygonNode.vbo.Setup(shadowPolygonNode.createRenderVertices(), GL2.GL_TRIANGLES);
    }
    shadowPolygonNode.traverse(gl, RenderMode.REGULAR, modelMatrix);
  }

  public void setColor(Vector color) {
    this.color = color;
    vbo.Setup(createRenderVertices(), GL2.GL_TRIANGLES);
  }

  public void setShowNormals(boolean showNormals) {
    this.showNormals = showNormals;
  }
}
