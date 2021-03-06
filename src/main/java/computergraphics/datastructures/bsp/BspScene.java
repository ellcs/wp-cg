/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.datastructures.bsp;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.framework.Scene;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.math.Vector;

/**
 * Scene and application for the BSP tree example.
 * 
 * @author Philipp Jenke
 */
public class BspScene extends Scene {

  private static final long serialVersionUID = 6506789797991105075L;

  /**
   * Scene graph BSP node
   */
  private BspNode node;

  public BspScene() {
    super(100, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);
  }

  @Override
  public void init(GL2 gl) {
    super.init(gl);
    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);
    gl.glLineWidth(5);
    gl.glPointSize(5);

    // Create data
    int numberOfPoints = 10;
    List<Vector> points = new ArrayList<Vector>();
    List<Integer> pointIndices = new ArrayList<Integer>();
    for (int i = 0; i < numberOfPoints; i++) {
      points.add(new Vector((float) (2 * Math.random() - 1), (float) (2 * Math.random() - 1), 0));
      pointIndices.add(i);
    }

    // Create tree
    BspTreeToolsDummy tools = new BspTreeToolsDummy();
    BspTreeNode rootNode = tools.createBspTree(null, points, pointIndices);

    System.out.println(rootNode.toString());
    // Add result to scne graph
    if (rootNode != null) {
      Vector observer = new Vector(1, 1, 0);
      List<Integer> back2FrontSorted = tools.getBackToFront(rootNode, points, observer);
      node = new BspNode(rootNode, points, back2FrontSorted, observer);
      getRoot().addChild(node);
    }
  }

  @Override
  public void keyPressed(int keyCode) {
    switch (keyCode) {
    case '1':
      node.showPoints = !node.showPoints;
      break;
    case '2':
      node.showElements = !node.showElements;
      break;
    case '3':
      node.showPlanes = !node.showPlanes;
      break;
    case '4':
      node.showBackToFront = !node.showBackToFront;
      break;
    }
  }

  @Override
  public void timerTick(int counter) {
  }

  public static void main(String[] args) {
    new BspScene();
  }
}
