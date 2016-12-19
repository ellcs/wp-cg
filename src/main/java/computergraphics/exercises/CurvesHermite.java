/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 *
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import java.util.ArrayList;
import java.util.List;

import computergraphics.curves.HermiteCurve;
import computergraphics.framework.Scene;
import computergraphics.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.INode.RenderMode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.curvenodes.DebugCurveNode;

public class CurvesHermite extends Scene {
  private static final long serialVersionUID = 8141036480333465137L;
  RotationNode rotationNode;

  public CurvesHermite() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(100, Shader.ShaderMode.PHONG, RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    // Example hermite
    List<Vector> controlPoints = new ArrayList<>();
    controlPoints.add(new Vector(0,0,0));
    controlPoints.add(new Vector(1,0,0));
    controlPoints.add(new Vector(-1,0,0));
    controlPoints.add(new Vector(1,1,0));
    HermiteCurve hermiteCurve = new HermiteCurve(controlPoints, HermiteCurve.getBasisFunctions());
    DebugCurveNode curveNode = new DebugCurveNode(hermiteCurve);
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
    new CurvesHermite();
  }
}
