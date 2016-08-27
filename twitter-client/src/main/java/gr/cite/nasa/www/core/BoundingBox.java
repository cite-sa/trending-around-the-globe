package gr.cite.nasa.www.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class BoundingBox {
	
	@XmlElement(name="southWest")
	private PlaceGeography southWest;
	
	@XmlElement(name="northEast")
	private PlaceGeography northEast;

	public PlaceGeography getSouthWest() {
		return southWest;
	}

	public void setSouthWest( PlaceGeography southWest) {
		this.southWest = southWest;
	}

	public  PlaceGeography getNorthEast() {
		return northEast;
	}

	public void setNorthEast( PlaceGeography northEast) {
		this.northEast = northEast;
	}

}
