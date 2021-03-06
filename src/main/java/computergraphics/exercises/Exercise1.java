/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import computergraphics.framework.Scene;
import computergraphics.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.CubeNode;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.PlaneNode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.INode.RenderMode;
import computergraphics.framework.scenegraph.model.TreeNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class Exercise1 extends Scene {
  private static final long serialVersionUID = 8141036480333465137L;
  RotationNode rotationNode;

  public Exercise1() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(100, Shader.ShaderMode.PHONG, RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    // Sphere geometry
//    TranslationNode sphereTranslation =
//        new TranslationNode(new Vector(0, 0, 0));
//    ScaleNode scaleNode = new ScaleNode(new Vector(0.5, 0.5, 1));
//    SphereNode sphereNode = new SphereNode(0.5, 20);
//    sphereTranslation.addChild(sphereNode);
//
//    getRoot().addChild(sphereNode);
//
//    CylinderNode cylinderNode = new CylinderNode(0.5, 1, 30);
//
//    getRoot().addChild(scaleNode);
//    scaleNode.addChild(cylinderNode);

    TreeNode treeNode = new TreeNode();
    getRoot().addChild(treeNode);

    // Cube geometry
    TranslationNode cubeTranslation =
        new TranslationNode(new Vector(-1, 0.5, 0));
    CubeNode cubeNode = new CubeNode(0.5);
    rotationNode = new RotationNode(new Vector(0, 1, 0), Math.PI);

    RotationNode planeRotation = new RotationNode(new Vector(0, 0, 1), Math.PI);
    PlaneNode planeNode = new PlaneNode(1);

    getRoot().addChild(planeRotation);
    planeRotation.addChild(planeNode);

//    getRoot().addChild(rotationNode);
//    rotationNode.addChild(cubeTranslation);
//    cubeTranslation.addChild(cubeNode);

//    getRoot().addChild(rotationNode);
//    rotationNode.addChild(cubeNode);

//    getRoot().addChild(cubeTranslation);
//    cubeTranslation.addChild(cubeNode);

    getRoot().addChild(cubeTranslation);
    cubeTranslation.addChild(rotationNode);
    rotationNode.addChild(cubeNode);

    // Light geometry
    TranslationNode lightTranslation =
        new TranslationNode(getRoot().getLightPosition());
    INode lightSphereNode = new SphereNode(0.1f, 10);
    lightTranslation.addChild(lightSphereNode);
    getRoot().addChild(lightTranslation);

  }

  @Override
  public void keyPressed(int keyCode) {
    // handle key event
  }

  @Override
  public void timerTick(int counter) {
    // rotationNode.inc();
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    new Exercise1();
  }
}
