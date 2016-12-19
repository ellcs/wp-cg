package computergraphics.framework.scenegraph.model;

import com.jogamp.opengl.GL2;

import computergraphics.math.Colors;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.framework.scenegraph.CylinderNode;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.ScaleNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TranslationNode;

/**
 * Created by alex on 9/24/16.
 */
public class TreeNode extends InnerNode {

  public TreeNode() {
    TranslationNode sphereTranslation =
        new TranslationNode(new Vector(0, 0, 0));
    ScaleNode trunkScaleNode = new ScaleNode(new Vector(0.5, 0.5, 1));
    SphereNode headNode = new SphereNode(0.5, 50, Colors.green);

    CylinderNode trunkNode = new CylinderNode(0.5, 1, 50, Colors.brown);
    RotationNode standUpTreeRotation = new RotationNode(new Vector(1, 0, 0), -Math.PI/2);

    trunkScaleNode.addChild(trunkNode);
    sphereTranslation.addChild(headNode);
    standUpTreeRotation.addChild(trunkScaleNode);
    standUpTreeRotation.addChild(headNode);
    addChild(standUpTreeRotation);
  }

  public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    super.traverse(gl, mode, modelMatrix);
  }

  public void timerTick(int counter) {
    super.timerTick(counter);
  }
}
