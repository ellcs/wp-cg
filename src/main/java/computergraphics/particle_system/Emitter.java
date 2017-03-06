package computergraphics.particle_system;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by ellcs on 03.03.17.
 */
public class Emitter implements IEmitter {

    private EmitterPreferences emitterPreferences;
    private ParticlePreferences particlePreferences;

    private Random r;

    private List<Particle> particles;

    public Emitter(EmitterPreferences preferences, ParticlePreferences particlePreferences) {
        this.emitterPreferences = preferences;
        this.particlePreferences = particlePreferences;
        this.r = new Random(System.currentTimeMillis() * 321221);
//        this.particles = new Particle[preferences.maximumParticles];
        this.particles = new ArrayList<>();
    }

    @Override
    public List<RenderVertex> getRenderVerticies() {
        return this.particles.stream().map(Particle::getRenderVertex).
                collect(Collectors.toList());
    }

    /**
     * Updates all particles, removes dead ones and creates new ones.
     * @param deltaTime is the last t
     */
    public void update(float deltaTime) {
        updateAllParticles(deltaTime);
        removeDeadParticles();
        createNewParticles(deltaTime);
        sortParticles();
    }

    private void updateAllParticles(float deltaTime) {
        for (Particle particle : this.particles) {
            particle.update(deltaTime);
        }
        // for all particles p:
        // p.update(deltaTime);
    }

    private void removeDeadParticles() {

    }

    private void createNewParticles(float deltaTime) {
        float minPerDeltaTime = this.emitterPreferences.spawnRate.minPerMilliSec * deltaTime;
        float maxPerDeltaTime = this.emitterPreferences.spawnRate.maxPerMilliSec * deltaTime;
        int amountToCreate = (int) ((r.nextFloat() + minPerDeltaTime) % maxPerDeltaTime);
        System.out.println("dt:" + deltaTime);
        System.out.println("min pdt: " + minPerDeltaTime);
        System.out.println("max pdt: " + maxPerDeltaTime);
        System.out.println("amount to create: " + amountToCreate);
        for (int i = 0; i < amountToCreate; i++) {
            createNewParticle();
        }
    }

    private void createNewParticle() {
        Vector position = getRandomVectorInRange(this.emitterPreferences.emitterSize);
        Particle p = new Particle(position);
        this.particles.add(p);
    }

    private Vector getRandomVectorInRange(Vector range) {
        Vector v = getRandomVector();
        v.set(0, v.x() % range.x());
        v.set(1, v.z() % range.z());
        v.set(2, v.y() % range.y());
        return v;
    }

    private Vector getRandomVector() {
        float x = r.nextFloat();
        float z = r.nextFloat();
        float y = r.nextFloat();
        Vector v = new Vector(x, y, z);
        return v;
    }

    /**
     * Sorts the particles depending on the distance to the camera.
     */
    private void sortParticles() {

    }

}
