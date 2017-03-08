package computergraphics.particle_system.particle;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.particle_system.services.RandomService;
import computergraphics.particle_system.services.VectorService;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

/**
 * Created by ellcs on 06.03.17.
 */
public class ParticleFactory {

    ParticlePreferences particlePreferences;

    EmitterPreferences emitterPreferences;

    RandomService randomHelper;

    VectorService vectorHelper;

    public ParticleFactory(ParticlePreferences particlePreferences,
                           EmitterPreferences emitterPreferences,
                           RandomService randomHelper,
                           VectorService vectorHelper) {
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
//        int amountOfForces = this.particlePreferences.life.amountOfForces;
//        Vector range = this.particlePreferences.life.forceBoxSize;
//        range.addSelf(this.particlePreferences.life.forceBoxPosition);
//        Vector[] forces = new Vector[amountOfForces];
//        for (int i = 0; i < amountOfForces; i++) {
//            Vector force = vectorHelper.getRandomVectorInRange(range);
//            forces[i] = force;
//        }
        particle.forces = new Vector[1];
        Vector range = this.particlePreferences.life.forceBoxSize;
        particle.forces[0] = vectorHelper.getRandomVectorInRange(range);
    }

    private void setColorDifferenceInMillisec(Particle particle) {
        Vector minimumColorDifference = this.particlePreferences.life.minimumColorDifferenceInMillisec;
        Vector maximumColorDifference = this.particlePreferences.life.minimumColorDifferenceInMillisec;
        // when they're equal use one :)
        if (minimumColorDifference.equals(maximumColorDifference)) {
            particle.colorDifferenceInMilliSec = minimumColorDifference.dublicate();
        } else {
            Vector colorDifference = this.vectorHelper.getRandomVectorBetween(minimumColorDifference, maximumColorDifference);
            particle.colorDifferenceInMilliSec = colorDifference;
        }
    }

    private void setActualSpeed(Particle particle) {
        particle.actualSpeed = this.particlePreferences.creation.startSpeed.dublicate();
    }

    private void setOwnMaximumLifetime(Particle particle) {
        long min = this.particlePreferences.dead.minimumLifetimeInMilliSec;
        long max = this.particlePreferences.dead.maximumLifetimeInMilliSec;
        particle.ownMaximumLifetime = this.randomHelper.randomBetween(min, max);
    }

    private void setStartColor(Particle particle) {
        Vector[] startColors = this.particlePreferences.creation.startColors;;
        int randomIndex = randomHelper.getRandomIndex(startColors.length);
        particle.renderVertex.color = startColors[randomIndex].dublicate();
    }
}
