package com.flansmod.client.tmt;

import java.util.ArrayList;
import net.minecraft.client.model.ModelBase;
import types.model.Polygon;
import types.model.VertexUV;

/**
 * An extension to the ModelRenderer class. It basically is a copy to
 * ModelRenderer, however, it contains various new methods to make your models.
 * <br />
 * <br />
 * Since the ModelRendererTurbo class gets loaded during startup, the models
 * made can be very complex. This is why I can afford to add, for example,
 * Wavefont OBJ support or have the addSprite method, methods that add a lot of
 * vertices and polygons.
 *
 * @author GaryCXJk
 *
 */
public class ModelRendererTurbo {
	/**
	 * Creates a new ModelRenderTurbo object. It requires the coordinates of the
	 * position of the texture, but also allows you to specify the width and height
	 * of the texture, allowing you to use bigger textures instead.
	 */
	public ModelRendererTurbo(ModelBase modelbase, int offsetX, int offsetY, int textureX, int textureY) {
		this.textureOffsetX = offsetX;
		this.textureOffsetY = offsetY;
		this.textureX = textureX;
		this.textureY = textureY;
	}


	public void addShape3D(float x, float y, float z, Coord2D[] coordinates, float depth, int shapeTextureWidth,
			int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction) {
		addShape3D(x, y, z, coordinates, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth,
				sideTextureHeight, direction, null);
	}

	public void addShape3D(float x, float y, float z, Coord2D[] coordinates, float depth, int shapeTextureWidth,
			int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction, float[] faceLengths) {
		addShape3D(x, y, z, new Shape2D(coordinates), depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth,
				sideTextureHeight, direction, faceLengths);
	}

	public void addShape3D(float x, float y, float z, ArrayList<Coord2D> coordinates, float depth,
			int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction) {
		addShape3D(x, y, z, coordinates, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth,
				sideTextureHeight, direction, null);
	}

	public void addShape3D(float x, float y, float z, ArrayList<Coord2D> coordinates, float depth,
			int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction,
			float[] faceLengths) {
		addShape3D(x, y, z, new Shape2D(coordinates), depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth,
				sideTextureHeight, direction, faceLengths);
	}

	public void addShape3D(float x, float y, float z, Shape2D shape, float depth, int shapeTextureWidth,
			int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction) {
		addShape3D(x, y, z, shape, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight,
				direction, null);
	}

	public void addShape3D(float x, float y, float z, Shape2D shape, float depth, int shapeTextureWidth,
			int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction, float[] faceLengths) {
		float rotX = 0;
		float rotY = 0;
		float rotZ = 0;
		switch (direction) {
		case MR_LEFT:
			rotY = pi / 2;
			break;
		case MR_RIGHT:
			rotY = -pi / 2;
			break;
		case MR_TOP:
			rotX = pi / 2;
			break;
		case MR_BOTTOM:
			rotX = -pi / 2;
			break;
		case MR_FRONT:
			rotY = pi;
			break;
		case MR_BACK:
			break;
		}
		addShape3D(x, y, z, shape, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight,
				rotX, rotY, rotZ, faceLengths);
	}

	public void addShape3D(float x, float y, float z, Shape2D shape, float depth, int shapeTextureWidth,
			int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, float rotX, float rotY, float rotZ) {
		addShape3D(x, y, z, shape, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight,
				rotX, rotY, rotZ, null);
	}

