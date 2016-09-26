package computergraphics.framework.scenegraph;

/**
 * Classes implementing this interface are able to 'animate'.
 */
public interface TimerTickable {

  /**
   * Tell object that time passed by and it has to move again.
   */
  void timerTick(int counter);
}
