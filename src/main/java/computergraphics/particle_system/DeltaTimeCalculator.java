package computergraphics.particle_system;

/**
 * Created by ellcs on 06.03.17.
 */
public class DeltaTimeCalculator {

    float timeAtLastFrame;

    /**
     * This is delta time.
     */
    float timeSinceLastFrame;

    public DeltaTimeCalculator() {
        this.timeAtLastFrame = currentTime();
    }

    private float currentTime() {
        return System.currentTimeMillis();
    }

    /**
     * Call this method every in frame to update the deltaTime;
     */
    public void tick() {
        this.timeSinceLastFrame = currentTime() - timeAtLastFrame;
        this.timeAtLastFrame = currentTime();
    }

    public float getDeltaTime() {
        return timeSinceLastFrame;
    }
}
