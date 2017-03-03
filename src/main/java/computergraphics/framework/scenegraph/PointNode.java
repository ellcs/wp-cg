package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileCacheImageInputStream;
import javax.imageio.stream.ImageInputStream;

import computergraphics.framework.AssetPath;
import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;

/**
 * Created by alex on 1/22/17.
 */
public class PointNode extends LeafNode implements TimerTickable {


  private Texture texture;

  List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

  /**
   * VBO.
   */
  private VertexBufferObject vbo = new VertexBufferObject();

  public PointNode() {
    createVbo();
  }



  private void createVbo() {
    renderVertices.add(new RenderVertex(new Vector(0,0,0),  new Vector(0,0,0), Colors.transparentAzure));
    renderVertices.add(new RenderVertex(new Vector(0,0,0),  new Vector(0,0,0), Colors.transparentAzure));
    renderVertices.add(new RenderVertex(new Vector(0.1,1,0),  new Vector(0,0,0), Colors.transparentAzure));
    renderVertices.add(new RenderVertex(new Vector(0.1,0,0),  new Vector(0,0,0), Colors.transparentAzure));
    vbo.Setup(renderVertices, GL2.GL_POINTS);
  }

  float i = 50;
  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
//      gl.glEnable(GL2.GL_TEXTURE_2D);
//      try {
//        File imgFile = new File(AssetPath.getPathToAsset("img.png"));
//        texture = TextureIO.newTexture(imgFile, true);
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//      texture.enable(gl);
//      texture.bind(gl);
//      gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject(gl));
//      gl.glEnable(GL2.GL_POINT_SPRITE);
//      gl.glTexEnvi(GL2.GL_POINT_SPRITE, GL2.GL_COORD_REPLACE, GL2.GL_TRUE);
//      i = i - 0.1f;
//      gl.glDepthMask(true);
      gl.glPointSize(i);
      gl.glEnable(GL2.GL_BLEND);
      gl.glEnable(GL2.GL_POINT_SMOOTH);
      gl.glBegin(GL2.GL_POINTS);
      gl.glVertex3d(0,0,0);
      gl.glEnd();
//      vbo.draw(gl);
    }
  }

  @Override
  public void timerTick(int counter) {
    RenderVertex renderVertex = renderVertices.get(0);
    renderVertex.position.addSelf(new Vector(0.01, 0, 0));
  }
}
