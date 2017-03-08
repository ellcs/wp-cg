package computergraphics.exercises;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.particle_system.EmitterNode;
import computergraphics.particle_system.Emitter;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

/**
 * Created by ellcs on 06.03.17.
 */
public class DustGrainParticleSystem extends ParticleScene {

    public DustGrainParticleSystem() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(3, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

        emitterPreferences = new EmitterPreferences();
        emitterPreferences.maximumParticles = 70;
        emitterPreferences.emitterSize = new Vector(1,1,1);
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 10f;
        emitterPreferences.spawnRate.minPerMilliSec = 5f;

        particlePreferences = new ParticlePreferences();
        particlePreferences.particleSize = 1.5f;
        particlePreferences.creation.startColors = new Vector[] {
                Colors.white,
                Colors.white.subtract(new Vector(50,50,50,0.3)),
        };
        // add random vector for the startSpeed
        // then they fly curves :)
//        particlePreferences.creation.startSpeed = new Vector(0.00001, 0.00001, 0.00001);
        particlePreferences.creation.startSpeed = new Vector(0.0001, 0.0001, 0.0001);
        particlePreferences.life.minimumColorDifferenceInMillisec = new Vector(-0.05, -0.05, -0.05, -0.001);
        particlePreferences.life.maximumColorDifferenceInMillisec = new Vector(-0.01, -0.01, -0.01, -0.01);
        particlePreferences.life.weight = 5000000;
        particlePreferences.life.amountOfForces = 3;
        particlePreferences.life.forceBoxSize = new Vector(1,1,1);
        particlePreferences.dead.minimumLifetimeInMilliSec = 2500;
        particlePreferences.dead.maximumLifetimeInMilliSec = particlePreferences.dead.minimumLifetimeInMilliSec;

        Emitter e = new Emitter(emitterPreferences, particlePreferences);
        EmitterNode emitterNode = new EmitterNode(e);
        this.tickableList.add(emitterNode);

        getRoot().addChild(emitterNode);
    }

    public static void main(String[] args) {
        new DustGrainParticleSystem();
    }

}
