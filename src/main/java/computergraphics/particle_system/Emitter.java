package computergraphics.particle_system;

import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.particle_system.particle.Particle;
import computergraphics.particle_system.particle.ParticleFactory;
import computergraphics.particle_system.services.RandomService;
import computergraphics.particle_system.services.VectorService;
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

    private RandomService randomHelper;
    private VectorService vectorHelper;

    private Random r;

    private List<Particle> particles;

    public Emitter(EmitterPreferences preferences, ParticlePreferences particlePreferences) {
        this.emitterPreferences = preferences;
        this.particlePreferences = particlePreferences;
        this.r = new Random(System.currentTimeMillis() * 321221);
        this.randomHelper = new RandomService(r);
        this.vectorHelper = new VectorService(r, this.randomHelper);
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
    }

    @Override
    public boolean drawEmitterBox() {
        return this.emitterPreferences.drawEmitterBox;
    }

    @Override
    public Vector getEmitterBox() {
        return this.emitterPreferences.emitter.getSum();
    }

    @Override
    public float getParticleSize() {
        return this.particlePreferences.particleSize;
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

}
