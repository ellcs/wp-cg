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
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.INode.RenderMode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.model.Mew;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class MixedColorsScene extends Scene {
  private static final long serialVersionUID = 8141036480333465137L;
  RotationNode rotationNode;

  public MixedColorsScene() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(100, Shader.ShaderMode.PHONG, RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    Mew mew = new Mew(2);
    getRoot().addChild(mew);

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
    new MixedColorsScene();
  }
}
