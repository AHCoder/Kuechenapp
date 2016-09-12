package model;

import java.io.Serializable;

import view.Einheit;

public class Artikel implements Serializable{
	private static final long serialVersionUID = -7196319576894252892L;

	private String name;

	private Einheit masseinheit;

	private double menge;

	/**
	 * @param name
	 * @param masseinheit
	 * @param menge
	 */
	public Artikel(String name, Einheit masseinheit, double menge) {
		this.name = name;
		this.masseinheit = masseinheit;
		this.menge = menge;
	}
	
	
	/***
	 * Diese Methode erstellt die xml-Formatierung für den Artikel. Bsp, <zutat><menge> ......</zutat>.
	 * 
	 * @return man kriegt ein String mit der xml-Formatierung für den Artikel zurück.
	 */
	public String toXML() {
		// TODO ilker

		String artikelString = "<zutat>\n" + "<menge>" + getMenge() + "</menge>\n" +
		"<masseinheit>" + getMasseinheit() + "</masseinheit>\n" +
		"<name>" + getName() + "</name>\n" + "</zutat>\n";
		return artikelString;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the masseinheit
	 */
	public Einheit getMasseinheit() {
		return masseinheit;
	}

	/**
	 * @param masseinheit the masseinheit to set
	 */
	public void setMasseinheit(Einheit masseinheit) {
		this.masseinheit = masseinheit;
	}

	/**
	 * @return the menge
	 */
	public double getMenge() {
		return menge;
	}

	/**
	 * @param menge the menge to set
	 */
	public void setMenge(double menge) {
		this.menge = menge;
	}

}
