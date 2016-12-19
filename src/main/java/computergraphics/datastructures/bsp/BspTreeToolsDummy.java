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
    Vector v0 = allPoints.get(0);
    Vector v1 = allPoints.get(1);
    Vector centroid = v0.add(v1).multiply(0.5f);
    Vector normal = v0.subtract(v1).getNormalized();

    // when there are more then two points, we use pca in order get our
    // centroid and our normal (biggest eigenvektor).
    if (pointIndices.size() > 2) {
      System.out.println("foobar");
      PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis();
      pca.addAll(allPoints);
      pca.applyPCA();
      centroid = pca.getCentroid();
      normal = pca.getBiggestEigenvector();
    }
    parentNode.setN(normal);
    parentNode.setP(centroid);

    // assign all points to pos or neg in parent node
    for (int i = 0; i < pointIndices.size(); i++) {
      Vector point = allPoints.get(i);
      Integer index = pointIndices.get(i);
      parentNode.AddElement(point, index);
    }
    System.out.println("Pos: " + parentNode.getNumberOfElements(BspTreeNode.Orientation.POSITIVE));
    System.out.println("Neg: " + parentNode.getNumberOfElements(BspTreeNode.Orientation.NEGATIVE));

    recurse(parentNode, allPoints, BspTreeNode.Orientation.POSITIVE);
    recurse(parentNode, allPoints, BspTreeNode.Orientation.NEGATIVE);

    return parentNode;
  }

  /**
   * Fetches all indices for given orientation, from parentNode and sets a child for given orientation.
   */
  private void recurse(BspTreeNode parentNode, List<Vector> allPoints, BspTreeNode.Orientation orientation) {
    BspTreeNode child = new BspTreeNode();
    List<Integer> indicies = new ArrayList<>();
    for (int i = 0; i < parentNode.getNumberOfElements(orientation); i++) {
      int posIndex = parentNode.getElement(orientation, i);
      indicies.add(posIndex);
    }
    parentNode.SetChild(orientation, createBspTree(child, allPoints, indicies));
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
        if (node.getNumberOfElements(BspTreeNode.Orientation.NEGATIVE) == 1) {
          return Arrays.asList(node.getElement(BspTreeNode.Orientation.NEGATIVE, 0));
        }
        if (node.getNumberOfElements(BspTreeNode.Orientation.POSITIVE) == 1) {
          return Arrays.asList(node.getElement(BspTreeNode.Orientation.POSITIVE, 0));
        }
        accu.addAll(getBackToFront(node.GetChild(BspTreeNode.Orientation.POSITIVE), points, eye));
      } else {
        accu.addAll(getBackToFront(node.GetChild(BspTreeNode.Orientation.POSITIVE), points, eye));
        accu.addAll(getBackToFront(node.GetChild(BspTreeNode.Orientation.NEGATIVE), points, eye));
      }
    }
    return accu;
  }
}
