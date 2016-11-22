/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 *
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import computergraphics.curves.BezierCurve;
import computergraphics.curves.HermiteSpline;
import computergraphics.framework.Scene;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.INode.RenderMode;
import computergraphics.framework.scenegraph.curvenodes.DebugCurveNode;

public class CurvesBezier extends Scene {
  private static final long serialVersionUID = 8141036480333465137L;
  RotationNode rotationNode;

  public CurvesBezier() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(100, Shader.ShaderMode.PHONG, RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    /**
     * { -4.0, -4.0, 0.0}, { -2.0, 4.0, 0.0},
     {2.0, -4.0, 0.0}, {4.0, 4.0, 0.0}
     */
    List<Vector> controlPoints = new ArrayList<>();
    controlPoints.add(new Vector(-1.0,-1.0,0));
    controlPoints.add(new Vector(0.5,-1.0,0));
    controlPoints.add(new Vector(-0.5,1.0,0));
    controlPoints.add(new Vector(1.0,1.0,0));

    BezierCurve bezierCurve = new BezierCurve(controlPoints);
    DebugCurveNode curve = new DebugCurveNode(bezierCurve);
    getRoot().addChild(curve);

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
    new CurvesBezier();
  }
}