	public void addShape3D(float x, float y, float z, Shape2D shape, float depth, int shapeTextureWidth,
			int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, float rotX, float rotY, float rotZ,
			float[] faceLengths) {
		Coord2D[] coords = shape.coords;

		if (faceLengths != null && faceLengths.length < coords.length)
			faceLengths = null;

		Coord2D exVec = new Coord2D(0, 0);
		exVec.zCoord = -depth;
		exVec = exVec.rotate(rotX, rotY, rotZ);

		VertexUV[] vertsTop = new VertexUV[coords.length];
		VertexUV[] vertsBottom = new VertexUV[coords.length];
		VertexUV[][] verts = new VertexUV[2][coords.length];
		float totalLength = 0;

		for (int i = 0; i < coords.length; i++) {
			/** 回転適応済み */
			Coord2D curCoord = coords[i].rotate(rotX, rotY, rotZ);
			Coord2D nextCoord = coords[(i + 1) % coords.length];

			// 多角形部分用UV
			float u0 = (textureOffsetX + curCoord.uCoord) / textureX;
			float u1 = (textureOffsetX + curCoord.uCoord + shapeTextureWidth) / textureX;
			float v = 1 - ((textureOffsetY + curCoord.vCoord) / textureY);

			VertexUV top = new VertexUV(curCoord).translate(x, y, z).setUV(u0, v);
			VertexUV bottom = new VertexUV(curCoord).translate(x, y, z)
					.translate((float) exVec.xCoord, (float) exVec.yCoord, (float) exVec.zCoord).setUV(u1, v);
			vertsTop[i] = top;
			vertsBottom[coords.length - i - 1] = bottom;
			verts[0][i] = top;
			verts[1][i] = bottom;

			if (faceLengths != null)
				totalLength += faceLengths[i];
			else
				totalLength += Math.sqrt(Math.pow(curCoord.xCoord - nextCoord.xCoord, 2)
						+ Math.pow(curCoord.yCoord - nextCoord.yCoord, 2));

		}

		float currentLengthPosition = totalLength;

		for (int i = 0; i < coords.length; i++) {
			Coord2D curCoord = coords[i];
			Coord2D nextCoord = coords[(i + 1) % coords.length];
			VertexUV ver0 = verts[0][i];
			VertexUV ver1 = verts[1][i];
			VertexUV ver2 = verts[1][(i + 1) % coords.length];
			VertexUV ver3 = verts[0][(i + 1) % coords.length];

			float currentLength = (float) Math.sqrt(
					Math.pow(curCoord.xCoord - nextCoord.xCoord, 2) + Math.pow(curCoord.yCoord - nextCoord.yCoord, 2));
			if (faceLengths != null)
				currentLength = faceLengths[faceLengths.length - i - 1];
			float ratioPosition = currentLengthPosition / totalLength;
			float ratioLength = (currentLengthPosition - currentLength) / totalLength;

			float u0 = (ratioLength * sideTextureWidth + textureOffsetX) / textureX;
			float u1 = (ratioPosition * sideTextureWidth + textureOffsetX) / textureX;
			float v0 = 1-((textureOffsetY + shapeTextureHeight) / textureY);
			float v1 = 1-((textureOffsetY + shapeTextureHeight + sideTextureHeight) / textureY);

			Poly.add(new Polygon(
					new VertexUV[] { ver0.setUV(u1, v0), ver1.setUV(u1, v1), ver2.setUV(u0, v1), ver3.setUV(u0, v0) }));
		}
		Poly.add(new Polygon(vertsTop));
		Poly.add(new Polygon(vertsBottom));
	}

	public void addFlexBox(float x, float y, float z, int w, int h, int d, float scale, float bScale1, float bScale2,
			float bScale3, float bScale4, int dir) {
		float f4 = x + w;
		float f5 = y + h;
		float f6 = z + d;
		x -= scale;
		y -= scale;
		z -= scale;
		f4 += scale;
		f5 += scale;
		f6 += scale;

		int m = 1;
		/*
		 * int m = (mirror ? -1 : 1); if(mirror) { float f7 = f4; f4 = x; x = f7; } //
		 */

		float[] v = { x, y, z };
		float[] v1 = { f4, y, z };
		float[] v2 = { f4, f5, z };
		float[] v3 = { x, f5, z };
		float[] v4 = { x, y, f6 };
		float[] v5 = { f4, y, f6 };
		float[] v6 = { f4, f5, f6 };
		float[] v7 = { x, f5, f6 };

		switch (dir) {
		case MR_RIGHT:
			v[1] -= bScale1;
			v[2] -= bScale3;
			v3[1] += bScale2;
			v3[2] -= bScale3;
			v4[1] -= bScale1;
			v4[2] += bScale4;
			v7[1] += bScale2;
			v7[2] += bScale4;
			break;
		case MR_LEFT:
			v1[1] -= bScale1;
			v1[2] -= bScale3;
			v2[1] += bScale2;
			v2[2] -= bScale3;
			v5[1] -= bScale1;
			v5[2] += bScale4;
			v6[1] += bScale2;
			v6[2] += bScale4;
			break;
		case MR_FRONT:
			v[0] -= m * bScale4;
			v[1] -= bScale1;
			v1[0] += m * bScale3;
			v1[1] -= bScale1;
			v2[0] += m * bScale3;
			v2[1] += bScale2;
			v3[0] -= m * bScale4;
			v3[1] += bScale2;
			break;
		case MR_BACK:
			v4[0] -= m * bScale4;
			v4[1] -= bScale1;
			v5[0] += m * bScale3;
			v5[1] -= bScale1;
			v6[0] += m * bScale3;
			v6[1] += bScale2;
			v7[0] -= m * bScale4;
			v7[1] += bScale2;
			break;
		case MR_TOP:
			v[0] -= m * bScale1;
			v[2] -= bScale3;
			v1[0] += m * bScale2;
			v1[2] -= bScale3;
			v4[0] -= m * bScale1;
			v4[2] += bScale4;
			v5[0] += m * bScale2;
			v5[2] += bScale4;
			break;
		case MR_BOTTOM:
			v2[0] += m * bScale2;
			v2[2] -= bScale3;
			v3[0] -= m * bScale1;
			v3[2] -= bScale3;
			v6[0] += m * bScale2;
			v6[2] += bScale4;
			v7[0] -= m * bScale1;
			v7[2] += bScale4;
			break;
		}
		addRectShape(v, v1, v2, v3, v4, v5, v6, v7, w, h, d);
	}

