package computergraphics.framework.scenegraph.meshmodels;

import computergraphics.framework.mesh.ObjReader;
import computergraphics.framework.mesh.TriangleMesh;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.TriangleMeshNode;

/**
 * Created by alex on 10/4/16.
 */
public class CowNode extends InnerNode {

  public static final String assetPath = "meshes/cow.obj";

  /**
   * Load file cow.obj.
   */
  public CowNode() {
    ObjReader reader = new ObjReader();
    TriangleMesh triangleMesh = new TriangleMesh();
    reader.read(assetPath, triangleMesh);
    addChild(new TriangleMeshNode(triangleMesh));
  }

}
