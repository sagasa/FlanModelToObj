package com.flansmod.client.model;

import com.flansmod.client.tmt.ModelRendererTurbo;
import com.flansmod.common.vector.Vector3f;

//Extensible ModelVehicle class for rendering vehicle models
public class ModelVehicle extends ModelDriveable {
    public ModelRendererTurbo turretModel[] = new ModelRendererTurbo[0];            //The turret (for tanks)
    public ModelRendererTurbo barrelModel[] = new ModelRendererTurbo[0];            //The barrel of the main turret
    public ModelRendererTurbo ammoModel[][] = new ModelRendererTurbo[0][0];            //Ammo models for the main turret. ammoModel[i] will render if the vehicle has less than 3 ammo slots or if slot i is full. Checks shell / missile inventory
    public ModelRendererTurbo frontWheelModel[] = new ModelRendererTurbo[0];        //Front and back wheels are for bicycles and motorbikes and whatnot
    public ModelRendererTurbo backWheelModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo leftFrontWheelModel[] = new ModelRendererTurbo[0];    //This set of 4 wheels are for 4 or more wheeled things
    public ModelRendererTurbo rightFrontWheelModel[] = new ModelRendererTurbo[0];    //The front wheels will turn as the player steers, and the back ones will not
    public ModelRendererTurbo leftBackWheelModel[] = new ModelRendererTurbo[0];        //They will all turn as the car drives if the option to do so is set on
    public ModelRendererTurbo rightBackWheelModel[] = new ModelRendererTurbo[0];    //In the vehicle type file
    public ModelRendererTurbo rightTrackModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo leftTrackModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo rightTrackWheelModels[] = new ModelRendererTurbo[0];    //These go with the tracks but rotate
    public ModelRendererTurbo leftTrackWheelModels[] = new ModelRendererTurbo[0];

    public ModelRendererTurbo leftAnimTrackModel[][] = new ModelRendererTurbo[0][0];  //Unlimited frame track animations
    public ModelRendererTurbo rightAnimTrackModel[][] = new ModelRendererTurbo[0][0];

    public ModelRendererTurbo bodyDoorOpenModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo bodyDoorCloseModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo trailerModel[] = new ModelRendererTurbo[0];
    public ModelRendererTurbo steeringWheelModel[] = new ModelRendererTurbo[0];

    public ModelRendererTurbo drillHeadModel[] = new ModelRendererTurbo[0];        //Drill head. Rotates around
    public Vector3f drillHeadOrigin = new Vector3f();                                //this point

    public ModelRendererTurbo animBarrelModel[] = new ModelRendererTurbo[0];
    public Vector3f barrelAttach = new Vector3f();

    public int animFrame = 0;

}
