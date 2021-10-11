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

	
}
