package model;

import controller.parseCSV;

public class Manuel extends Livre{

	private int niveau;
	
	/**
	 * @param titre
	 * @param auteur
	 * @param nb_pages
	 * @param niveau
	 */
	public Manuel(String titre, String auteur, int nb_pages, int niveau) {
		super(titre, auteur, nb_pages);
		this.niveau = niveau;
	}

	/**
	 * @return niveau
	 */
	public int getNiveau() {
		return niveau;
	}

	/**
	 * @param niveau
	 */
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	/*
	 * @see model.Livre#toString()
	 */
	public String toString() {
		String toRet;
		
		toRet = super.toString();
		toRet += "Ce manuel est de niveau " + this.niveau + "\n";
		return toRet; 
	}
	
	/**
	 * @return string
	 */
	public String toCsv() {
		String res = "";
		res += this.getTitre() + parseCSV.csvDelimiter + this.getAuteur() + parseCSV.csvDelimiter +
				String.valueOf(this.getNbPages()) + parseCSV.csvDelimiter + parseCSV.csvDelimiter + parseCSV.csvDelimiter +
				String.valueOf(this.getNiveau()) + parseCSV.csvDelimiter + parseCSV.csvDelimiter + "Manuel";
		return res;
	}
	
}
