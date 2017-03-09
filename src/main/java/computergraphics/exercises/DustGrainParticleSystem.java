package computergraphics.exercises;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.particle_system.EmitterNode;
import computergraphics.particle_system.Emitter;
import computergraphics.particle_system.Range;
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
        emitterPreferences.emitter = new Range(new Vector(0,0,0), new Vector(1,1,1));
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 10f;
        emitterPreferences.spawnRate.minPerMilliSec = 5f;

        particlePreferences = new ParticlePreferences();
        particlePreferences.particleSize = 1.5f;
        particlePreferences.creation.startColors = new Vector[] {
                Colors.white,
                Colors.white.subtract(new Vector(50,50,50,0.3)),
        };
        // add random vector for the startDirectionBoxSize
        // then they fly curves :)
//        particlePreferences.creation.startDirectionBoxSize = new Vector(0.00001, 0.00001, 0.00001);
        particlePreferences.creation.startSpeed = 0.0001;
        particlePreferences.creation.startDirectionRange = emitterPreferences.emitter;
        particlePreferences.life.minimumColorDifferenceInMilliSec = new Vector(-0.05, -0.05, -0.05, -0.001);
        particlePreferences.life.maximumColorDifferenceInMilliSec = new Vector(-0.01, -0.01, -0.01, -0.01);
        particlePreferences.life.weight = 5000000;
        particlePreferences.life.amountOfForces = 3;
        particlePreferences.life.forceRange = emitterPreferences.emitter;
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
