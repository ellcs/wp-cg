package computergraphics.framework.math;

import computergraphics.particle_system.services.RandomService;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by alex on 10/21/16.
 */
public class TestRandomService {

  RandomService randomService;

  @Before
  public void setUp() {
    Random r = new Random(1333333337);
    randomService = new RandomService(r);
  }

  @Test
  public void testGetRandomIndex() {
    Integer randomIndex = null;
    try {
      for (int i = 1; i < 200; i++) {
        randomIndex = randomService.getRandomIndex(i);
        assertTrue(randomIndex >= 0);
        assertFalse(randomIndex >= i);
      }
    } catch (AssertionFailedError assertionFailedError) {
      System.err.println("Failed with index: " + randomIndex);
      throw assertionFailedError;
    }
  }

  @Test
  public void testRandomBetween_SameNumber_ReturnSameNumber_WithLong() {
    for (long l = -3000; l < 3000; l++) {
      assertEquals(l, randomService.randomBetween(l, l));
    }
  }

  @Test
  public void testRandomBetween_SameNumber_ReturnSameNumber_WithDouble() {
    double delta = 0.00001;
    for (double d = -15; d < 15; d++) {
      assertEquals(d, randomService.randomBetween(d, d), delta);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRandomBetween_MaxSmallerThenMin_WithLong() {
    randomService.randomBetween(2l, 1l);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRandomBetween_MaxSmallerThenMin_WithDouble() {
    randomService.randomBetween(2, 1);
  }

}
