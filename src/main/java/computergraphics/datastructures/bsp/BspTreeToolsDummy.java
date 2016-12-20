package computergraphics.datastructures.bsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import computergraphics.math.PrincipalComponentAnalysis;
import computergraphics.math.Vector;

public class BspTreeToolsDummy {

  /**
   * Recursively create a BSP tree for a given set of points.
   * 
   * @param parentNode
   *          Parent scene graph node
   * @param allPoints
   *          List with all point positions in the dataset
   * @param pointIndices
   *          if indices used in the current recursive call
   */
  public BspTreeNode createBspTree(BspTreeNode parentNode, List<Vector> allPoints, List<Integer> pointIndices) {
    if (parentNode == null) {
      parentNode = new BspTreeNode();
    }

    if (pointIndices.size() == 1 || pointIndices.size() == 0) {
      return null;
    }
    System.out.println("pi: " + pointIndices.size());

    // default centroid (average of two points) and normals (difference) are
    // set here. these are valid, when there are only two points left.
    Vector v0 = allPoints.get(pointIndices.get(0));
    Vector v1 = allPoints.get(pointIndices.get(1));
    Vector centroid = v0.add(v1).multiply(0.5f);
    Vector normal = v0.subtract(v1).getNormalized();

    // when there are more then two points, we use pca in order get our
    // centroid and our normal (biggest eigenvektor).
    if (pointIndices.size() > 2) {
      PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis();

      for (Integer index : pointIndices) {
        pca.add(allPoints.get(index));
      }
      pca.applyPCA();
      centroid = pca.getCentroid();
      normal = pca.getBiggestEigenvector();
    }
    parentNode.setP(centroid);
    parentNode.setN(normal);

    List<Integer> posIndices = new ArrayList<>();
    List<Integer> negIndices = new ArrayList<>();

    // assign all points to pos or neg in parent node
    for (Integer i : pointIndices) {
      Vector p = allPoints.get(i);
      if (parentNode.IsPositive(p)) {
        posIndices.add(i);
      } else {
        negIndices.add(i);
      }
    }
    System.out.println("Pos: " + posIndices.size() + " " + posIndices.toString());
    System.out.println("Neg: " + negIndices.size() + " " + negIndices.toString());

    if (posIndices.size() == 1) {
      parentNode.AddElement(BspTreeNode.Orientation.POSITIVE, posIndices.get(0));
    } else {
      parentNode.SetChild(BspTreeNode.Orientation.POSITIVE, createBspTree(null, allPoints, posIndices));
    }

    if (posIndices.size() == 1) {
      parentNode.AddElement(BspTreeNode.Orientation.NEGATIVE, negIndices.get(0));
    } else {
      parentNode.SetChild(BspTreeNode.Orientation.NEGATIVE, createBspTree(null, allPoints, negIndices));
    }

    return parentNode;
  }

  /**
   * Compute the back-to-front ordering for all points in 'points' based on the
   * tree in 'node' and the given eye position
   * 
   * @param node
   *          Root node of the BSP tree
   * @param points
   *          List of points to be considered
   * @param eye
   *          Observer position
   * @return Sorted (back-to-front) list of points.
   */
  public List<Integer> getBackToFront(BspTreeNode node, List<Vector> points, Vector eye) {
    List<Integer> accu = new ArrayList<>();
    if (node != null) {
      if (node.IsPositive(eye)) {
        accu.addAll(getBackToFront(node.GetChild(BspTreeNode.Orientation.NEGATIVE), points, eye));
        accu.addAll(getBackToFront(node.GetChild(BspTreeNode.Orientation.POSITIVE), points, eye));
      } else {
        accu.addAll(getBackToFront(node.GetChild(BspTreeNode.Orientation.POSITIVE), points, eye));
        accu.addAll(getBackToFront(node.GetChild(BspTreeNode.Orientation.NEGATIVE), points, eye));
      }
    }
    return accu;
  }
}
