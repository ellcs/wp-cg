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
        setForces(newParticle);
        return newParticle;
    }

    private void setForces(Particle particle) {
        particle.forces = new Vector[1];
        particle.forces[0] = this.particlePreferences.life.force.dublicate();
//        int amountOfForces = this.particlePreferences.life.amountOfForces;
//        Vector range = this.particlePreferences.life.forceBoxSize;
//        range.addSelf(this.particlePreferences.life.forceBoxPosition);
//        Vector[] forces = new Vector[amountOfForces];
//        for (int i = 0; i < amountOfForces; i++) {
//            Vector force = vectorHelper.getRandomVectorInRange(range);
//            force.normalize();
//            force.multiplySelf(this.particlePreferences.life.forceLength);
//            forces[i] = force;
//        }
//        particle.forces = forces;
    }

    private void setColorDifferenceInMillisec(Particle particle) {
        particle.colorDifferenceInMilliSec = this.particlePreferences.life.minimumColorDifferenceInMillisec;
    }

    private void setActualSpeed(Particle particle) {
        particle.actualSpeed = this.particlePreferences.creation.startSpeed;
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