	public void addFlexTrapezoid(float x, float y, float z, int w, int h, int d, float scale, float bScale1,
			float bScale2, float bScale3, float bScale4, float fScale1, float fScale2, int dir) {
		float f4 = x + w;
		float f5 = y + h;
		float f6 = z + d;
		x -= scale;
		y -= scale;
		z -= scale;
		f4 += scale;
		f5 += scale;
		f6 += scale;

		int m = (mirror ? -1 : 1);
		if (mirror) {
			float f7 = f4;
			f4 = x;
			x = f7;
		}

		float[] v = { x, y, z };
		float[] v1 = { f4, y, z };
		float[] v2 = { f4, f5, z };
		float[] v3 = { x, f5, z };
		float[] v4 = { x, y, f6 };
		float[] v5 = { f4, y, f6 };
		float[] v6 = { f4, f5, f6 };
		float[] v7 = { x, f5, f6 };

		switch (dir) {
		case MR_RIGHT:
			v[2] -= fScale1;
			v1[2] -= fScale1;
			v4[2] += fScale2;
			v5[2] += fScale2;

			v[1] -= bScale1;
			v[2] -= bScale3;
			v3[1] += bScale2;
			v3[2] -= bScale3;
			v4[1] -= bScale1;
			v4[2] += bScale4;
			v7[1] += bScale2;
			v7[2] += bScale4;
			break;
		case MR_LEFT:
			v[2] -= fScale1;
			v1[2] -= fScale1;
			v4[2] += fScale2;
			v5[2] += fScale2;

			v1[1] -= bScale1;
			v1[2] -= bScale3;
			v2[1] += bScale2;
			v2[2] -= bScale3;
			v5[1] -= bScale1;
			v5[2] += bScale4;
			v6[1] += bScale2;
			v6[2] += bScale4;
			break;
		case MR_FRONT:
			v1[1] -= fScale1;
			v5[1] -= fScale1;
			v2[1] += fScale2;
			v6[1] += fScale2;

			v[0] -= m * bScale4;
			v[1] -= bScale1;
			v1[0] += m * bScale3;
			v1[1] -= bScale1;
			v2[0] += m * bScale3;
			v2[1] += bScale2;
			v3[0] -= m * bScale4;
			v3[1] += bScale2;
			break;
		case MR_BACK:
			v1[1] -= fScale1;
			v5[1] -= fScale1;
			v2[1] += fScale2;
			v6[1] += fScale2;

			v4[0] -= m * bScale4;
			v4[1] -= bScale1;
			v5[0] += m * bScale3;
			v5[1] -= bScale1;
			v6[0] += m * bScale3;
			v6[1] += bScale2;
			v7[0] -= m * bScale4;
			v7[1] += bScale2;
			break;
		case MR_TOP:
			v1[2] -= fScale1;
			v2[2] -= fScale1;
			v5[2] += fScale2;
			v6[2] += fScale2;

			v[0] -= m * bScale1;
			v[2] -= bScale3;
			v1[0] += m * bScale2;
			v1[2] -= bScale3;
			v4[0] -= m * bScale1;
			v4[2] += bScale4;
			v5[0] += m * bScale2;
			v5[2] += bScale4;
			break;
		case MR_BOTTOM:
			v1[2] -= fScale1;
			v2[2] -= fScale1;
			v5[2] += fScale2;
			v6[2] += fScale2;

			v2[0] += m * bScale2;
			v2[2] -= bScale3;
			v3[0] -= m * bScale1;
			v3[2] -= bScale3;
			v6[0] += m * bScale2;
			v6[2] += bScale4;
			v7[0] -= m * bScale1;
			v7[2] += bScale4;
			break;
		}

		addRectShape(v, v1, v2, v3, v4, v5, v6, v7, w, h, d);
	}

