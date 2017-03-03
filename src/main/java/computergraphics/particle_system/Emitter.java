package computergraphics.particle_system;

import computergraphics.framework.rendering.RenderVertex;
import computergraphics.particle_system.preferences.EmitterPreferences;
import computergraphics.particle_system.preferences.ParticlePreferences;

import java.util.List;

/**
 * Created by ellcs on 03.03.17.
 */
public class Emitter {

    private EmitterPreferences emitterPreferences;
    private ParticlePreferences particlePreferences;

    // private void Particle[] particles;

    public Emitter(EmitterPreferences preferences, ParticlePreferences particlePreferences) {
        this.emitterPreferences = preferences;
        this.particlePreferences = particlePreferences;
    }

    /**
     * Updates all particles, removes dead ones and creates new ones.
     * @param deltaTime is the last t
     */
    public void update(float deltaTime) {
        updateAllParticles(deltaTime);
        removeDeadParticles();
        createNewParticles(deltaTime);
        sortParticles();
    }

    // public Set<Particle> getParticles()
    //
    public List<RenderVertex> getParticlesAsRenderVertices() {
//        List<RenderVertex> renderVertices = new ArrayList<>();
//        for (Particle p : particles) {
//            renderVertices = p.getRenderVertex();
//        }
//        return renderVertices;
        return null;
    }

    private void updateAllParticles(float deltaTime) {
        // for all particles p:
        // p.update(deltaTime);
    }

    private void removeDeadParticles() {

    }

    private void createNewParticles(float deltaTime) {

    }

    private void sortParticles() {

    }

}
