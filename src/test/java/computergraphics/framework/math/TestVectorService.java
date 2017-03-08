package computergraphics.framework.math;

import computergraphics.particle_system.services.RandomService;
import computergraphics.particle_system.services.VectorService;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by alex on 10/21/16.
 */
public class TestVectorService {

  VectorService vectorService;

  @Before
  public void setUp() {
    Random r = new Random(1333333337);
    RandomService rs = new RandomService(r);
    this.vectorService = new VectorService(r, rs);
  }

  @Test
  public void testGetRandomVector() {
    Vector randomVector = vectorService.getRandomVector();
    assertNotNull(randomVector);
  }

  @Test
  public void testGetRandomVectorInRange() {
    Vector range = new Vector(1,1,1);

    for (int i = 0; i < 200; i++) {
      Vector randomVector = vectorService.getRandomVectorInRange(range);
      assertTrue(randomVector.x() >= 0.0f);
      assertTrue(randomVector.y() >= 0.0f);
      assertTrue(randomVector.z() >= 0.0f);

      assertTrue(randomVector.x() < range.x());
      assertTrue(randomVector.y() < range.y());
      assertTrue(randomVector.z() < range.z());
    }
  }

  @Test
  public void testGetRandomVectorBetween() {
    Vector currentMin = null;
    Vector currentMax = null;
    Vector randomVector = null;
    try {

      List<Vector[]> vectors = new ArrayList<>();
      vectors.add(new Vector[]{new Vector(0, 0, 0), new Vector(1, 1, 1)});
      vectors.add(new Vector[]{new Vector(1, 1, 1), new Vector(2, 2, 2)});

      for (Vector[] minMax : vectors) {
        currentMin = minMax[0];
        currentMax = minMax[1];
        for (int i = 0; i < 200; i++) {
          randomVector = vectorService.getRandomVectorBetween(currentMin, currentMax);
          assertTrue(randomVector.x() >= currentMin.x());
          assertTrue(randomVector.y() >= currentMin.y());
          assertTrue(randomVector.z() >= currentMin.z());

          assertTrue(randomVector.x() <= currentMax.x());
          assertTrue(randomVector.y() <= currentMax.y());
          assertTrue(randomVector.z() <= currentMax.z());

          randomVector = null;
        }
        currentMin = null;
        currentMax = null;
      }
    } catch (AssertionFailedError assertionError) {
      System.err.println("Failed with: getRandomVectorBetween(" + currentMin
              + ", " + currentMax + ") = " + randomVector);
      throw assertionError;
    }
  }

}
