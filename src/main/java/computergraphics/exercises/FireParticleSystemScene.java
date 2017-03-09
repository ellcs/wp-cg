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
public class FireParticleSystemScene extends ParticleScene {

    public FireParticleSystemScene() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(3, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

        emitterPreferences = new EmitterPreferences();
        emitterPreferences.maximumParticles = 5000;
        emitterPreferences.emitterRange = new Range(Vector.zero(), new Vector(0.3,0.1,0.3));
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 250f;
        emitterPreferences.spawnRate.minPerMilliSec = 150f;

        particlePreferences = new ParticlePreferences();
        particlePreferences.particleSize = 4f;
        particlePreferences.creation.startColors = new Vector[] {
                Colors.fireRed,
                Colors.red,
                Colors.brightRed
        };
        particlePreferences.creation.startDirectionRange = new Range(new Vector(0,0,0), new Vector(0,0,0));

        particlePreferences.life.minimumColorDifferenceInMilliSec = new Vector(-0.0005, 0, 0, -0.01);
        particlePreferences.life.maximumColorDifferenceInMilliSec = new Vector(-0.0003, -0.003, 0, -0.001);
        particlePreferences.life.weight = 10000;
        particlePreferences.life.amountOfForces = 1;
        particlePreferences.life.forceRange = new Range(new Vector(0,1, 0), new Vector(0.2,1, 0.2));

        particlePreferences.dead.minimumLifetimeInMilliSec = 300;
        particlePreferences.dead.maximumLifetimeInMilliSec = 700;

        TranslationNode translationNode = new TranslationNode(new Vector(1, 0,0));
        Emitter e = new Emitter(emitterPreferences, particlePreferences);
        EmitterNode emitterNode = new EmitterNode(e);
        this.tickableList.add(emitterNode);

        translationNode.addChild(emitterNode);
//        getRoot().addChild(translationNode);
        getRoot().addChild(emitterNode);
    }

    public static void main(String[] args) {
        new FireParticleSystemScene();
    }

}
