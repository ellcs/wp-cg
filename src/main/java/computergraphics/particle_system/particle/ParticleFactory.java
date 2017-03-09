package computergraphics.particle_system.particle;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.particle_system.Range;
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
        setPositionAndColor(newParticle);
        setColorDifferenceInMilliSec(newParticle);
        setOwnMaximumLifetime(newParticle);
        setStartColor(newParticle);
        setActualSpeed(newParticle);
        setWeight(newParticle);
        setForce(newParticle);
        return newParticle;
    }

    private void setPositionAndColor(Particle particle) {
        Vector position = vectorHelper.getRandomVectorInRange(this.emitterPreferences.emitterRange);
        particle.renderVertex = new RenderVertex(position, Vector.zero(), Colors.darkGreen);
    }

    private void setWeight(Particle particle) {
        particle.weight = this.particlePreferences.life.weight;
    }

    private void setForce(Particle particle) {
        if (this.particlePreferences.life.weight == 0.0) {
            return;
        }
        Range startForceRange = this.particlePreferences.life.forceRange;
        Vector randomForce = vectorHelper.getRandomVectorInRange(startForceRange);

        particle.forces = new Vector[1];
        particle.forces[0] = randomForce;
    }

    private void setColorDifferenceInMilliSec(Particle particle) {
        Vector minimumColorDifference = this.particlePreferences.life.minimumColorDifferenceInMilliSec;
        Vector maximumColorDifference = this.particlePreferences.life.minimumColorDifferenceInMilliSec;
        // when they're equal use one :)
        if (minimumColorDifference.equals(maximumColorDifference)) {
            particle.colorDifferenceInMilliSec = minimumColorDifference.dublicate();
        } else {
            Vector colorDifference = this.vectorHelper.getRandomVectorBetween(minimumColorDifference, maximumColorDifference);
            particle.colorDifferenceInMilliSec = colorDifference;
        }
    }

    private void setActualSpeed(Particle particle) {
        double speed = this.particlePreferences.creation.startSpeed;
        Vector startDirection = vectorHelper.getRandomVectorInRange(this.particlePreferences.creation.startDirectionRange);
        Vector startSpeed = startDirection.multiply(speed);
        particle.actualSpeed = startSpeed;
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
