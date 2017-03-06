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
public class RainParticleSystem extends computergraphics.framework.Scene {

    List<TimerTickable> tickableList = new ArrayList<>();
    boolean paused = false;

    public RainParticleSystem() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(3, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

        EmitterPreferences emitterPreferences = new EmitterPreferences();
        emitterPreferences.maximumParticles = 350;
        emitterPreferences.emitterSize = new Vector(1,0.1,1);
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 8f;
        emitterPreferences.spawnRate.minPerMilliSec = 3f;

        ParticlePreferences particlePreferences = new ParticlePreferences();
        particlePreferences.creation.startColor = Colors.blue;
        particlePreferences.creation.startForce = new Vector(0.0001, -0.009, 0);
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

    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == 'p') {
            paused = !paused;
        }
    }

    @Override
    public void timerTick(int counter) {
        if (paused)
            return;

        for (TimerTickable timerTickable : tickableList) {
            timerTickable.timerTick(counter);
        }

    }

    public static void main(String[] args) {
        new RainParticleSystem();
    }

}
