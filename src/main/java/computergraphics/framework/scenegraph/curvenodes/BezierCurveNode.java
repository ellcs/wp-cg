package computergraphics.framework.scenegraph.curvenodes;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.curves.BezierCurve;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;
import computergraphics.framework.scenegraph.LeafNode;

/**
 * Created by alex on 11/15/16.
 */
public class BezierCurveNode extends LeafNode {

  /**
   * VBO.
   */
  private VertexBufferObject vbo;

  private BezierCurve bezierCurve;

  public BezierCurveNode(BezierCurve bezierCurve) {
    vbo = new VertexBufferObject();
    this.bezierCurve = bezierCurve;
    createVbo();
  }

  private void createVbo() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

    Vector first = bezierCurve.p(0.0f);
    for (float t = 0.01f; t <= 1.0f; t = t + 0.01f) {
      Vector second = bezierCurve.p(t);
      System.out.println(t + ": " + first.toString() + ", " + second.toString());
      renderVertices.add(new RenderVertex(first, new Vector(1,1,1), new Vector(1,1,1,1)));
      renderVertices.add(new RenderVertex(second, new Vector(1,1,1), new Vector(1,1,1,1)));
      first = second;
    }
    vbo.Setup(renderVertices, GL2.GL_LINE_STRIP);
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
      vbo.draw(gl);
    }
  }
}
