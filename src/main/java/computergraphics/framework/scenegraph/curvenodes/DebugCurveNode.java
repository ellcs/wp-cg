package computergraphics.framework.scenegraph.curvenodes;

import computergraphics.curves.AbstractCurve;
import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;

/**
 * Created by alex on 11/21/16.
 */
public class DebugCurveNode extends InnerNode {

  private AbstractCurve curve;

  public DebugCurveNode(AbstractCurve curve) {
    this.curve = curve;
    addCurve();
    addControlPointSpheres();
  }

  private void addCurve() {
    CurveNode curve = new CurveNode(this.curve);
    this.addChild(curve);
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
