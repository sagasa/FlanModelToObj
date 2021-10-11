package com.flansmod.client.tmt;

import java.util.ArrayList;
import java.util.List;

import com.flansmod.common.vector.Vector3f;
import types.model.Polygon;
import types.model.VertexUV;

public class TexturedPolygon
{
    public TexturedPolygon(PositionTextureVertex[] apositionTexturevertex)
    {
        this.invertNormal = false;
        this.vertexPositions = apositionTexturevertex;
        this.nVertices = apositionTexturevertex.length;
        this.iNormals = new ArrayList<Vector3f>();
        this.normals = new float[0];
    }

    public TexturedPolygon(PositionTextureVertex[] apositionTexturevertex, int par2, int par3, int par4, int par5, float par6, float par7)
    {
        this(apositionTexturevertex);
        float var8 = 0.0F / par6;
        float var9 = 0.0F / par7;
        apositionTexturevertex[0] = apositionTexturevertex[0].setTexturePosition(par4 / par6 - var8, par3 / par7 + var9);
        apositionTexturevertex[1] = apositionTexturevertex[1].setTexturePosition(par2 / par6 + var8, par3 / par7 + var9);
        apositionTexturevertex[2] = apositionTexturevertex[2].setTexturePosition(par2 / par6 + var8, par5 / par7 - var9);
        apositionTexturevertex[3] = apositionTexturevertex[3].setTexturePosition(par4 / par6 - var8, par5 / par7 - var9);
    }

    public void setInvertNormal(boolean isSet)
    {
        invertNormal = isSet;
    }

    public void setNormals(float x, float y, float z)
    {
        normals = new float[] {x, y, z};
    }

    public void flipFace()
    {
        PositionTextureVertex[] var1 = new PositionTextureVertex[this.vertexPositions.length];

        for (int var2 = 0; var2 < this.vertexPositions.length; ++var2)
        {
            var1[var2] = this.vertexPositions[this.vertexPositions.length - var2 - 1];
        }

        this.vertexPositions = var1;
    }

    public void setNormals(ArrayList<Vector3f> vec)
    {
        iNormals = vec;
    }

    public Polygon makePoly()
    {
        Polygon poly = new Polygon();
        poly.Vertex = new VertexUV[nVertices];
        for(int i = 0; i < nVertices; i++)
        {
            PositionTextureVertex vertex = vertexPositions[i];
            poly.Vertex[i] = new VertexUV(vertex.x,vertex.y,vertex.z,vertex.texturePositionX,vertex.texturePositionY);
        }
        return poly;
    }

    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;
    private float[] normals;
    private ArrayList<Vector3f> iNormals;
}
