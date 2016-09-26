package computergraphics.framework.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.framework.math.Matrix;
import computergraphics.framework.math.Vector;
import computergraphics.framework.rendering.RenderVertex;
import computergraphics.framework.rendering.VertexBufferObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 9/26/16.
 */
public class CuboidNode extends LeafNode  {

    private final double xLength;
    private final double yLength;
    private final double zLength;
    private final Vector color;

    private double halfXLength;
    private double halfYLength;
    private double halfZLength;

    /**
     * VBO.
     */
    private VertexBufferObject vbo = new VertexBufferObject();

    /**
     * Constructor.
     */
    public CuboidNode(double xLength, double yLength, double zLength, Vector color) {
        this.xLength = xLength;
        this.yLength = yLength;
        this.zLength = zLength;
        this.color = color;
        this.halfXLength = xLength / 2.0;
        this.halfYLength = yLength / 2.0;
        this.halfZLength = zLength / 2.0;
        createVbo();
    }

    /**
     * Constructor.
     */
    public CuboidNode(double xLength, double yLength, double zLength) {
        this(xLength, yLength, zLength, new Vector(0.25, 0.25, 0.75, 1));
    }

    private void createVbo() {
        List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

        Vector p0 = new Vector(-halfXLength, -halfYLength, -halfZLength);
        Vector p1 = new Vector(halfXLength, -halfYLength, -halfZLength);
        Vector p2 = new Vector(halfXLength, halfYLength, -halfZLength);
        Vector p3 = new Vector(-halfXLength, halfYLength, -halfZLength);
        Vector p4 = new Vector(-halfXLength, -halfYLength, halfZLength);
        Vector p5 = new Vector(halfXLength, -halfYLength, halfZLength);
        Vector p6 = new Vector(halfXLength, halfYLength, halfZLength);
        Vector p7 = new Vector(-halfXLength, halfYLength, halfZLength);
        Vector n0 = new Vector(0, 0, -1);
        Vector n1 = new Vector(1, 0, 0);
        Vector n2 = new Vector(0, 0, 1);
        Vector n3 = new Vector(-1, 0, 0);
        Vector n4 = new Vector(0, 1, 0);
        Vector n5 = new Vector(0, -1, 0);

        AddSideVertices(renderVertices, p0, p1, p2, p3, n0, color);
        AddSideVertices(renderVertices, p1, p5, p6, p2, n1, color);
        AddSideVertices(renderVertices, p4, p7, p6, p5, n2, color);
        AddSideVertices(renderVertices, p0, p3, p7, p4, n3, color);
        AddSideVertices(renderVertices, p2, p6, p7, p3, n4, color);
        AddSideVertices(renderVertices, p5, p1, p0, p4, n5, color);

        vbo.Setup(renderVertices, GL2.GL_QUADS);
    }

    /**
     * Add 4 vertices to the array
     */
    private void AddSideVertices(List<RenderVertex> renderVertices, Vector p0,
                                 Vector p1, Vector p2, Vector p3, Vector normal, Vector color) {
        renderVertices.add(new RenderVertex(p3, normal, color));
        renderVertices.add(new RenderVertex(p2, normal, color));
        renderVertices.add(new RenderVertex(p1, normal, color));
        renderVertices.add(new RenderVertex(p0, normal, color));
    }

    @Override
    public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        if (mode == RenderMode.REGULAR) {
            vbo.draw(gl);
        }
    }
}
