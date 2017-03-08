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
        setColorDifferenceInMillisec(newParticle);
        setOwnMaximumLifetime(newParticle);
        setStartColor(newParticle);
        setActualSpeed(newParticle);
        setWeight(newParticle);
        setForces(newParticle);
        return newParticle;
    }

    private void setWeight(Particle particle) {
        particle.weight = this.particlePreferences.life.weight;
    }

    private void setForces(Particle particle) {
        int amountOfForces = this.particlePreferences.life.amountOfForces;
        Vector range = this.particlePreferences.life.forceBoxSize;
        range.addSelf(this.particlePreferences.life.forceBoxPosition);
        Vector[] forces = new Vector[amountOfForces];
        for (int i = 0; i < amountOfForces; i++) {
            Vector force = vectorHelper.getRandomVectorInRange(range);
            forces[i] = force;
        }
        particle.forces = forces;
    }

    private void setColorDifferenceInMillisec(Particle particle) {
        particle.colorDifferenceInMilliSec = this.particlePreferences.life.minimumColorDifferenceInMillisec;
    }

    private void setActualSpeed(Particle particle) {
        particle.actualSpeed = this.particlePreferences.creation.minStartSpeed.dublicate();
    }

    private void setOwnMaximumLifetime(Particle particle) {
        long min = this.particlePreferences.dead.minimumLifetimeInMilliSec;
        long max = this.particlePreferences.dead.maximumLifetimeInMilliSec;
        particle.ownMaximumLifetimeInMilliSec = this.randomHelper.randomBetween(min, max);
    }

    private void setStartColor(Particle particle) {
        Vector minStartColor = this.particlePreferences.creation.minStartColor;
        Vector maxStartColor = this.particlePreferences.creation.maxStartColor;
        Vector startColor = vectorHelper.getVectorBetween(minStartColor, maxStartColor);
        particle.renderVertex.color = startColor;
    }
}
