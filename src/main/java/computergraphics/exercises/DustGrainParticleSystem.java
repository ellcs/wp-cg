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
public class DustGrainParticleSystem extends computergraphics.framework.Scene {

    List<TimerTickable> tickableList = new ArrayList<>();
    boolean paused = false;

    public DustGrainParticleSystem() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(3, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

        EmitterPreferences emitterPreferences = new EmitterPreferences();
        emitterPreferences.maximumParticles = 400;
        emitterPreferences.emitterSize = new Vector(2,2,2);
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 50f;
        emitterPreferences.spawnRate.minPerMilliSec = 35f;

        ParticlePreferences particlePreferences = new ParticlePreferences();
        particlePreferences.particleSize = 2.5f;
        particlePreferences.creation.startColor = Colors.white;
        particlePreferences.creation.startSpeed = new Vector(0.00001, 0.000001, 0);
        particlePreferences.life.amountOfForces = 2;
        particlePreferences.life.forceLength = 0.0001f;
        particlePreferences.life.forceBoxSize = new Vector(1,1,1);
        particlePreferences.dead.minimumLifetimeInMilliSec = 1500;
        particlePreferences.dead.maximumLifetimeInMilliSec = particlePreferences.dead.minimumLifetimeInMilliSec;

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
        new DustGrainParticleSystem();
    }

}
