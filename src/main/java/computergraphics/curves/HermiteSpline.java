package computergraphics.curves;

import computergraphics.framework.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alex on 11/21/16.
 */
public class HermiteSpline extends AbstractCurve {

  Vector nullVector = new Vector(0,0,0);

  List<HermiteCurve> curves;

  public HermiteSpline(List<Vector> controlPoints) {
    this.controlPoints = controlPoints;
    this.curves = new ArrayList<>();

    Iterator<Vector> itr = this.controlPoints.iterator();
    Vector pre = itr.next();
    Vector current = itr.next();
    Vector post = itr.next();

    Vector oldTangent = new Vector(nullVector);
    Vector newTangent = formula(pre, post);

    add(new HermiteCurve(Arrays.asList(pre, oldTangent, newTangent, current)));
    while (itr.hasNext()) {
      oldTangent = newTangent;
      pre = current;
      current = post;
      post = itr.next();
      newTangent = formula(pre, post);
      add(new HermiteCurve(Arrays.asList(pre, oldTangent, newTangent, current)));
    }
    oldTangent = newTangent;
    pre = current;
    current = post;
    add(new HermiteCurve(Arrays.asList(pre, oldTangent, nullVector, current)));
    System.err.println("DEBUG: " + this.curves.size());
  }


  private void add(HermiteCurve hermiteCurve) {
    this.curves.add(hermiteCurve);
  }

  public Vector formula(Vector pMinusOne, Vector pPlusOne) {
    double distance = pPlusOne.subtract(pMinusOne).getNorm();
    return pPlusOne.subtract(pMinusOne).multiply(distance);
  }

  @Override
  public Vector p(float t) {
    int index = (int) t;
    HermiteCurve curve = this.curves.get(index);
    float hermiteT = t - index;
    System.err.println("DEBUG: " + index + ", " + hermiteT);
    return curve.p(hermiteT);
  }

  @Override
  public Vector pTangent(float t) {
    return null;
  }
}
