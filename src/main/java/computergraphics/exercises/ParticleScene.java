package computergraphics.exercises;

import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.TimerTickable;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellcs on 07.03.17.
 */
public abstract class ParticleScene extends computergraphics.framework.Scene  {


    List<TimerTickable> tickableList = new ArrayList<>();
    EmitterPreferences emitterPreferences;
    ParticlePreferences particlePreferences;
    boolean paused = false;

    public ParticleScene(int timerTimeout, Shader.ShaderMode shaderMode, INode.RenderMode renderMode) {
        super(timerTimeout, shaderMode, renderMode);
    }


    @Override
    public void timerTick(int counter) {
        if (paused)
            return;

        for (TimerTickable timerTickable : tickableList) {
            timerTickable.timerTick(counter);
        }

    }

    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == 'p' || keyCode == 'P') {
            paused = !paused;
        } else if (keyCode == 'b' || keyCode == 'B') {
            emitterPreferences.drawEmitterBox = !emitterPreferences.drawEmitterBox;
        }
    }

}
