package computergraphics.curves;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ITriangleMesh;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.curvenodes.CurveNode;

/**
 * Created by alex on 11/21/16.
 */
public class HermiteSpline extends InnerNode {

  Vector nullVector = new Vector(0,0,0);

  List<Vector> controlPoints;

  List<HermiteCurve> curves;

  public HermiteSpline(List<Vector> controlPoints) {
    if (controlPoints.size() < 4) {
      throw new IllegalArgumentException("There've to be at least 3 control-points.");
    }
    this.controlPoints = controlPoints;

    Iterator<Vector> itr = this.controlPoints.iterator();
    Vector pre = itr.next();
    Vector current = itr.next();
    Vector post = itr.next();

    Vector oldTangent = formula(pre, post);

    add(new HermiteCurve(Arrays.asList(pre, nullVector, oldTangent, current)));
    int i = 0;
    while (itr.hasNext()) {
      pre = current;
      current = post;
      post = itr.next();

      Vector newTangent = formula(pre, post);
      add(new HermiteCurve(Arrays.asList(pre, oldTangent, newTangent, post)));
      oldTangent = newTangent;
      if (controlPoints.size() == i) {

      }
      i++;
    }

  }

  private void add(HermiteCurve hermiteCurve) {
    addChild(new CurveNode(hermiteCurve));
  }

  public Vector formula(Vector pMinusOne, Vector pPlusOne) {
    double distance = pPlusOne.subtract(pMinusOne).getNorm();
    return pPlusOne.subtract(pMinusOne).multiply(distance);
  }

}
