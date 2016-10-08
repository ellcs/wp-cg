package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;

import static com.jogamp.opengl.GL.GL_LINES;

/**
 * Created by alex on 10/8/16.
 */
public class LineNode extends LeafNode {

  /**
   * VBO.
   */
  private VertexBufferObject vbo = new VertexBufferObject();

  private Vector start;

  private Vector end;

  private Vector color;

  public LineNode(Vector start, Vector end) {
    this(start, end, Colors.gray);
  }

  public LineNode(Vector start, Vector end, Vector color) {
    this.start = start;
    this.end = end;
    this.color = color;
//    createVbo();
  }

//  glLineWidth(2.5);
//  glColor3f(1.0, 0.0, 0.0);
//  glBegin(GL_LINES);
//  glVertex3f(0.0, 0.0, 0.0);
//  glVertex3f(15, 0, 0);
//  glEnd();

  private void createVbo() {
    List<RenderVertex> vertexList = new ArrayList<>();
    vertexList.add(new RenderVertex(start, new Vector(0,0,0), color));
    vertexList.add(new RenderVertex(end, new Vector(0,0,0), color));
    vbo.Setup(vertexList, GL_LINES);
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
//      vbo.draw(gl);
      gl.glLineWidth(2.5f);
      gl.glColor3f(0.1f, 0.1f, 0.1f);
      gl.glBegin(GL_LINES);
      gl.glVertex3f((float) start.get(0), (float) start.get(1), (float) start.get(2));
      gl.glVertex3f((float) end.get(0), (float) end.get(1), (float) end.get(2));
      gl.glEnd();
    }
  }
}
