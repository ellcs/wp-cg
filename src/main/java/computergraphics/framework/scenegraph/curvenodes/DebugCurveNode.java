package computergraphics.framework.scenegraph.curvenodes;

import computergraphics.curves.AbstractCurve;
import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.LineNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;

/**
 * Created by alex on 11/21/16.
 */
public class DebugCurveNode extends InnerNode {

  private AbstractCurve curve;

  /**
   * Position of tangent.
   */
  private float t = 0.1f;

  /**
   * itrMax is also known as n.
   */
  private float itrMax;

  public DebugCurveNode(AbstractCurve curve, float itrMax) {
    this.curve = curve;
    this.itrMax = itrMax;
    addCurve();
    addTangent();
    addControlPointSpheres();
  }

  /**
   * Default itrMax is 1.0f
   */
  public DebugCurveNode(AbstractCurve curve) {
    this(curve, 1.0f);
  }

  /**
   * Add curve itself.
   */
  private void addCurve() {
    CurveNode curve = new CurveNode(this.curve, this.itrMax);
    this.addChild(curve);
  }

  /**
   * Add a line as tangent representation.
   */
  private void addTangent() {
    Vector tangentDirection = curve.pTangent(t);
    Vector start = curve.p(t);
    addChild(new LineNode(start, tangentDirection));
  }

  /**
   * Add control-points as spheres.
   */
  private void addControlPointSpheres() {
    for (Vector vector : this.curve.getControlPoints()) {
      SphereNode sphereNode = new SphereNode(0.01, 5, Colors.transparentAzure);
      TranslationNode translationNode = new TranslationNode(vector);
      translationNode.addChild(sphereNode);
      addChild(translationNode);
    }
  }

}
