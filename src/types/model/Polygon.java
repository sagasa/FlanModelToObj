package types.model;

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

	public Polygon translate(float x, float y, float z) {
		for (int i = 0; i < Vertex.length; i++) {
			Vertex[i] = Vertex[i].translate(x, y, z);

		}
		return this;
	}

	public Polygon rotate(float rotateX, float rotateY, float rotateZ) {
		for (int i = 0; i < Vertex.length; i++) {
			Vertex[i] = Vertex[i].rotate(rotateX, rotateY, rotateZ);
		}
		return this;
	}
}
