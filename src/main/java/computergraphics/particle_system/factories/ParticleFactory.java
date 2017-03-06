package computergraphics.particle_system.factories;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.particle_system.helpers.RandomHelper;
import computergraphics.particle_system.helpers.VectorHelper;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

/**
 * Created by ellcs on 06.03.17.
 */
public class ParticleFactory {

    ParticlePreferences particlePreferences;

    EmitterPreferences emitterPreferences;

    RandomHelper randomHelper;

    VectorHelper vectorHelper;

    public ParticleFactory(ParticlePreferences particlePreferences,
                           EmitterPreferences emitterPreferences,
                           RandomHelper randomHelper,
                           VectorHelper vectorHelper) {
        this.particlePreferences = particlePreferences;
        this.emitterPreferences = emitterPreferences;
        this.randomHelper = randomHelper;
        this.vectorHelper = vectorHelper;
    }

    public Particle createParticle() {
        Particle newParticle = new Particle();
        Vector position = vectorHelper.getRandomVectorInRange(this.emitterPreferences.emitterSize);
        newParticle.renderVertex = new RenderVertex(position, Vector.zero(), Colors.darkGreen);
        setOwnMaximumLifetime(newParticle);
        setStartColor(newParticle);
        return newParticle;
    }

    private void setOwnMaximumLifetime(Particle particle) {
        long min = this.particlePreferences.dead.minimumLifetimeInMilliSec;
        long max = this.particlePreferences.dead.maximumLifetimeInMilliSec;
        particle.ownMaximumLifetime = this.randomHelper.randomBetween(min, max);
    }

    private void setStartColor(Particle particle) {
        Vector startColor = this.particlePreferences.creation.startColor;
        particle.renderVertex.color = startColor;
    }
}
