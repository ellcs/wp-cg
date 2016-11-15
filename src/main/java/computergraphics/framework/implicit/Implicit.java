package computergraphics.framework.implicit;

import java.util.ArrayList;
import java.util.List;

import computergraphics.framework.math.Vector;
import computergraphics.framework.mesh.ITriangleMesh;
import computergraphics.framework.mesh.Triangle;
import computergraphics.framework.mesh.Vertex;

/**
 * Created by alex on 11/4/16.
 */
public class Implicit {

  /**
   * Represents mapping between edges and their vertices.
   *
   *   int edge = 0;
   *   EDGEPOINTS[edge]
   *
   * The returned array gives you the indexes of the neighbour vertices.
   */
  private static final int[][] EDGEPOINTS = {
    { 0, 1 },
    { 1, 2 },
    { 2, 3 },
    { 3, 0 },
    { 4, 5 },
    { 5, 6 },
    { 6, 7 },
    { 7, 4 },
    { 0, 4 },
    { 1, 5 },
    { 3, 7 },
    { 2, 6 }
  };

  ITriangleMesh<Vertex, Triangle> triangleMesh;

  Lambda implicitFunction;

  final double iso;

  /**
   *
   * @param iso
   * @param implicitFunction
   * @param triangleMesh
   */
  public Implicit(Double iso, ITriangleMesh triangleMesh, Lambda implicitFunction) {
    this.iso = iso;
    this.implicitFunction = implicitFunction;
    this.triangleMesh = triangleMesh;
  }

  public ITriangleMesh generate(Vector size, Vector res) {
    final Vector base = new Vector(0, 0, 0);
    final double sizeX = size.get(0);
    final double sizeY = size.get(1);
    final double sizeZ = size.get(2);

    double stepX = sizeX * res.get(0);
    double stepY = sizeY * res.get(1);
    double stepZ = sizeZ * res.get(2);

    for(double x = -sizeX; x < sizeX - stepX; x += stepX) {
      for(double y = -sizeY; y < sizeY - stepY; y += stepY) {
        for(double z = -sizeZ; z < sizeZ - stepZ; z += stepZ) {
          List<Vector> points = new ArrayList<>(8);
          points.add(base.add(x, y, z));
          points.add(base.add(x + stepX, y, z));
          points.add(base.add(x + stepX, y + stepY, z));
          points.add(base.add(x, y + stepY, z));
          points.add(base.add(x, y, z + stepZ));
          points.add(base.add(x + stepX, y, z + stepZ));
          points.add(base.add(x + stepX, y + stepY, z + stepZ));
          points.add(base.add(x, y + stepY, z + stepZ));

          List<Double> values = new ArrayList<>(8);
          for(Vector point : points) {
            values.add(this.implicitFunction.f(point));
          }

          this.createTriangles(points, values);
        }
      }
    }

    System.out.println("Vertices: " + this.triangleMesh.getNumberOfVertices());
    System.out.println("Triangles: " + this.triangleMesh.getNumberOfTriangles());
    this.triangleMesh.finishLoad();
    return this.triangleMesh;
  }

  /**
   * Ensures list length of eight.
   */
  private void checkLength(List list) {
    if (list.size() != 8) {
      throw new IllegalArgumentException("Invalid size. Must have size 8.");
    }
  }

  /**
   * Checks if given value is bigger then iso. If so, 1 returned else 0.
   */
  private int bi(Double value) {
    return value > this.iso ? 1 : 0;
  }

  /**
   * bi = (vi > t)? 1 : 0
   * caseIndex = b1*1 + b2*2 + b3*4 + b4*8 + b5*16 + b6*32 + b7*64 + b8*128
   *
   * @param values as vi.
   * @return calculated caseIndex.
   */
  private int getCaseIndex(List<Double> values) {
    checkLength(values);
    int i = 1;
    int accu = 0;
    for (Double value : values) {
      accu += i * bi(value);
      i = i << 1;
    }
    return accu;
  }

  /**
   * Fills a marching cubes cube with triangles.
   * @param points or better said positions of the cube points.
   * @param values at these points.
   */
  private void createTriangles(List<Vector> points, List<Double> values) {
    List<Integer> bs = new ArrayList<>(values.size());
    int caseIndex = getCaseIndex(values);

    int minLookupIndex = caseIndex * 15;
    int maxLookupIndex = minLookupIndex + 14;

    int vertexCount = this.triangleMesh.getNumberOfVertices();
    for(int i = minLookupIndex, j = 0; i <= maxLookupIndex; i++, j++) {
      int value = LookUpTable.lookUp(i);
      if(value != -1) {
        int[] edges = EDGEPOINTS[value];

        Vector point1 = points.get(edges[0]);
        Vector point2 = points.get(edges[1]);
        Double value1 = values.get(edges[0]);
        Double value2 = values.get(edges[1]);

        Vector interpolated =  interpolate(point1, point2, value1, value2);
        this.triangleMesh.addVertex(interpolated);
        vertexCount++;
        if(vertexCount % 3 == 0) {
          this.triangleMesh.addTriangle(vertexCount - 3, vertexCount - 2, vertexCount - 1);
        }
      }
    }
  }

  /**
   * Interpolates a vector between two vectors point1 and point2 with the weights of value1 and value2.
   * @param value1 is the weight of point1.
   * @param value2 is the weight of point2.
   */
  private Vector interpolate(Vector point1, Vector point2, Double value1, Double value2) {
    double teh = (this.iso - value1) / (value2 - value1);
    return point1.multiply(1 - teh).add(point2.multiply(teh));
  }

//  private Vector interpolate(Vector point1, Vector point2, Double value1, Double value2) {
//    return point1.add(point2).multiply(0.5);
//  }

  public ITriangleMesh<Vertex, Triangle> getTriangleMesh() {
    return this.triangleMesh;
  }


}
