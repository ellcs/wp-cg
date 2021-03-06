package computergraphics.math;

/**
 * Created by alex on 9/24/16.
 */
public final class Colors {

  private Colors() {
    // permit instance of Colors
  }

  public static final Vector gray = new Vector(0.7, 0.7, 0.7, 1);
  public static final Vector darkGreen = new Vector(0.05, 0.45, 0.05, 1);
  public static final Vector green = new Vector(0.25, 0.75, 0.25, 1);
  public static final Vector brown = new Vector(0.128, 0, 0, 1);
  public static final Vector yellow = new Vector(0.255, 0.255, 0, 1);
  public static final Vector transparentAzure = new Vector(240,255,255, 0.9);
  public static final Vector orange = new Vector(1.000, 0.647, 0.000, 1);
  public static final Vector black = new Vector(0, 0, 0, 1);
}
