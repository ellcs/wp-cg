package computergraphics.framework.scenegraph.model;

import com.jogamp.opengl.GL2;

import java.util.Random;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.scenegraph.*;

/**
 * Created by Mooty on 26-Sep-16.
 */
public class CloudNode extends InnerNode {

    /**
     * A bounding box of an object specifies the most outer faces.
     * In this case that bounding box limits cloud sphere creations in box only.
     */
    public CloudNode(float xBounding, float yBounding, float zBounding) {
        Random random = new Random(System.currentTimeMillis());
        for (int t = 0; t < 7; t++) {
            float x = (random.nextFloat() * 2 - 1) % xBounding;
            float y = (random.nextFloat() * 2 - 1) % yBounding;
            float z = (random.nextFloat() * 2 - 1) % zBounding;
            addSphereToCloud(new Vector(x, y, z));
        }
    }

    public void addSphereToCloud(Vector relativePosition) {
        SphereNode sphereNode = new SphereNode(0.15, 50, Colors.transparentAzure);
        TranslationNode translationNode = new TranslationNode(relativePosition);

        addChild(translationNode);
        translationNode.addChild(sphereNode);
    }

    public void traverse(GL2 gl, INode.RenderMode mode, Matrix modelMatrix) {
        super.traverse(gl, mode, modelMatrix);
    }

    public void timerTick(int counter) {
        super.timerTick(counter);
    }
}



