package computergraphics.particle_system.services;

/**
 * Created by ellcs on 06.03.17.
 */
public class DeltaTimeCalculatorService {

    long timeAtLastFrame;

    /**
     * This is delta time.
     */
    long timeSinceLastFrame;

    public DeltaTimeCalculatorService() {
        this.timeAtLastFrame = currentTime();
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }

    /**
     * Call this method every in frame to update the deltaTime;
     */
    public void tick() {
        this.timeSinceLastFrame = currentTime() - timeAtLastFrame;
        this.timeAtLastFrame = currentTime();
    }

    public long getDeltaTime() {
        return timeSinceLastFrame;
    }
}
