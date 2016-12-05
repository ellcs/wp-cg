package computergraphics.exercises;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ObjReader;
import computergraphics.framework.mesh.ShadowTriangleMesh;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.JenkeTriangleMeshNode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TimerTickable;
import computergraphics.framework.scenegraph.TranslationNode;

/**
 * Created by alex on 10/4/16.
 */
public class ShadowScene extends computergraphics.framework.Scene {

  private static final long serialVersionUID = 8141036480333465137L;

  List<TimerTickable> tickableList = new ArrayList<>();
  boolean toggled = false;

  public ShadowScene() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(25, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

    Vector lightPosition = new Vector(1, 1, 1);
    getRoot().setLightPosition(lightPosition);
    getRoot().setAnimated(true);

    RotationNode rotationNode = new RotationNode(new Vector(0, 1, 0), 0, 0.015);
    tickableList.add(rotationNode);

    ObjReader objReader = new ObjReader();
    ShadowTriangleMesh triangleMesh = new ShadowTriangleMesh();
    objReader.read("meshes/cow.obj", triangleMesh);
    JenkeTriangleMeshNode node = new JenkeTriangleMeshNode(triangleMesh, lightPosition);

    getRoot().addChild(node);

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
    for (TimerTickable timerTickable : this.tickableList) {
      if (toggled) {
        timerTickable.timerTick(counter);
      }
    }
  }

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    new ShadowScene();
  }
}
