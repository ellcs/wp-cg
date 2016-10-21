package computergraphics.framework.scenegraph.meshmodels;

import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ObjReader;
import computergraphics.framework.mesh.Triangle;
import computergraphics.framework.mesh.TriangleMesh;
import computergraphics.framework.scenegraph.InnerNode;
import computergraphics.framework.scenegraph.LineNode;
import computergraphics.framework.scenegraph.TriangleMeshNode;

/**
 * Created by alex on 10/4/16.
 */
public class CowNode extends InnerNode {

  public static boolean SHOW_NORMALS = true;
  public static float LINE_LENGTH = 0.015f;

  public static final String assetPath = "meshes/cow.obj";

  /**
   * Load file cow.obj.
   */
  public CowNode() {
    ObjReader reader = new ObjReader();
    TriangleMesh triangleMesh = new TriangleMesh();
    reader.read(assetPath, triangleMesh);
    addChild(new TriangleMeshNode(triangleMesh));

    if (SHOW_NORMALS) {
      for (int i = 0; i < triangleMesh.getNumberOfTriangles(); i++) {
        Triangle triangle = triangleMesh.getTriangle(i);
        Vector p0 = triangleMesh.getTriangleVectorByIndex(triangle, 0);
        Vector p1 = triangleMesh.getTriangleVectorByIndex(triangle, 1);
        Vector p2 = triangleMesh.getTriangleVectorByIndex(triangle, 2);

        Vector normal = triangle.getNormal();
        Vector middle = p0.add(p1).add(p2);
        middle.multiplySelf(1f / 3f);
        Vector end = middle.add(normal.multiply(LINE_LENGTH));
        addChild(new LineNode(end, middle));
      }
    }
  }

}
