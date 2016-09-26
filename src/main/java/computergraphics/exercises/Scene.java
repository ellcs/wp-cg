/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import java.util.Random;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.INode.RenderMode;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.PlaneNode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.ScaleNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.model.CloudNode;
import computergraphics.framework.scenegraph.model.TreeNode;


public class Scene extends computergraphics.framework.Scene {

  RotationNode cloudSpin;

  public Scene() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(100, Shader.ShaderMode.PHONG, RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    float groundLength = 2;
    float skyHeight = 1;
    int amountOfClouds = 3;
    int amountOfTrees = 25;


    RotationNode planeRotation = new RotationNode(new Vector(0, 0, 1), Math.PI);
    PlaneNode planeNode = new PlaneNode(groundLength, Colors.darkGreen);

    planeRotation.addChild(planeNode);
    getRoot().addChild(planeRotation);

    // Create random forest
    Random random = new Random(System.currentTimeMillis());
    for (int t = 0; t < amountOfTrees; t++) {
      float x = (random.nextFloat() * 2 - 1) % (groundLength - 0.2f);
      float y = (random.nextFloat() * 2 - 1) % (groundLength - 0.2f);
      addTree(getRoot(), x, y);
    }

    // Create random clouds
    cloudSpin = new RotationNode(new Vector(0, 1, 0),0);
    getRoot().addChild(cloudSpin);
    for (int c = 0; c < amountOfClouds; c++) {
      CloudNode cloudNode = new CloudNode(0.3f, 0.05f, 0.3f);
      float xCloudPosition = (random.nextFloat() * 2 - 1) % (groundLength - 0.2f);
      float zCloudPosition = (random.nextFloat() * 2 - 1) % (groundLength - 0.2f);
      Vector translationVector = new Vector(xCloudPosition, skyHeight, zCloudPosition);
      TranslationNode translationNode = new TranslationNode(translationVector);

      cloudSpin.addChild(translationNode);
      translationNode.addChild(cloudNode);
    }

    // Light geometry
    TranslationNode lightTranslation =
            new TranslationNode(getRoot().getLightPosition());
    INode lightSphereNode = new SphereNode(0.01f, 10, Colors.yellow);
    lightTranslation.addChild(lightSphereNode);
    getRoot().addChild(lightTranslation);
  }

  public void addTree(InnerNode rootNode, float x, float z) {
    TreeNode treeNode = new TreeNode();
    TranslationNode translationNode = new TranslationNode(new Vector(x, 0.1, z));
    ScaleNode scaleNode = new ScaleNode(new Vector(0.1, 0.1, 0.1));

    rootNode.addChild(translationNode);
    translationNode.addChild(scaleNode);
    scaleNode.addChild(treeNode);
  }


  @Override
  public void keyPressed(int keyCode) {
    // key action
  }

  @Override
  public void timerTick(int counter) {
    cloudSpin.timerTick(counter);
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    new Scene();
  }
}
