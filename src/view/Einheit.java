package view;

import java.io.Serializable;

public enum Einheit implements Serializable{
	KILOGRAMM,
	GRAMM, 
	LITER, 
	MILLILITER, 
	STUECK, 
	FLASCHE, 
	PAECKCHEN,
	PRISE;
	
	public static double toKg(double wert){
		return wert / 1000;
	}
	
	public static double toGr(double wert){
		return wert * 1000;
	}
	
	public static double toLi(double wert){
		return wert / 1000;
	}
	
	public static double toMl(double wert){
		return wert * 1000;
	}
}
