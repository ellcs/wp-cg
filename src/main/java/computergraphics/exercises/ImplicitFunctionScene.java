package computergraphics.exercises;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.implicit.Implicit;
import computergraphics.framework.implicit.Lambda;
import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ObjReader;
import computergraphics.framework.mesh.TriangleMesh;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.INode;
import computergraphics.framework.scenegraph.RotationNode;
import computergraphics.framework.scenegraph.SphereNode;
import computergraphics.framework.scenegraph.TimerTickable;
import computergraphics.framework.scenegraph.TranslationNode;
import computergraphics.framework.scenegraph.TriangleMeshNode;

/**
 * Created by alex on 10/4/16.
 */
public class ImplicitFunctionScene extends computergraphics.framework.Scene {

  private static final long serialVersionUID = 8141036480333465137L;

  List<TimerTickable> tickableList = new ArrayList<>();
  boolean toggled = false;

  ObjReader objReader = new ObjReader();

  public ImplicitFunctionScene() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(25, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    RotationNode rotationNode = new RotationNode(new Vector(0, 1, 0), 0, 0.015);
    tickableList.add(rotationNode);

    Lambda sphere = vector -> {
      return vector.x() * vector.x() +
        vector.y() * vector.y() +
        vector.z() * vector.z() -
        // radius
        0.3;
    };

    TriangleMesh triangleMesh = new TriangleMesh();
    Implicit implicit = new Implicit(0.0, triangleMesh, sphere);
    implicit.generate(new Vector(0,0,0), new Vector(1.0,1.0,1.0), new Vector(0.05, 0.05, 0.05));

    getRoot().addChild(new TriangleMeshNode(triangleMesh));

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
    new ImplicitFunctionScene();
  }
}
