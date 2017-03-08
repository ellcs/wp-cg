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
public class FireParticleSystemScene extends ParticleScene {

    public FireParticleSystemScene() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(3, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

        emitterPreferences = new EmitterPreferences();
        emitterPreferences.maximumParticles = 3000;
        emitterPreferences.emitterSize = new Vector(0.3,0.1,0.3);
        emitterPreferences.drawEmitterBox = true;
        emitterPreferences.spawnRate.maxPerMilliSec = 400f;
        emitterPreferences.spawnRate.minPerMilliSec = 100f;

        particlePreferences = new ParticlePreferences();
        particlePreferences.particleSize = 5f;
        particlePreferences.creation.minStartColor = Colors.red;
        particlePreferences.creation.minStartSpeed = new Vector(0, 0, 0);
        particlePreferences.life.minimumColorDifferenceInMillisec = new Vector(-0.0005, 0, 0, -0.01);
        particlePreferences.life.weight = 1000000000;
        particlePreferences.life.amountOfForces = 2;
        particlePreferences.life.forceBoxPosition = new Vector(0,0.1,0);
        particlePreferences.life.forceBoxSize = new Vector(2,0.3,2);
        particlePreferences.dead.minimumLifetimeInMilliSec = 300;
        particlePreferences.dead.maximumLifetimeInMilliSec = particlePreferences.dead.minimumLifetimeInMilliSec;

        Emitter e = new Emitter(emitterPreferences, particlePreferences);
        EmitterNode emitterNode = new EmitterNode(e);
        this.tickableList.add(emitterNode);

        getRoot().addChild(emitterNode);
    }

    public static void main(String[] args) {
        new FireParticleSystemScene();
    }

}
