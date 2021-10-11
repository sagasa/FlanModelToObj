package com.flansmod.client.tmt;

public class Shape3D
{
    public Shape3D(PositionTextureVertex[] verts, TexturedPolygon[] poly)
    {
        vertices = verts;
        faces = poly;
    }
    public PositionTextureVertex[] vertices;
    public TexturedPolygon[] faces;
}
