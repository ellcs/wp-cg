package computergraphics.curves;

import java.util.List;

import computergraphics.framework.math.Vector;

/**
 * Created by alex on 11/21/16.
 */
public abstract class AbstractCurve {


  protected List<Vector> controlPoints;


  public abstract Vector p(float t);

  public abstract Vector pTangent(float t);

  public List<Vector> getControlPoints() {
    return this.controlPoints;
  }
}
