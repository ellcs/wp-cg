package computergraphics.framework.scenegraph.curvenodes;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.curves.AbstractCurve;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.LeafNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;

/**
 * Created by alex on 11/15/16.
 */
public class CurveNode extends LeafNode {

  /**
   * VBO.
   */
  private VertexBufferObject curveVbo;

  private AbstractCurve curve;

  public CurveNode(AbstractCurve curve) {
    this.curveVbo = new VertexBufferObject();
    this.curve = curve;
    createCurveVbo();
  }

  private void createCurveVbo() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

    Vector first = curve.p(0.0f);
    for (float t = 0.01f; t <= 1.0f; t = t + 0.01f) {
      Vector second = curve.p(t);
      System.out.println(t + ": " + first.toString() + ", " + second.toString());
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
