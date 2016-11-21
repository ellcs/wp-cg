package computergraphics.curves;

import java.util.List;

import computergraphics.framework.math.Vector;

/**
 * Created by alex on 11/15/16.
 */
public class BezierCurve extends AbstractCurve {

  final static float h = 0.000001f;

  public BezierCurve(List<Vector> controlPoints) {
    this.controlPoints = controlPoints;
  }

  public Vector p(float t) {
    if (t < 0 || 1 < t) {
      throw new IllegalArgumentException("t has to be between 0 and 1.");
    }
    Vector v = new Vector(0,0,0);
    int n = controlPoints.size()-1;
    int i = 0;
    for (Vector controlPoint : this.controlPoints) {
      float b = b(n, i, t);
      Vector ctrl = controlPoint.multiply(b);
      System.out.println("b: " + b + ", " + ctrl);
      v.addSelf(ctrl);
      i++;
    }
    System.out.println("v: " + v);
    return v;
  }

  public Vector pTangent(float t) {
    return (p(t + h).subtract(p(t))).multiply(1/h);
  }

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
