package computergraphics.exercises;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.math.Colors;
import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.HalfEdgeTriangleMesh;
import computergraphics.framework.mesh.ObjReader;
import computergraphics.framework.rendering.Shader;
import computergraphics.framework.scenegraph.*;
import computergraphics.framework.scenegraph.meshmodels.CowNode;

/**
 * Created by alex on 10/4/16.
 */
public class HalfEdgeScene extends computergraphics.framework.Scene {

  private static final long serialVersionUID = 8141036480333465137L;

  List<TimerTickable> tickableList = new ArrayList<>();
  boolean toggled = false;

  ObjReader objReader = new ObjReader();

  public HalfEdgeScene() {
    // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
    super(25, Shader.ShaderMode.PHONG, INode.RenderMode.REGULAR);

    getRoot().setLightPosition(new Vector(1, 1, 1));
    getRoot().setAnimated(true);

    RotationNode rotationNode = new RotationNode(new Vector(0, 1, 0), 0, 0.015);
    tickableList.add(rotationNode);

    ObjReader reader = new ObjReader();
    HalfEdgeTriangleMesh mesh = new HalfEdgeTriangleMesh();
    reader.read("meshes/hemisphere.obj", mesh);

    HalfEdgeTriangleMeshNode node = new HalfEdgeTriangleMeshNode(mesh, HalfEdgeTriangleMeshNode.NormalType.vertex, Colors.gray);

    rotationNode.addChild(node);
    getRoot().addChild(rotationNode);

//    getRoot().addChild(new LineNode(new Vector(0, 0, 0), new Vector(1, 0, 0)));

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
    new HalfEdgeScene();
  }
}
