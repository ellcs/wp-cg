package computergraphics.framework.scenegraph.model;

import com.jogamp.opengl.GL2;
import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.scenegraph.*;

/**
 * Created by Mooty on 26-Sep-16.
 */
public class CloudNode extends InnerNode {
    public CloudNode() {
        TranslationNode sphereTranslation =
                new TranslationNode(new Vector(0, 0, 0));
        SphereNode headNode = new SphereNode(0.8, 60, Colors.transparent_azure);
    }
    public void traverse(GL2 gl, INode.RenderMode mode, Matrix modelMatrix) {
        super.traverse(gl, mode, modelMatrix);
    }

    public void timerTick(int counter) {
        super.timerTick(counter);
    }
}



