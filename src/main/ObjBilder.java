package main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import types.model.Polygon;
import types.model.VertexUV;

public class ObjBilder {
	private Map<Vertex, Integer> VertexMap = new LinkedHashMap<>();
	private Map<UV, Integer> UVMap = new LinkedHashMap<>();

	private StringBuilder sb = new StringBuilder("mtllib texture.mtl\n");

	public void addPart(Polygon[] polis, String name) {
		//長さ0ならブレイク
		if(polis.length==0) {
			return;
		}
		sb.append("g " + name.trim() + "\nusemtl ModelMaterial\n\n");
		for (Polygon poly : polis) {
			StringBuilder polysb = new StringBuilder("\nf");
			for (VertexUV vert : poly.Vertex) {
				Vertex pos = new Vertex(vert.X, vert.Y, vert.Z);
				if (!new ArrayList<>(VertexMap.keySet()).contains(pos)) {
					sb.append("v " + vert.X + " " + vert.Y + " " + vert.Z + "\n");
					VertexMap.put(pos, VertexMap.keySet().size() + 1);
				}

				UV uv = new UV(vert.U, vert.V);
				if (!new ArrayList<>(UVMap.keySet()).contains(uv)) {
					sb.append("vt " + vert.U + " " + vert.V + "\n");
					UVMap.put(uv, UVMap.keySet().size() + 1);
				}
				polysb.append(" " + VertexMap.get(pos) + "/" + UVMap.get(uv));
			}
			sb.append(polysb.toString() + "\n\n");
		}
	}

	public String flash() {
		return sb.toString();
	}

	public static String getMtl(String texture) {
		return "newmtl ModelMaterial\nKa 0.000000 0.000000 0.000000\nKd 0.000000 0.000000 0.000000\nKs 0.000000 0.000000 0.000000\nillum 0\nd 1.0000\nmap_Ka "
				+ texture + ".png\nmap_Kd " + texture + ".png";
	}

	private class Vertex {
		float X, Y, Z;

		public Vertex(float x, float y, float z) {
			X = x;
			Y = y;
			Z = z;
		}

		@Override
		public int hashCode() {
			return Objects.hash(X, Y, Z);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Vertex) {
				Vertex value = (Vertex) obj;
				return value.X == X && value.Y == Y && value.Z == Z;
			}
			return false;
		}
	}

	private class UV {
		float U, V;

		public UV(float u, float v) {
			U = u;
			V = v;
		}

		@Override
		public int hashCode() {
			return Objects.hash(U, V);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof UV) {
				UV value = (UV) obj;
				return value.U == U && value.V == V;
			}
			return false;
		}
	}
}
