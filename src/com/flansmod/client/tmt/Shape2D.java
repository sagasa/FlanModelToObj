package com.flansmod.client.tmt;

import com.flansmod.common.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collections;

public class Shape2D
{
	public Shape2D()
	{
		coords = new ArrayList<Coord2D>();
	}

	public Shape2D(Coord2D[] coordArray)
	{
		coords = new ArrayList<Coord2D>();

		Collections.addAll(coords, coordArray);
	}

	public Shape2D(ArrayList<Coord2D> coordList)
	{
		coords = coordList;
	}

	public Coord2D[] getCoordArray()
	{
		return (Coord2D[]) coords.toArray();
	}

	public Shape3D extrude(float x, float y, float z, float rotX, float rotY, float rotZ, float depth, int u, int v, float textureWidth, float textureHeight, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, float[] faceLengths)
	{
		PositionTextureVertex[] verts = new PositionTextureVertex[coords.size() * 2];
		PositionTextureVertex[] vertsTop = new PositionTextureVertex[coords.size()];
		PositionTextureVertex[] vertsBottom = new PositionTextureVertex[coords.size()];
		TexturedPolygon[] poly = new TexturedPolygon[coords.size() + 2];

		Vector3f extrudeVector = new Vector3f(0, 0, depth);

		extrudeVector = setVectorRotations(extrudeVector, rotX, rotY, rotZ);

		if(faceLengths != null && faceLengths.length < coords.size())
			faceLengths = null;

		float totalLength = 0;

		for(int idx = 0; idx < coords.size(); idx++)
		{
			Coord2D curCoord = coords.get(idx);
			Coord2D nextCoord = coords.get((idx + 1) % coords.size());
			float texU1 = ((curCoord.uCoord + u) / textureWidth);
			float texU2 = ((shapeTextureWidth * 2 - curCoord.uCoord + u) / textureWidth);
			float texV = ((curCoord.vCoord + v) / textureHeight);

			Vector3f vecCoord = new Vector3f(curCoord.xCoord, curCoord.yCoord, 0);

			vecCoord = setVectorRotations(vecCoord, rotX, rotY, rotZ);

			verts[idx] = new PositionTextureVertex(
					x + (float)vecCoord.x,
					y + (float)vecCoord.y,
					z + (float)vecCoord.z, texU1, texV);
			verts[idx + coords.size()] = new PositionTextureVertex(
					x + (float)vecCoord.x - (float)extrudeVector.x,
					y + (float)vecCoord.y - (float)extrudeVector.y,
					z + (float)vecCoord.z - (float)extrudeVector.z, texU2, texV);

			vertsTop[idx] = verts[idx];
			vertsBottom[coords.size() - idx - 1] = verts[idx + coords.size()];

			if(faceLengths != null)
				totalLength+= faceLengths[idx];
			else
				totalLength+= Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2));
		}

		poly[coords.size()] = new TexturedPolygon(vertsTop);
		poly[coords.size() + 1] = new TexturedPolygon(vertsBottom);

		float currentLengthPosition = totalLength;

		for(int idx = 0; idx < coords.size(); idx++)
		{
			Coord2D curCoord = coords.get(idx);
			Coord2D nextCoord = coords.get((idx + 1) % coords.size());
			float currentLength = (float)Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2));
			if(faceLengths != null)
				currentLength = faceLengths[faceLengths.length - idx - 1];
			float ratioPosition = currentLengthPosition / totalLength;
			float ratioLength = (currentLengthPosition - currentLength) / totalLength;

			float texU1 = ((ratioLength * sideTextureWidth + u) / textureWidth);
			float texU2 = ((ratioPosition * sideTextureWidth + u) / textureWidth);
			float texV1 = (((float)v + (float)shapeTextureHeight) / textureHeight);
			float texV2 = (((float)v + (float)shapeTextureHeight + sideTextureHeight) / textureHeight);

			PositionTextureVertex[] polySide = new PositionTextureVertex[4];

			polySide[0] = new PositionTextureVertex(verts[idx], texU2, texV1);
			polySide[1] = new PositionTextureVertex(verts[coords.size() + idx], texU2, texV2);
			polySide[2] = new PositionTextureVertex(verts[coords.size() + ((idx + 1) % coords.size())], texU1, texV2);
			polySide[3] = new PositionTextureVertex(verts[(idx + 1) % coords.size()], texU1, texV1);

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

	public ArrayList<Coord2D> coords;
}
