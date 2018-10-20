package types.model;

import com.flansmod.client.tmt.Coord2D;

public class VertexUV {
	public VertexUV(float x, float y, float z, float u, float v) {
		X = x;
		Y = y;
		Z = z;
		U = u;
		V = v;
	}

	public VertexUV(float[] pos, float u, float v) {
		X = pos[0];
		Y = pos[1];
		Z = pos[2];
		U = u;
		V = v;
	}

	public VertexUV(Coord2D coord2d) {
		X = (float) coord2d.xCoord;
		Y = (float) coord2d.yCoord;
		Z = (float) coord2d.zCoord;
		U = coord2d.uCoord;
		V = coord2d.vCoord;
	}

	public VertexUV translate(float x, float y, float z) {
		return new VertexUV(X + x, Y + y, Z + z, U, V);
	}

	public VertexUV multiplication(float x, float y, float z) {
		return new VertexUV(X * x, Y * y, Z * z, U, V);
	}

	public VertexUV rotate(float pointX, float pointY, float pointZ, float rotateX, float rotateY, float rotateZ) {
		// rotateX
		double r = Math.toRadians(rotateX);

		float x = X;
		float y = Y;
		float z = Z;

		float f = y - pointY;
		float f1 = z - pointZ;
		y = (float) ((Math.cos(r) * f) + (Math.sin(r) * f));
		z = (float) ((-Math.sin(r) * f1) + (Math.cos(r) * f1));
		// rotateY
		r = Math.toRadians(rotateY);
		f = x - pointX;
		f1 = z - pointZ;
		x = (float) ((Math.cos(r) * f) + (Math.sin(r) * f));
		z = (float) ((-Math.sin(r) * f1) + (Math.cos(r) * f1));
		// rotateZ
		r = Math.toRadians(rotateZ);
		f = x - pointX;
		f1 = y - pointY;
		x = (float) ((Math.cos(r) * f) + (Math.sin(r) * f));
		y = (float) ((-Math.sin(r) * f1) + (Math.cos(r) * f1));
		return new VertexUV(x, y, z, U, V);
	}

	public final float U;
	public final float V;

	public final float X;
	public final float Y;
	public final float Z;

	@Override
	public String toString() {
		return super.toString() + "[X = " + X + ",Y = " + Y + ",Z = " + Z + ",U = " + U + ",V = " + V + "]";
	}
}