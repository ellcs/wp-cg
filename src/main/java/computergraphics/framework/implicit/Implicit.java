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

  public ITriangleMesh generate(Vector base, Vector size, Vector res) {
    double sizeX = size.get(0);
    double sizeY = size.get(1);
    double sizeZ = size.get(2);

    double stepX = sizeX * res.get(0);
    double stepY = sizeY * res.get(1);
    double stepZ = sizeZ * res.get(2);

    for(double x = -sizeX; x < sizeX - stepX; x += stepX) {
      for(double y = -sizeY; y < sizeY - stepY; y += stepY) {
        for(double z = -sizeZ; z < sizeZ - stepZ; z += stepZ) {
//          System.out.println("Implicit.generate: " + x + ", " + y + ", " + z);
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
    for(Double  value : values)
    {
      bs.add(value > this.iso ? 1 : 0);
    }

    int caseIndex = 0;
    for(int i = 0; i < bs.size(); i++)
    {
      caseIndex += bs.get(i) * Math.pow(2, i);
    }

    int[] relevantValues = new int[15];
    int minLookupIndex = caseIndex * 15;
    int maxLookupIndex = minLookupIndex + 14;
    for(int i = minLookupIndex, j = 0; i <= maxLookupIndex; i++, j++)
    {
      relevantValues[j] = LookUpTable.lookUp(i);
    }

    int vertexCount = this.triangleMesh.getNumberOfVertices();
    for(int value : relevantValues)
    {
      if(value >= 0)
      {
        int[] edges = EDGEPOINTS[value];
        Vector intersection/* = points.get(edges[0]).add(points.get(edges[1])).multiply(0.5)*/;
        double teh = (this.iso - values.get(edges[0])) / (values.get(edges[1]) - values.get(edges[0]));
        intersection = points.get(edges[0]).multiply(1 - teh).add(points.get(edges[1]).multiply(teh));

        this.triangleMesh.addVertex(new Vector(intersection));
        vertexCount++;
        if(vertexCount % 3 == 0) {
          this.triangleMesh.addTriangle(vertexCount - 1, vertexCount - 2, vertexCount - 3);
        }
      }
    }


  }

  public ITriangleMesh<Vertex, Triangle> getTriangleMesh() {
    return this.triangleMesh;
  }


}
