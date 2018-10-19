package com.flansmod.client.tmt;

import java.util.Collection;

import com.flansmod.common.vector.Vector3f;

import types.model.Polygon;

public class Shape2D
{

	public Shape2D(Coord2D[] coordArray)
	{
		coords = coordArray;
	}

	public Collection<Polygon> extrude(float x, float y, float z, float rotX, float rotY, float rotZ, float depth, int u, int v, float textureWidth, float textureHeight, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, float[] faceLengths)
	{
		PositionTransformVertex[] verts = new PositionTransformVertex[coords.length * 2];
		PositionTransformVertex[] vertsTop = new PositionTransformVertex[coords.length];
		PositionTransformVertex[] vertsBottom = new PositionTransformVertex[coords.length];
		TexturedPolygon[] poly = new TexturedPolygon[coords.length + 2];

		Vector3f extrudeVector = new Vector3f(0, 0, depth);

		extrudeVector = setVectorRotations(extrudeVector, rotX, rotY, rotZ);

		if(faceLengths != null && faceLengths.length < coords.length)
			faceLengths = null;

		float totalLength = 0;

		for(int idx = 0; idx < coords.length; idx++)
		{
			Coord2D curCoord = coords[idx];
			Coord2D nextCoord = coords[(idx + 1) % coords.length];
			float texU1 = ((curCoord.uCoord + u) / textureWidth);
			float texU2 = ((shapeTextureWidth * 2 - curCoord.uCoord + u) / textureWidth);
			float texV = ((curCoord.vCoord + v) / textureHeight);

			Vector3f vecCoord = new Vector3f(curCoord.xCoord, curCoord.yCoord, 0);

			vecCoord = setVectorRotations(vecCoord, rotX, rotY, rotZ);

			verts[idx] = new PositionTransformVertex(
													x + (float)vecCoord.xCoord,
													y + (float)vecCoord.yCoord,
													z + (float)vecCoord.zCoord, texU1, texV);
			verts[idx + coords.length] = new PositionTransformVertex(
													x + (float)vecCoord.xCoord - (float)extrudeVector.xCoord,
													y + (float)vecCoord.yCoord - (float)extrudeVector.yCoord,
													z + (float)vecCoord.zCoord - (float)extrudeVector.zCoord, texU2, texV);

			vertsTop[idx] = new PositionTransformVertex(verts[idx]);
			vertsBottom[coords.length - idx - 1] = new PositionTransformVertex(verts[idx + coords.length]);

			if(faceLengths != null)
				totalLength+= faceLengths[idx];
			else
				totalLength+= Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2));
		}

		poly[coords.length] = new TexturedPolygon(vertsTop);
		poly[coords.length + 1] = new TexturedPolygon(vertsBottom);

		float currentLengthPosition = totalLength;

		for(int idx = 0; idx < coords.length; idx++)
		{
			Coord2D curCoord = coords.get(idx);
			Coord2D nextCoord = coords.get((idx + 1) % coords.length);
			float currentLength = (float)Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2));
			if(faceLengths != null)
				currentLength = faceLengths[faceLengths.length - idx - 1];
			float ratioPosition = currentLengthPosition / totalLength;
			float ratioLength = (currentLengthPosition - currentLength) / totalLength;

			float texU1 = ((ratioLength * sideTextureWidth + u) / textureWidth);
			float texU2 = ((ratioPosition * sideTextureWidth + u) / textureWidth);
			float texV1 = (((float)v + (float)shapeTextureHeight) / textureHeight);
			float texV2 = (((float)v + (float)shapeTextureHeight + sideTextureHeight) / textureHeight);

			PositionTransformVertex[] polySide = new PositionTransformVertex[4];

			polySide[0] = new PositionTransformVertex(verts[idx], texU2, texV1);
			polySide[1] = new PositionTransformVertex(verts[coords.length + idx], texU2, texV2);
			polySide[2] = new PositionTransformVertex(verts[coords.length + ((idx + 1) % coords.length)], texU1, texV2);
			polySide[3] = new PositionTransformVertex(verts[(idx + 1) % coords.length], texU1, texV1);

			poly[idx] = new TexturedPolygon(polySide);
			currentLengthPosition -= currentLength;
		}

		Shape3D shape3D = new Shape3D(verts, poly);
		for(TexturedPolygon face : shape3D.faces)
		{
			face.setInvertNormal(true);
		}

		return shape3D;
	}

	protected Vector3f setVectorRotations(Vector3f vector, float xRot, float yRot, float zRot)
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

		double xVec = vector.x;
		double yVec = vector.y;
		double zVec = vector.z;

		// rotation around x
		double xy = xC*yVec - xS*zVec;
		double xz = xC*zVec + xS*yVec;
		// rotation around y
		double yz = yC*xz - yS*xVec;
		double yx = yC*xVec + yS*xz;
		// rotation around z
		double zx = zC*yx - zS*xy;
		double zy = zC*xy + zS*yx;

		xVec = zx;
		yVec = zy;
		zVec = yz;

		return new Vector3f(xVec, yVec, zVec);
	}

	public Coord2D[] coords;
}
