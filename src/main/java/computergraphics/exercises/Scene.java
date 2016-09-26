/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import sun.reflect.generics.tree.Tree;

import java.util.Random;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.CubeNode;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.INode.RenderMode;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.PlaneNode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.ScaleNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.model.TreeNode;


public class Scene extends computergraphics.framework.Scene {

  RotationNode spin;
  float skyHeight = 1;
  public Scene() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(100, Shader.ShaderMode.PHONG, RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    float groundLength = 2;
    int amountOfTrees = 25;


    RotationNode planeRotation = new RotationNode(new Vector(0, 0, 1), Math.PI);
    PlaneNode planeNode = new PlaneNode(groundLength, Colors.darkGreen);

    planeRotation.addChild(planeNode);
    getRoot().addChild(planeRotation);

    Random random = new Random(System.currentTimeMillis());
    for (int t = 0; t < amountOfTrees; t++) {
      float x = (random.nextFloat() * 2 - 1) % (groundLength - 0.2f);
      float y = (random.nextFloat() * 2 - 1) % (groundLength - 0.2f);
      addTree(getRoot(), x, y);
    }

    // Light geometry
    TranslationNode lightTranslation =
            new TranslationNode(getRoot().getLightPosition());
    INode lightSphereNode = new SphereNode(0.01f, 10, Colors.yellow);
    lightTranslation.addChild(lightSphereNode);
    getRoot().addChild(lightTranslation);

    spin = new RotationNode(new Vector(0, 1, 0),0);
    Random rand = new Random(System.currentTimeMillis());
    for (int t = 0; t < 6; t++) {
      float x = (rand.nextFloat() * 2 - 1) % (0.3f);
      float y = (rand.nextFloat() * 2 - 1) % (0.1f);
      float z = (rand.nextFloat() * 2 - 1) % (0.4f);
      addSpheretoCloud(spin, x, y);
    }
    TranslationNode cloud2 = new TranslationNode(new Vector(0.5, 0, 0.7));
    for (int t = 0; t < 7; t++) {
      float x = (rand.nextFloat() * 2 - 1) % (0.25f);
      float y = (rand.nextFloat() * 2 - 1) % (0.2f);
      float z = (rand.nextFloat() * 2 - 1) % (0.4f);
      addSpheretoCloud(cloud2, x, y);

    }
    getRoot().addChild(spin);
    spin.addChild(cloud2);

    //clouds position
    TranslationNode cloudsTranslation =
            new TranslationNode(new Vector(0,skyHeight,0));
    INode cloudSphereNode = new SphereNode(0.2, 50, Colors.transparent_azure);
    cloudsTranslation.addChild(cloudSphereNode);
    getRoot().addChild(cloudsTranslation);
  }

  public void addTree(InnerNode rootNode, float x, float z) {
    TreeNode treeNode = new TreeNode();
    TranslationNode translationNode = new TranslationNode(new Vector(x, 0.1, z));
    ScaleNode scaleNode = new ScaleNode(new Vector(0.1, 0.1, 0.1));


    rootNode.addChild(translationNode);
    translationNode.addChild(scaleNode);
    scaleNode.addChild(treeNode);
  }

  public void addSpheretoCloud(InnerNode rootNode, float x, float z) {
    SphereNode sphereNode = new SphereNode(0.15, 50, Colors.transparent_azure);
    TranslationNode translationNode = new TranslationNode(new Vector(x, skyHeight, z));

    rootNode.addChild(translationNode);
    translationNode.addChild(sphereNode);

  }


  @Override
  public void keyPressed(int keyCode) {
    spin.inc();
  }

  @Override
  public void timerTick(int counter) {
    spin.inc();
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    new Scene();
  }
}