	public void addTrapezoid(float x, float y, float z, int w, int h, int d, float scale, float bottomScale, int dir) {
		float f4 = x + w;
		float f5 = y + h;
		float f6 = z + d;
		x -= scale;
		y -= scale;
		z -= scale;
		f4 += scale;
		f5 += scale;
		f6 += scale;

		int m = (mirror ? -1 : 1);
		if (mirror) {
			float f7 = f4;
			f4 = x;
			x = f7;
		}

		float[] v = { x, y, z };
		float[] v1 = { f4, y, z };
		float[] v2 = { f4, f5, z };
		float[] v3 = { x, f5, z };
		float[] v4 = { x, y, f6 };
		float[] v5 = { f4, y, f6 };
		float[] v6 = { f4, f5, f6 };
		float[] v7 = { x, f5, f6 };

		switch (dir) {
		case MR_RIGHT:
			v[1] -= bottomScale;
			v[2] -= bottomScale;
			v3[1] += bottomScale;
			v3[2] -= bottomScale;
			v4[1] -= bottomScale;
			v4[2] += bottomScale;
			v7[1] += bottomScale;
			v7[2] += bottomScale;
			break;
		case MR_LEFT:
			v1[1] -= bottomScale;
			v1[2] -= bottomScale;
			v2[1] += bottomScale;
			v2[2] -= bottomScale;
			v5[1] -= bottomScale;
			v5[2] += bottomScale;
			v6[1] += bottomScale;
			v6[2] += bottomScale;
			break;
		case MR_FRONT:
			v[0] -= m * bottomScale;
			v[1] -= bottomScale;
			v1[0] += m * bottomScale;
			v1[1] -= bottomScale;
			v2[0] += m * bottomScale;
			v2[1] += bottomScale;
			v3[0] -= m * bottomScale;
			v3[1] += bottomScale;
			break;
		case MR_BACK:
			v4[0] -= m * bottomScale;
			v4[1] -= bottomScale;
			v5[0] += m * bottomScale;
			v5[1] -= bottomScale;
			v6[0] += m * bottomScale;
			v6[1] += bottomScale;
			v7[0] -= m * bottomScale;
			v7[1] += bottomScale;
			break;
		case MR_TOP:
			v[0] -= m * bottomScale;
			v[2] -= bottomScale;
			v1[0] += m * bottomScale;
			v1[2] -= bottomScale;
			v4[0] -= m * bottomScale;
			v4[2] += bottomScale;
			v5[0] += m * bottomScale;
			v5[2] += bottomScale;
			break;
		case MR_BOTTOM:
			v2[0] += m * bottomScale;
			v2[2] -= bottomScale;
			v3[0] -= m * bottomScale;
			v3[2] -= bottomScale;
			v6[0] += m * bottomScale;
			v6[2] += bottomScale;
			v7[0] -= m * bottomScale;
			v7[2] += bottomScale;
			break;
		}
		addRectShape(v, v1, v2, v3, v4, v5, v6, v7, w, h, d);
	}

	public ModelRendererTurbo addBox(float x, float y, float z, int w, int h, int d) {
		func_78790_a(x, y, z, w, h, d, 0.0F);
		return this;
	}

	public void func_78790_a(float x, float y, float z, int w, int h, int d, float expansion) {
		float x1 = x + w;
		float y1 = y + h;
		float z1 = z + d;

		x -= expansion;
		y -= expansion;
		z -= expansion;
		x1 += expansion;
		y1 += expansion;
		z1 += expansion;
		/*
		 * if(mirror) { float xTemp = x1; x1 = x; x = xTemp; } //
		 */
		float[] v = { x, y, z };
		float[] v1 = { x1, y, z };
		float[] v2 = { x1, y1, z };
		float[] v3 = { x, y1, z };
		float[] v4 = { x, y, z1 };
		float[] v5 = { x1, y, z1 };
		float[] v6 = { x1, y1, z1 };
		float[] v7 = { x, y1, z1 };
		addRectShape(v, v1, v2, v3, v4, v5, v6, v7, w, h, d);
	}

	public void addShapeBox(float x, float y, float z, int w, int h, int d, float scale, float x0, float y0, float z0,
			float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4,
			float y4, float z4, float x5, float y5, float z5, float x6, float y6, float z6, float x7, float y7,
			float z7) {

		float f4 = x + w;
		float f5 = y + h;
		float f6 = z + d;
		x -= scale;
		y -= scale;
		z -= scale;
		f4 += scale;
		f5 += scale;
		f6 += scale;

		float[] v0 = { x - x0, y - y0, z - z0 };
		float[] v1 = { f4 + x1, y - y1, z - z1 };
		float[] v2 = { f4 + x5, f5 + y5, z - z5 };
		float[] v3 = { x - x4, f5 + y4, z - z4 };
		float[] v4 = { x - x3, y - y3, f6 + z3 };
		float[] v5 = { f4 + x2, y - y2, f6 + z2 };
		float[] v6 = { f4 + x6, f5 + y6, f6 + z6 };
		float[] v7 = { x - x7, f5 + y7, f6 + z7 };

		addRectShape(v0, v1, v2, v3, v4, v5, v6, v7, w, h, d);
	}

