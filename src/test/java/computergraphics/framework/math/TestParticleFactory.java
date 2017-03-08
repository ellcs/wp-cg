package computergraphics.framework.math;

import computergraphics.particle_system.particle.ParticleFactory;
import computergraphics.particle_system.services.RandomService;
import computergraphics.particle_system.services.VectorService;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alex on 10/21/16.
 */
public class TestParticleFactory {

  ParticleFactory particleFactory;
  RandomService randomService;
  VectorService vectorService;

  @Before
  public void setUp() {
    Random r = new Random();
    randomService = new RandomService(r);
    vectorService = new VectorService(r, randomService);
  }

  @Test
  public void testTheyDoNotShareProperties() {

  }
}
