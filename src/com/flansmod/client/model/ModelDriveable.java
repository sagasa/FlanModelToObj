package com.flansmod.client.model;

import java.util.HashMap;

import net.minecraft.client.model.ModelBase;

import com.flansmod.client.tmt.ModelRendererTurbo;

public class ModelDriveable extends ModelBase
{
	public static final float pi = 3.14159265F;
	public static final float tau = 2 * pi;

	public HashMap<String, ModelRendererTurbo[][]> gunModels = new HashMap<String, ModelRendererTurbo[][]>();
	public ModelRendererTurbo bodyModel[] = new ModelRendererTurbo[0];
	public ModelRendererTurbo bodyDoorOpenModel[] = new ModelRendererTurbo[0];
	public ModelRendererTurbo bodyDoorCloseModel[] = new ModelRendererTurbo[0];

	/** Set to true to use the old rotation order (ZYX) rather than (YZX) */
	public boolean oldRotateOrder = false;


	public void registerGunModel(String name, ModelRendererTurbo[][] gunModel)
	{
		gunModels.put(name, gunModel);
	}

	public void flipAll()
	{
		flip(bodyModel);
		flip(bodyDoorOpenModel);
		flip(bodyDoorCloseModel);
		for(ModelRendererTurbo[][] modsOfMods : gunModels.values())
		{
			for(ModelRendererTurbo[] mods : modsOfMods)
			{
				flip(mods);
			}
		}
	}

	protected void translate(ModelRendererTurbo[] model, float x, float y, float z)
	{
		for(ModelRendererTurbo mod : model)
		{
			mod.rotationPointX += x;
			mod.rotationPointY += y;
			mod.rotationPointZ += z;
		}
	}

	public void translateAll(float x, float y, float z)
	{
		translate(bodyModel, x, y, z);
		translate(bodyDoorOpenModel, x, y, z);
		translate(bodyDoorCloseModel, x, y, z);
		for(ModelRendererTurbo[][] modsOfMods : gunModels.values())
		{
			for(ModelRendererTurbo[] mods : modsOfMods)
			{
				translate(mods, x, y, z);
			}
		}
	}

	public void translateAll(int x, int y, int z)
	{
		translateAll((float)x, (float)y, (float)z);
	}
}
