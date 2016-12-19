package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.Colors;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
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
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
      gl.glLineWidth(2.5f);
      gl.glColor3f((float) this.color.get(0), (float) this.color.get(1), (float) this.color.get(2));
      gl.glBegin(GL_LINES);
      gl.glVertex3f((float) start.get(0), (float) start.get(1), (float) start.get(2));
      gl.glVertex3f((float) end.get(0), (float) end.get(1), (float) end.get(2));
      gl.glEnd();
    }
  }
}
