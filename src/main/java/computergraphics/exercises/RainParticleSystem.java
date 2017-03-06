package computergraphics.exercises;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.TimerTickable;
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
        super(40, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

        EmitterPreferences emitterPreferences = new EmitterPreferences();
        emitterPreferences.maximumParticles = 40;
        emitterPreferences.emitterSize = new Vector(1,1,1);
        emitterPreferences.spawnRate.maxPerMilliSec = 0.5f;
        emitterPreferences.spawnRate.minPerMilliSec = 0.01f;

        ParticlePreferences particlePreferences = new ParticlePreferences();
        particlePreferences.creation.startColor = Colors.transparentAzure;
        particlePreferences.dead.minimumLifetimeInMilliSec = 1000;
        particlePreferences.dead.maximumLifetimeInMilliSec = 3000;


        Emitter e = new Emitter(emitterPreferences, particlePreferences);
        EmitterNode emitterNode = new EmitterNode(e);
        this.tickableList.add(emitterNode);

        getRoot().addChild(emitterNode);
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
