package com.flansmod.client.tmt;


import com.flansmod.common.vector.Vector3f;

public class PositionTextureVertex extends Vector3f
{
    public float texturePositionX = 1F;
    public float texturePositionY = 1F;
    public float texturePositionW = 1F;

    public PositionTextureVertex(float par1, float par2, float par3, float par4, float par5)
    {
        this(par1, par2, par3, par4, par5, 1F);
    }

    public PositionTextureVertex(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        this(new Vector3f(par1, par2, par3), par4, par5);
    }

    public PositionTextureVertex setTexturePosition(float par1, float par2)
    {
        return new PositionTextureVertex(this, par1, par2, 1F);
    }

    public PositionTextureVertex setTexturePosition(float par1, float par2, float q)
    {
        return new PositionTextureVertex(this, par1, par2, q);
    }

    public PositionTextureVertex(PositionTextureVertex par1PositionTextureVertex, float par2, float par3)
    {
        this(par1PositionTextureVertex, par2, par3, 1F);
    }

    public PositionTextureVertex(PositionTextureVertex par1PositionTextureVertex, float par2, float par3, float q)
    {
        super(par1PositionTextureVertex.x,par1PositionTextureVertex.y,par1PositionTextureVertex.z);
        texturePositionX = par2;
        texturePositionY =par3;
        this.texturePositionW = q;
    }

    public PositionTextureVertex(Vector3f par1Vec3, float par2, float par3)
    {
        this(par1Vec3, par2, par3, 1F);
    }

    public PositionTextureVertex(Vector3f par1Vec3, float par2, float par3, float par4)
    {
        super(par1Vec3.x,par1Vec3.y,par1Vec3.z);
        texturePositionX = par2;
        texturePositionY =par3;
        this.texturePositionW = par4;
    }
}
