package computergraphics.framework.scenegraph.model;

import com.jogamp.opengl.GL2;
import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.scenegraph.*;

/**
 * Created by alex on 9/26/16.
 */
public class HelicopterNode extends InnerNode implements TimerTickable {

    private final float rotationSpeed = 0.3f;

    private RotationNode rotorRotation;

    public HelicopterNode() {
        rotorRotation = new RotationNode(new Vector(0, 1, 0), 0, rotationSpeed);

        // add main body
        SphereNode bodySphere = new SphereNode(1, 20, Colors.orange);
        addChild(bodySphere);

        // add tail
        SphereNode tailSphere = new SphereNode(1, 20, Colors.orange);
        TranslationNode translateTailSphere = new TranslationNode(new Vector(0.0, 0.0, 2.0));
        ScaleNode tailScaleNode = new ScaleNode(new Vector(0.6, 0.6, 1));
        CylinderNode tailCylinder = new CylinderNode(1, 2, 20, Colors.orange);
        addChild(tailScaleNode);
        tailScaleNode.addChild(translateTailSphere);
        translateTailSphere.addChild(tailSphere);
        tailScaleNode.addChild(tailCylinder);

        // add right feet
        CuboidNode rightFeet = new CuboidNode(0.2, 0.2, 2, Colors.black);
        TranslationNode rightFeetTranslation = new TranslationNode(new Vector(0.7, -0.7, 0));
        addChild(rightFeetTranslation);
        rightFeetTranslation.addChild(rightFeet);

        // add left feet
        CuboidNode leftFeet = new CuboidNode(0.2, 0.2, 2, Colors.black);
        TranslationNode leftFeetTranslation = new TranslationNode(new Vector(-0.7, -0.7, 0));
        leftFeetTranslation.addChild(leftFeet);
        addChild(leftFeetTranslation);

        // add rotors
        CuboidNode firstRotor = new CuboidNode(0.2, 0.2, 3, Colors.black);
        TranslationNode firstRotorTranslation = new TranslationNode(new Vector(0, 1, 0));
        firstRotorTranslation.addChild(firstRotor);
        rotorRotation.addChild(firstRotorTranslation);

        CuboidNode secondRotor = new CuboidNode(0.2, 0.2, 3, Colors.black);
        TranslationNode secondRotorTranslation = new TranslationNode(new Vector(0, 1, 0));
        secondRotorTranslation.addChild(secondRotor);
        RotationNode secondRotorRotation = new RotationNode(new Vector(0, 1, 0), Math.PI/2);
        secondRotorRotation.addChild(secondRotorTranslation);
        rotorRotation.addChild(secondRotorRotation);
        addChild(rotorRotation);
    }

    public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        super.traverse(gl, mode, modelMatrix);
    }

    @Override
    public void timerTick(int counter) {
        rotorRotation.timerTick(counter);
    }
}
