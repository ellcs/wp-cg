package computergraphics.framework.scenegraph.model;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Colors;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;
import computergraphics.framework.scenegraph.LeafNode;

/**
 * Created by alex on 10/8/16.
 */
public class Mew extends LeafNode {

    /**
     * Cube side length
     */
    private double sideLength;

    /**
     * VBO.
     */
    private VertexBufferObject vbo = new VertexBufferObject();

    /**
     * Constructor.
     */
    public Mew(double sideLength) {
      this.sideLength = sideLength;
      createVbo();
    }

    private void createVbo() {
      List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

      double halfSideLength = sideLength / 2;
      Vector p0 = new Vector(-halfSideLength, 0.0, halfSideLength);
      Vector p1 = new Vector(halfSideLength, 0.0, halfSideLength);
      Vector p2 = new Vector(halfSideLength, 0.0, -halfSideLength);
      Vector p3 = new Vector(-halfSideLength, 0.0, -halfSideLength);
      Vector n0 = new Vector(0, -1, 0);

      renderVertices.add(new RenderVertex(p3, n0, Colors.yellow));
      renderVertices.add(new RenderVertex(p2, n0, Colors.green));
      renderVertices.add(new RenderVertex(p1, n0, Colors.green));
      renderVertices.add(new RenderVertex(p0, n0, Colors.yellow));

      vbo.Setup(renderVertices, GL2.GL_QUADS);
    }

     @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
      if (mode == RenderMode.REGULAR) {
        vbo.draw(gl);
      }
    }
}
