package com.flansmod.client.tmt;

import java.util.ArrayList;

public class Shape2D {

	public Shape2D(Coord2D[] coordArray) {
		coords = coordArray;
	}

	public Shape2D(ArrayList<Coord2D> coordinates) {
		coords = coordinates.toArray(new Coord2D[coordinates.size()]);
	}
	public Coord2D[] coords;
}
