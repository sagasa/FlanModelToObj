package com.flansmod.client.tmt;

/**
 * 使いにくすぎたのでほぼ改変 実質フォーマットを合わせただけのVertUV
 */
public class Coord2D {
	public Coord2D(double x, double y, double z) {
		this(x, y, z, (int) Math.floor(x), (int) Math.floor(y));
	}

	public Coord2D(double x, double y, int u, int v) {
		this(x, y, 0d, u, v);
	}

	public Coord2D(double x, double y, double z, int u, int v) {
		xCoord = x;
		yCoord = y;
		zCoord = z;
		uCoord = u;
		vCoord = v;
	}

	public Coord2D rotate(float rotateX, float rotateY, float rotateZ) {
		double x = xCoord;
		double y = yCoord;
		double z = zCoord;

		// X軸回転
		y = y * Math.cos(rotateX) - z * Math.sin(rotateX);
		z = y * Math.sin(rotateX) + z * Math.cos(rotateX);

		// Y軸回転
		x = x * Math.cos(rotateY) + z * Math.sin(rotateY);
		z = -x * Math.sin(rotateY) + z * Math.cos(rotateY);

		// Z軸回転
		x = x * Math.cos(rotateZ) - y * Math.sin(rotateZ);
		y = x * Math.sin(rotateZ) + y * Math.cos(rotateZ);

		return new Coord2D(x, y, z, uCoord, vCoord);
	}

	final public double xCoord;
	final public double yCoord;
	final public double zCoord;
	final public int uCoord;
	final public int vCoord;
}
