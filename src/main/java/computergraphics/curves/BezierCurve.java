package computergraphics.curves;

import java.util.List;

import computergraphics.framework.math.Vector;

/**
 * Created by alex on 11/15/16.
 */
public class BezierCurve extends AbstractCurve {

//  final static float h = Float.MIN_VALUE;
  final static float h = 0.000001f;

  public BezierCurve(List<Vector> controlPoints) {
    this.controlPoints = controlPoints;
  }

  public Vector p(float t) {
    Vector v = new Vector(0,0,0);
    int n = controlPoints.size()-1;
    int i = 0;
    for (Vector controlPoint : this.controlPoints) {
      float b = b(n, i, t);
      Vector ctrl = controlPoint.multiply(b);
      v.addSelf(ctrl);
      i++;
    }
    return v;
  }

  /**
   * Calculate tangent in point t.
   */
  public Vector pTangent(float t) {
    return (p(t + h).subtract(p(t))).multiply(1/h);
  }

  /**
   * Static base function of bezier curves.
   */
  private float b(int n, int i, float t) {
    float facN = fac(n);
    float facI = fac(i);
    float facNMinusI = fac(n - i);
    float accu = facN / (facI * facNMinusI);
    accu *= Math.pow(t, i);
    accu *= Math.pow(1.0f-t, n-i);
    return accu;
  }

  /**
   * Calucaltes faculty of given number n.
   */
  private int fac(int n) {
    return fac_(1, n);
  }

  /**
   * Tail recursion helper.
   */
  private int fac_(int accu, int n) {
    if (n == 0) {
      return accu;
    }
    return fac_(accu * n, n - 1);
  }
}
