package computergraphics.exercises;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ObjReader;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.PointNode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TimerTickable;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.meshmodels.CowNode;

/**
 * Created by alex on 10/4/16.
 */
public class PointScene extends computergraphics.framework.Scene {

  private static final long serialVersionUID = 8141036480333465137L;

  List<TimerTickable> tickableList = new ArrayList<>();
  boolean toggled = false;

  PointNode pointNode;

  public PointScene() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(40, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

    TranslationNode translateNode = new TranslationNode(new Vector(1, 0,0));
    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    pointNode = new PointNode();
    translateNode.addChild(pointNode);
    getRoot().addChild(translateNode);

    // Light geometry
    TranslationNode lightTranslation =
        new TranslationNode(getRoot().getLightPosition());
    INode lightSphereNode = new SphereNode(0.1f, 10);
    lightTranslation.addChild(lightSphereNode);
    getRoot().addChild(lightTranslation);

  }

  @Override
  public void keyPressed(int keyCode) {
    toggled = !toggled;
  }

  @Override
  public void timerTick(int counter) {
    if (toggled)
      pointNode.timerTick(counter);
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    new PointScene();
  }
}
