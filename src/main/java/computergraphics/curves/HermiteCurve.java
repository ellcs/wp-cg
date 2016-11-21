package computergraphics.curves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import computergraphics.framework.math.Vector;

/**
 * Created by alex on 11/21/16.
 */
public class HermiteCurve extends AbstractCurve {

  final static BasisFunction h0 = t -> (float) Math.pow(1.0f - t, 2) * (1 + 2*t);
  final static BasisFunction h1 = t -> t * (float) Math.pow(1.0f-t, 2);
  final static BasisFunction h2 = t -> - (t*t) * (1.0f - t);
  final static BasisFunction h3 = t -> (3.0f - (2*t)) * (t*t);

  List<BasisFunction> basisFunctions;

  public static List<BasisFunction> getBasisFunctions() {
    return Arrays.asList(h0, h1, h2, h3);
  }

  public HermiteCurve(List<Vector> controlPoints) {
    this(controlPoints, getBasisFunctions());
  }

  public HermiteCurve(List<Vector> controlPoints, List<BasisFunction> basisFunctions) {
    this.controlPoints =  controlPoints;
    this.basisFunctions = basisFunctions;
  }

  @Override
  public Vector p(float t) {
    Vector c0 = this.controlPoints.get(0);
    Vector c1 = this.controlPoints.get(1);
    Vector c2 = this.controlPoints.get(2);
    Vector c3 = this.controlPoints.get(3);
    BasisFunction h0 = this.basisFunctions.get(0);
    BasisFunction h1 = this.basisFunctions.get(1);
    BasisFunction h2 = this.basisFunctions.get(2);
    BasisFunction h3 = this.basisFunctions.get(3);

    Vector v0 = c0.multiply(h0.b(t));
    Vector v1 = c1.multiply(h1.b(t));
    Vector v2 = c2.multiply(h2.b(t));
    Vector v3 = c3.multiply(h3.b(t));

    return v0.add(v1).add(v2).add(v3);
  }

  @Override
  public Vector pTangent(float t) {
    return null;
  }

}
