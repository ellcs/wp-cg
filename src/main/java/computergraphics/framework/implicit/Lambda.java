package computergraphics.framework.implicit;

import computergraphics.math.Vector;

/**
 * Created by alex on 11/4/16.
 */
public interface Lambda {

  /**
   * @param vector of position to look up value.
   * @return function value at given position.
   */
  double f(Vector vector);
}
