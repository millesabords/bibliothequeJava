package model;

import controller.parseCSV;

public class Livre extends Document implements InterfaceAuteur {

	private String auteur;
	private int nbPages;
	
	
	/**
	 * @param titre
	 * @param auteur
	 * @param nb_pages
	 */
	public Livre(String titre, String auteur, int nb_pages) {
		super(titre);
		this.setAuteur(auteur);
		this.setNbPages(nb_pages);
	}
	
	/* 
	 * @see model.InterfaceAuteur#getAuteur()
	 */
	public String getAuteur()
	{
		return this.auteur;
	}

	/**
	 * @return nbPages
	 */
	public int getNbPages() {
		return nbPages;
	}

	/**
	 * @param nbPages
	 */
	public void setNbPages(int nbPages) {
		if (nbPages < 1)
		{
			System.err.println("Erreur numéro de page Livre");
			this.nbPages = 0;
		}
		else
			this.nbPages = nbPages;
	}

	/**
	 * @param auteur
	 */
	public void setAuteur(String auteur) {
		if (auteur == "")
		{
			System.err.println("Erreur auteur livre");
			this.auteur = "Auteur inconnu";
		}
		else
			this.auteur = auteur;
	}

	/*
	 * @see model.Document#toString()
	 */
	public String toString() {
		String toRet;
		
		toRet = super.toString();
		toRet += "Ce livre a pour auteur " + this.auteur + " et a " + this.nbPages + " pages\n";
		return toRet;
	}

	/**
	 * @return string
	 */
	public String toCsv() {
		String res = "";
		res += this.getTitre() + parseCSV.csvDelimiter + this.getAuteur() + parseCSV.csvDelimiter +
					String.valueOf(this.getNbPages()) + parseCSV.csvDelimiter + parseCSV.csvDelimiter +
					parseCSV.csvDelimiter + parseCSV.csvDelimiter + parseCSV.csvDelimiter + "Livre";
		return res;
	}
	
}
