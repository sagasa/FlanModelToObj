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

	public VertexUV setUV(float u, float v) {
		return new VertexUV(X, Y, Z, u, v);
	}

	public VertexUV translate(float x, float y, float z) {
		return new VertexUV(X + x, Y + y, Z + z, U, V);
	}

	public VertexUV multiplication(float x, float y, float z) {
		return new VertexUV(X * x, Y * y, Z * z, U, V);
	}

	public VertexUV rotate(float pointX, float pointY, float pointZ, float rotateX, float rotateY, float rotateZ) {
		float x = X - pointX;
		float y = Y - pointY;
		float z = Z - pointZ;

		// X軸回転
		y = (float) (y * Math.cos(rotateX) - z * Math.sin(rotateX));
		z = (float) (y * Math.sin(rotateX) + z * Math.cos(rotateX));

		// Y軸回転
		x = (float) (x * Math.cos(rotateY) + z * Math.sin(rotateY));
		z = (float) (-x * Math.sin(rotateY) + z * Math.cos(rotateY));

		// Z軸回転
		x = (float) (x * Math.cos(rotateZ) - y * Math.sin(rotateZ));
		y = (float) (x * Math.sin(rotateZ) + y * Math.cos(rotateZ));

		return new VertexUV(x + pointX, y + pointY, z + pointZ, U, V);
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