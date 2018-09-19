package types.model;

import com.flansmod.client.tmt.PositionTextureVertex;

public class Polygon {
	public Polygon(VertexUV[] vert) {
		Vertex = vert;
	}

	public Polygon() {
	}

	public VertexUV[] Vertex;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[vertexCount = " + Vertex.length + ",");
		for (VertexUV vert : Vertex) {
			str.append(vert.toString());
		}
		str.append("]");
		return super.toString() + str.toString();
	}

	public void translate(float x, float y, float z) {
		for (VertexUV ver : Vertex) {
			ver.X += x;
			ver.Y += y;
			ver.Z += z;
		}
	}
}
