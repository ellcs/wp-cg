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

  private float t = 0.1f;

  private float itrMax;

  public DebugCurveNode(AbstractCurve curve, float itrMax) {
    this.curve = curve;
    this.itrMax = itrMax;
    addCurve();
//    addTangent();
    addControlPointSpheres();
  }

  public DebugCurveNode(AbstractCurve curve) {
    this(curve, 1.0f);
  }

  private void addCurve() {
    CurveNode curve = new CurveNode(this.curve, this.itrMax);
    this.addChild(curve);
  }

  private void addTangent() {
    Vector tangentDirection = curve.pTangent(t);
    tangentDirection.normalize();
//    tangentDirection.multiplySelf(0.1);
    Vector start = curve.p(t);
    addChild(new LineNode(start, tangentDirection));
  }

  private void addControlPointSpheres() {
    for (Vector vector : this.curve.getControlPoints()) {
      SphereNode sphereNode = new SphereNode(0.01, 5, Colors.transparentAzure);
      TranslationNode translationNode = new TranslationNode(vector);
      translationNode.addChild(sphereNode);
      addChild(translationNode);
    }
  }

}
