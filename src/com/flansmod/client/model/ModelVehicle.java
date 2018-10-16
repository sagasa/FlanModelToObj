package com.flansmod.client.model;

import com.flansmod.client.tmt.ModelRendererTurbo;
import com.flansmod.common.vector.Vector3f;

//Extensible ModelVehicle class for rendering vehicle models
public class ModelVehicle extends ModelDriveable
{
	public ModelRendererTurbo turretModel[] = new ModelRendererTurbo[0];			//The turret (for tanks)
	public ModelRendererTurbo barrelModel[] = new ModelRendererTurbo[0];			//The barrel of the main turret
	public ModelRendererTurbo ammoModel[][] = new ModelRendererTurbo[0][0];			//Ammo models for the main turret. ammoModel[i] will render if the vehicle has less than 3 ammo slots or if slot i is full. Checks shell / missile inventory
	public ModelRendererTurbo frontWheelModel[] = new ModelRendererTurbo[0];		//Front and back wheels are for bicycles and motorbikes and whatnot
	public ModelRendererTurbo backWheelModel[] = new ModelRendererTurbo[0];
	public ModelRendererTurbo leftFrontWheelModel[] = new ModelRendererTurbo[0];	//This set of 4 wheels are for 4 or more wheeled things
	public ModelRendererTurbo rightFrontWheelModel[] = new ModelRendererTurbo[0];	//The front wheels will turn as the player steers, and the back ones will not
	public ModelRendererTurbo leftBackWheelModel[] = new ModelRendererTurbo[0];		//They will all turn as the car drives if the option to do so is set on
	public ModelRendererTurbo rightBackWheelModel[] = new ModelRendererTurbo[0];	//In the vehicle type file
	public ModelRendererTurbo rightTrackModel[] = new ModelRendererTurbo[0];
	public ModelRendererTurbo leftTrackModel[] = new ModelRendererTurbo[0];
	public ModelRendererTurbo rightTrackWheelModels[] = new ModelRendererTurbo[0];	//These go with the tracks but rotate
	public ModelRendererTurbo leftTrackWheelModels[] = new ModelRendererTurbo[0];

	public ModelRendererTurbo leftAnimTrackModel[][] = new ModelRendererTurbo[0][0];  //Unlimited frame track animations
	public ModelRendererTurbo rightAnimTrackModel[][] = new ModelRendererTurbo[0][0];

	public ModelRendererTurbo bodyDoorOpenModel[] = new ModelRendererTurbo[0];
	public ModelRendererTurbo bodyDoorCloseModel[] = new ModelRendererTurbo[0];
	public ModelRendererTurbo trailerModel[] = new ModelRendererTurbo[0];
	public ModelRendererTurbo steeringWheelModel[] = new ModelRendererTurbo[0];

	public ModelRendererTurbo drillHeadModel[] = new ModelRendererTurbo[0]; 		//Drill head. Rotates around
	public Vector3f drillHeadOrigin = new Vector3f();								//this point

	public int animFrame = 0;


	@Override
	public void flipAll()
	{
		super.flipAll();
		flip(bodyDoorOpenModel);
		flip(bodyDoorCloseModel);
		flip(turretModel);
		flip(barrelModel);
		flip(leftFrontWheelModel);
		flip(rightFrontWheelModel);
		flip(leftBackWheelModel);
		flip(rightBackWheelModel);
		flip(rightTrackModel);
		flip(leftTrackModel);
		flip(rightTrackWheelModels);
		flip(leftTrackWheelModels);
		flip(trailerModel);
		flip(steeringWheelModel);
		flip(frontWheelModel);
		flip(backWheelModel);
		flip(drillHeadModel);
		for(ModelRendererTurbo[] latm : leftAnimTrackModel)
			flip(latm);
		for(ModelRendererTurbo[] ratm : rightAnimTrackModel)
			flip(ratm);
	}


	@Override
	public void translateAll(float x, float y, float z)
	{
		super.translateAll(x, y, z);
		translate(bodyDoorOpenModel, x, y, z);
		translate(bodyDoorCloseModel, x, y, z);
		translate(turretModel, x, y, z);
		translate(barrelModel, x, y, z);
		translate(leftFrontWheelModel, x, y, z);
		translate(rightFrontWheelModel, x, y, z);
		translate(leftBackWheelModel, x, y, z);
		translate(rightBackWheelModel, x, y, z);
		translate(rightTrackModel, x, y, z);
		translate(leftTrackModel, x, y, z);
		translate(rightTrackWheelModels, x, y, z);
		translate(leftTrackWheelModels, x, y, z);
		translate(trailerModel, x, y, z);
		translate(steeringWheelModel, x, y, z);
		translate(frontWheelModel, x, y, z);
		translate(backWheelModel, x, y, z);
		translate(drillHeadModel, x, y, z);
		for(ModelRendererTurbo[] latm : leftAnimTrackModel)
			translate(latm, x, y, z);
		for(ModelRendererTurbo[] ratm : rightAnimTrackModel)
			translate(ratm, x, y, z);
	}
}
