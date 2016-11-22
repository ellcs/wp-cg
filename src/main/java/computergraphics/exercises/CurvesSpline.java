/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 *
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import java.util.Arrays;

import computergraphics.curves.HermiteSpline;
import computergraphics.framework.Scene;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.INode.RenderMode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.curvenodes.DebugCurveNode;

public class CurvesSpline extends Scene {
  private static final long serialVersionUID = 8141036480333465137L;
  RotationNode rotationNode;

  public CurvesSpline() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(100, Shader.ShaderMode.PHONG, RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    // Spline example
    Vector v1 = new Vector(0,0,0);
    Vector v2 = new Vector(.5f,.5f,0);
    Vector v4 = new Vector(1f,0f,0);
    Vector v5 = new Vector(1.5f,.5f,0);
    Vector v6 = new Vector(2f,.0f,0);
    Vector v7 = new Vector(2.5f,.5f,0);
    HermiteSpline curve = new HermiteSpline(Arrays.asList(v1, v2, v4, v5, v6, v7));
    DebugCurveNode curveNode = new DebugCurveNode(curve, 5.0f);
    getRoot().addChild(curveNode);

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
    new CurvesSpline();
  }
}
