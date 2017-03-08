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
        emitterPreferences.maximumParticles = 200;
        emitterPreferences.emitterSize = new Vector(1,1,1);
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 10f;
        emitterPreferences.spawnRate.minPerMilliSec = 5f;

        particlePreferences = new ParticlePreferences();
        particlePreferences.particleSize = 1f;
        particlePreferences.creation.minStartColor = Colors.white;
        // add random vector for the minStartSpeed
        // then they fly curves :)
         particlePreferences.creation.minStartSpeed = new Vector(0.0001, 0.0001, 0.0001);
//        particlePreferences.creation.minStartSpeed = new Vector(0, 0, 0);
        particlePreferences.life.minimumColorDifferenceInMillisec = new Vector(-0.05, -0.05, -0.05, -0.001);
        particlePreferences.life.weight = 10000000;
        particlePreferences.life.amountOfForces = 3;
        particlePreferences.life.forceBoxSize = emitterPreferences.emitterSize.dublicate();
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
