package computergraphics.exercises;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.math.MathHelpers;
import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ObjReader;
import computergraphics.framework.mesh.ShadowTriangleMesh;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.JenkeTriangleMeshNode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.ScaleNode;
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
    super(25, Shader.ShaderMode.PHONG, INode.RenderMode.SHADOW_VOLUME);

    Vector lightPosition = new Vector(1, 1, 1);
    getRoot().setLightPosition(lightPosition);
    getRoot().setAnimated(true);

    RotationNode rotationNode = new RotationNode(new Vector(0, 1, 0), 0, 0.015);
    tickableList.add(rotationNode);

    ObjReader objReader = new ObjReader();
    ShadowTriangleMesh cow = new ShadowTriangleMesh();
    objReader.read("meshes/hemisphere.obj", cow);
    JenkeTriangleMeshNode cowNode = new JenkeTriangleMeshNode(cow, lightPosition, true);

    ShadowTriangleMesh plane = new ShadowTriangleMesh();
    objReader.read("meshes/square.obj", plane);
    JenkeTriangleMeshNode square = new JenkeTriangleMeshNode(plane, lightPosition, true);

    ScaleNode scaleNode = new ScaleNode(new Vector(5, 5, 5));
    scaleNode.addChild(square);
    RotationNode squareRotation = new RotationNode(new Vector(1, 0, 0), Math.PI/2);
    squareRotation.addChild(scaleNode);
    TranslationNode translationNode = new TranslationNode(new Vector(0, -0.5f, 0));
    translationNode.addChild(squareRotation);

    getRoot().addChild(translationNode);
    getRoot().addChild(cowNode);

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
