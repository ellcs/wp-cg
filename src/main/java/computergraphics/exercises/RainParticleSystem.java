package computergraphics.exercises;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.TimerTickable;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.particle_system.EmitterNode;
import computergraphics.particle_system.Emitter;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellcs on 06.03.17.
 */
public class RainParticleSystem extends ParticleScene {


    public RainParticleSystem() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(3, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

        emitterPreferences = new EmitterPreferences();
        emitterPreferences.maximumParticles = 350;
        emitterPreferences.emitterSize = new Vector(1,0.2,1);
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 50f;
        emitterPreferences.spawnRate.minPerMilliSec = 35f;

        particlePreferences = new ParticlePreferences();
        particlePreferences.particleSize = 2f;
        particlePreferences.creation.startColor = Colors.blue;
        particlePreferences.creation.startSpeed = new Vector(0.00005, -0.0009, 0);
        particlePreferences.dead.minimumLifetimeInMilliSec = 2000;
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
