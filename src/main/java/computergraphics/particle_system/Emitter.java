package computergraphics.particle_system;

import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.particle_system.factories.Particle;
import computergraphics.particle_system.factories.ParticleFactory;
import computergraphics.particle_system.helpers.RandomHelper;
import computergraphics.particle_system.helpers.VectorHelper;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by ellcs on 03.03.17.
 */
public class Emitter implements IEmitter {

    private EmitterPreferences emitterPreferences;
    private ParticlePreferences particlePreferences;

    private ParticleFactory particleFactory;

    private RandomHelper randomHelper;
    private VectorHelper vectorHelper;

    private Random r;

    private List<Particle> particles;

    public Emitter(EmitterPreferences preferences, ParticlePreferences particlePreferences) {
        this.emitterPreferences = preferences;
        this.particlePreferences = particlePreferences;
        this.r = new Random(System.currentTimeMillis() * 321221);
        this.vectorHelper = new VectorHelper(r);
        this.randomHelper = new RandomHelper(r);
//        this.particles = new Particle[preferences.maximumParticles];
        this.particleFactory = new ParticleFactory(particlePreferences,
                emitterPreferences,
                randomHelper,
                vectorHelper);
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
    public void update(long deltaTime) {
        updateAllParticles(deltaTime);
        removeDeadParticles();
        createNewParticles(deltaTime);
        sortParticles();
    }

    @Override
    public boolean drawEmitterBox() {
        return this.emitterPreferences.drawEmitterBox;
    }

    @Override
    public Vector getEmitterBox() {
        return this.emitterPreferences.emitterSize;
    }

    private void updateAllParticles(long deltaTime) {
        for (Particle particle : this.particles) {
            particle.update(deltaTime);
        }
    }

    private void removeDeadParticles() {
        this.particles.removeAll(getDeadParticles());
    }

    private List<Particle> getDeadParticles() {
        return this.particles.stream().filter(Particle::lifetimeExceeded).collect(Collectors.toList());
    }

    private void createNewParticles(long deltaTime) {
        int amountToCreate = amountToCreate(deltaTime);
        for (int i = 0; i < amountToCreate; i++) {
            createNewParticle();
        }
    }

    /**
     * Get depending on the passed time, the amount of particles to be created.
     */
    private int amountToCreate(long deltaTime) {
        float minPerDeltaTime = this.emitterPreferences.spawnRate.minPerMilliSec * deltaTime;
        float maxPerDeltaTime = this.emitterPreferences.spawnRate.maxPerMilliSec * deltaTime;
        return (int) ((r.nextFloat() + minPerDeltaTime) % maxPerDeltaTime);
    }

    private void createNewParticle() {
        if (!maximumAmountOfParticlesReached()) {
            this.particles.add(particleFactory.createParticle());
        }
    }

    private boolean maximumAmountOfParticlesReached() {
        return this.emitterPreferences.maximumParticles <= this.particles.size();
    }

    /**
     * Sorts the particles depending on the distance to the camera.
     */
    private void sortParticles() {

    }

}