	public void addRectShape(float[] v0, float[] v1, float[] v2, float[] v3, float[] v4, float[] v5, float[] v6,
			float[] v7, int w, int h, int d) {
		Poly.add(toPoly(v0, v3, v2, v1, d, d, w, h));
		Poly.add(toPoly(v4, v7, v3, v0, 0, d, d, h));
		Poly.add(toPoly(v5, v6, v7, v4, d + w + d, d, w, h));
		Poly.add(toPoly(v1, v2, v6, v5, d + w, d, d, h));
		Poly.add(toPoly(v4, v0, v1, v5, d, 0, w, d));
		Poly.add(toPoly(v3, v7, v6, v2, d + w, 0, w, d));
	}

	/**
	 * 左上から反時計回り
	 *
	 * @param x0
	 *            左上のテクスチャX
	 * @param y0
	 *            左上のテクスチャY
	 * @param x1
	 *            幅
	 * @param y1
	 *            高さ
	 */
	private Polygon toPoly(float[] pos0, float[] pos1, float[] pos2, float[] pos3, int x0, int y0, int x1, int y1) {
		return new Polygon(new VertexUV[] {
				new VertexUV(pos0, (textureOffsetX + x0) / textureX, 1 - (textureOffsetY + y0) / textureY),
				new VertexUV(pos1, (textureOffsetX + x0) / textureX, 1 - (textureOffsetY + y0 + y1) / textureY),
				new VertexUV(pos2, (textureOffsetX + x0 + x1) / textureX, 1 - (textureOffsetY + y0 + y1) / textureY),
				new VertexUV(pos3, (textureOffsetX + x0 + x1) / textureX, 1 - (textureOffsetY + y0) / textureY) });
	}

	/**
	 * Sets the position of the shape, relative to the model's origins. Note that
	 * changing the offsets will not change the pivot of the model.
	 *
	 * @param x
	 *            the x-position of the shape
	 * @param y
	 *            the y-position of the shape
	 * @param z
	 *            the z-position of the shape
	 */
	public void func_78793_a(float x, float y, float z) {
		rotationPointX = x;
		rotationPointY = y;
		rotationPointZ = z;
	}

	/**
	 * Sets the default texture. When left as an empty string, it will use the
	 * texture that has been set previously. Note that this will also move on to
	 * other rendered models of the same entity.
	 *
	 * @param s
	 *            the filename
	 */
	public void setDefaultTexture(String s) {
		defaultTexture = s;
	}

	public void doMirror(boolean x, boolean y, boolean z) {
		for (Polygon face : Poly) {
			VertexUV[] verts = face.Vertex;
			for (int j = 0; j < verts.length; j++) {
				verts[j] = verts[j].multiplication((x ? -1 : 1), (y ? -1 : 1), (z ? -1 : 1));
			}
			if (x ^ y ^ z) {
			}
			// face.flipFace();
		}
	}

	public void setRotationPoint(float x, float y, float z) {
		rotationPointX = x;
		rotationPointY = y;
		rotationPointZ = z;
	}

	public void compile() {
		for (Polygon poly : Poly) {
			poly.translate(rotationPointX, rotationPointY, rotationPointZ);
		}
		for (Polygon poly : Poly) {
			for (int i = 0; i < poly.Vertex.length; i++) {
				poly.Vertex[i] = poly.Vertex[i].rotate(rotationPointX, rotationPointY, rotationPointZ, field_78795_f,
						field_78796_g, field_78808_h);
			}
		}
	}

	public float field_78795_f;// rotateX
	public float field_78796_g;// rotateY
	public float field_78808_h;// rotateZ

	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;

	/** UV付きのポリゴン */
	public ArrayList<Polygon> Poly = new ArrayList<>();

	public boolean mirror = false;

	private float textureX;
	private float textureY;

	private int textureOffsetX;
	private int textureOffsetY;

	private String defaultTexture;

	public static final int MR_FRONT = 0;
	public static final int MR_BACK = 1;
	public static final int MR_LEFT = 2;
	public static final int MR_RIGHT = 3;
	public static final int MR_TOP = 4;
	public static final int MR_BOTTOM = 5;

	private static final float pi = (float) Math.PI;
}