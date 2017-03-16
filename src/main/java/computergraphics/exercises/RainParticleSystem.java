package computergraphics.exercises;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.particle_system.EmitterNode;
import computergraphics.particle_system.Emitter;
import computergraphics.particle_system.Range;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

/**
 * Created by ellcs on 06.03.17.
 */
public class RainParticleSystem extends ParticleScene {


    public RainParticleSystem() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(3, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

        emitterPreferences = new EmitterPreferences();
        emitterPreferences.maximumParticles = 200;
        emitterPreferences.emitterRange = new Range(new Vector(0,1,0), new Vector(1,0.2,1));
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 100f;
        emitterPreferences.spawnRate.minPerMilliSec = 35f;

        particlePreferences = new ParticlePreferences();
        particlePreferences.particleSize = 2f;
        particlePreferences.creation.startColors = new Vector[] {
                Colors.blue,
                Colors.darkBlue
        };
        particlePreferences.creation.startSpeed = 0.001;
        particlePreferences.creation.startDirectionRange = new Range(new Vector(0,-1,0), new Vector(0,0.01,0));

        // nice values for the weight:
//         particlePreferences.life.weight = 1000000;
//         particlePreferences.life.weight = 0;
        particlePreferences.life.weight = 10000000;
        particlePreferences.life.minimumColorDifferenceInMilliSec = new Vector(0, 0, 0, 0);
        particlePreferences.life.maximumColorDifferenceInMilliSec = new Vector(0, 0, 0, 0);
        particlePreferences.life.forceRange = new Range(new Vector(3,0,0), new Vector(0.3,0.3,0.3));

        particlePreferences.dead.minimumLifetimeInMilliSec = 3000;
        particlePreferences.dead.maximumLifetimeInMilliSec = particlePreferences.dead.minimumLifetimeInMilliSec;

        Emitter e = new Emitter(emitterPreferences, particlePreferences);
        EmitterNode emitterNode = new EmitterNode(e);
        this.tickableList.add(emitterNode);

//        getRoot().addChild(emitterNode);

        TranslationNode translationNode = new TranslationNode(new Vector(-1, 1, 0));
        translationNode.addChild(emitterNode);
        getRoot().addChild(translationNode);
    }

    public static void main(String[] args) {
        new RainParticleSystem();
    }

}
