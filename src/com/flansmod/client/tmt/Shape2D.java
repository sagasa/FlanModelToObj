package com.flansmod.client.tmt;

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
	
	public ArrayList<Coord2D> coords;
}
