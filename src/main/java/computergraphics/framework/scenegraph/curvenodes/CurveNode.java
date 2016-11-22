package computergraphics.framework.scenegraph.curvenodes;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.curves.AbstractCurve;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;
import computergraphics.framework.scenegraph.LeafNode;

/**
 * Created by alex on 11/15/16.
 */
public class CurveNode extends LeafNode {

  /**
   * VBO.
   */
  private VertexBufferObject curveVbo;

  private AbstractCurve curve;

  /**
   * Amount of segments.
   */
  private float itrMax;

  public CurveNode(AbstractCurve curve, float itrMax) {
    this.curveVbo = new VertexBufferObject();
    this.curve = curve;
    this.itrMax = itrMax;
    createCurveVbo();
  }

  public CurveNode(AbstractCurve curve) {
    this(curve, 1.0f);
  }

  private void createCurveVbo() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

    Vector first = curve.p(0.0f);
    for (float t = 0.01f; t <= itrMax; t = t + 0.01f) {
      Vector second = curve.p(t);
      renderVertices.add(new RenderVertex(first, new Vector(1,1,1), new Vector(1,1,1,1)));
      renderVertices.add(new RenderVertex(second, new Vector(1,1,1), new Vector(1,1,1,1)));
      first = second;
    }
    curveVbo.Setup(renderVertices, GL2.GL_LINE_STRIP);
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
      curveVbo.draw(gl);
    }
  }
}
