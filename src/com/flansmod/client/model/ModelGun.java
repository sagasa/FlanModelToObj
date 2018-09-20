package com.flansmod.client.model;


import com.flansmod.client.tmt.ModelRendererTurbo;
import com.flansmod.common.vector.Vector3f;

public class ModelGun extends net.minecraft.client.model.ModelBase
{
	//Shapebox template. For quick copy pasting
	//, 0F, /* 0 */ 0F, 0F, 0F, /* 1 */ 0F, 0F, 0F, /* 2 */ 0F, 0F, 0F, /* 3 */ 0F, 0F, 0F, /* 4 */ 0F, 0F, 0F, /* 5 */ 0F, 0F, 0F, /* 6 */ 0F, 0F, 0F, /* 7 */ 0F, 0F, 0F);

	//These first 6 models are static with no animation
	public ModelRendererTurbo[] gunModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] backpackModel = new ModelRendererTurbo[0]; //For flamethrowers and such like. Rendered on the player's back
	//These models appear when no attachment exists
	public ModelRendererTurbo[] defaultBarrelModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] defaultScopeModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] defaultStockModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] defaultGripModel = new ModelRendererTurbo[0];

	//Animated models follow.
	public ModelRendererTurbo[] ammoModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] revolverBarrelModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] breakActionModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] slideModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] pumpModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] minigunBarrelModel = new ModelRendererTurbo[0];
	public ModelRendererTurbo[] leverActionModel = new ModelRendererTurbo[0];


	public Vector3f minigunBarrelOrigin = new Vector3f();

	//These designate the locations of 3D attachment models on the gun
	public Vector3f barrelAttachPoint = new Vector3f();
	public Vector3f scopeAttachPoint = new Vector3f();
	public Vector3f stockAttachPoint = new Vector3f();
	public Vector3f gripAttachPoint = new Vector3f();

	//Various animation parameters
	public float gunSlideDistance = 1F / 4F;
	public EnumAnimationType animationType = EnumAnimationType.NONE;
	public EnumMeleeAnimation meleeAnimation = EnumMeleeAnimation.DEFAULT;
	public float tiltGunTime = 0.25F, unloadClipTime = 0.25F, loadClipTime = 0.25F, untiltGunTime = 0.25F;
	/** If true, then the scope attachment will move with the top slide */
	public boolean scopeIsOnSlide = false;
	/** If true, then the scope attachment will move with the break action. Can be combined with the above */
	public boolean scopeIsOnBreakAction = false;
	/** For rifles and shotguns. Currently a generic reload animation regardless of how full the internal magazine already is */
	public float numBulletsInReloadAnimation = 1;
	/** For shotgun pump handles and rifle bolts */
	public int pumpDelay = 0, pumpDelayAfterReload = 0, pumpTime = 1;
	/** For shotgun pump handle */
	public float pumpHandleDistance = 4F / 16F;
	/** For end loaded projectiles */
	public float endLoadedAmmoDistance = 1F;
	/** If true, then the grip attachment will move with the shotgun pump */
	public boolean gripIsOnPump = false;
	/** The rotation point for the barrel break */
	public Vector3f barrelBreakPoint = new Vector3f();
	/** The amount the revolver barrel flips out by */
	public float revolverFlipAngle = 15F;
	/** The rotation point for the revolver flip */
	public Vector3f revolverFlipPoint = new Vector3f();
	/** The angle the gun is broken by for break actions */
	public float breakAngle = 45F;
	/** If true, then the gun will perform a spinning reload animation */
	public boolean spinningCocking = false;
	/** The point, in model co-ordinates, about which the gun is spun */
	public Vector3f spinPoint = new Vector3f();

	/** Custom reload Parameters. If Enum.CUSTOM is set, these parameters can build an animation within the gun model classes */
	public float rotateGunVertical = 0F;
	public float rotateGunHorizontal = 0F;
	public float tiltGun = 0F;
	public Vector3f translateGun = new Vector3f(0F, 0F, 0F);
	/* Ammo Model reload parameters */
	public float rotateClipVertical = 0F;
	public float rotateClipHorizontal = 0F;
	public float tiltClip = 0F;
	public Vector3f translateClip = new Vector3f(0F, 0F, 0F);

	/** This offsets the render position for third person */
	public Vector3f thirdPersonOffset = new Vector3f();

	/** This offsets the render position for item frames */
	public Vector3f itemFrameOffset = new Vector3f();

	/**回転を適応*/
	public void compile(){
		flip(gunModel);
		flip(defaultBarrelModel);
		flip(defaultScopeModel);
		flip(defaultStockModel);
		flip(defaultGripModel);
		flip(ammoModel);
		flip(slideModel);
		flip(pumpModel);
		flip(minigunBarrelModel);
		flip(revolverBarrelModel);
		flip(breakActionModel);
	}
	
	protected void compile(ModelRendererTurbo[] model){
		for(ModelRendererTurbo part : model)
		{
			part.compile();
		}
	}
	
	public void flipAll() {
		flip(gunModel);
		flip(defaultBarrelModel);
		flip(defaultScopeModel);
		flip(defaultStockModel);
		flip(defaultGripModel);
		flip(ammoModel);
		flip(slideModel);
		flip(pumpModel);
		flip(minigunBarrelModel);
		flip(revolverBarrelModel);
		flip(breakActionModel);
	}

	protected void flip(ModelRendererTurbo[] model)
	{
		for(ModelRendererTurbo part : model)
		{
			part.doMirror(false, true, true);
			part.setRotationPoint(part.rotationPointX, - part.rotationPointY, - part.rotationPointZ);
		}
	}

	public void translateAll(float x, float y, float z)
	{

	}
}