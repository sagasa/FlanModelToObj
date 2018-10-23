package com.flansmod.client.tmt;

/**使いにくすぎたのでほぼ改変
 * 実質フォーマットを合わせただけのVertUV*/
public class Coord2D
{
	public Coord2D(double x, double y)
	{
		this(x, y, (int)Math.floor(x), (int)Math.floor(y));
	}


	public Coord2D(double x, double y, int u, int v)
	{
		this(x,y,0d,u,v);
	}
	public Coord2D(double x, double y,double z, int u, int v)
	{
		xCoord = x;
		yCoord = y;
		zCoord = z;
		uCoord = u;
		vCoord = v;
	}

	public Coord2D rotate(float xRot, float yRot, float zRot)
	{
		float x = xRot;
		float y = yRot;
		float z = zRot;
		float xC = (float) Math.cos(x);
		float xS = (float) Math.sin(x);
		float yC = (float) Math.cos(y);
		float yS = (float) Math.sin(y);
		float zC = (float) Math.cos(z);
		float zS = (float) Math.sin(z);

		double xVec = xCoord;
		double yVec = yCoord;
		double zVec = zCoord;

		// rotation around x
		double xy = xC*yVec - xS*zVec;
		double xz = xC*zVec + xS*yVec;
		// rotation around y
		double yz = yC*xz - yS*xVec;
		double yx = yC*xVec + yS*xz;
		// rotation around z
		double zx = zC*yx - zS*xy;
		double zy = zC*xy + zS*yx;

		xCoord = zx;
		yCoord = zy;
		zCoord = yz;
		return new Coord2D(zx, zy, yz,uCoord ,vCoord);
	}

	public double xCoord;
	public double yCoord;
	public double zCoord;
	public int uCoord;
	public int vCoord;
}
