package model;

import controller.parseCSV;

public class Roman extends Livre {

	public static final int GONCOURT = 0;
	public static final int MEDICIS = 1;
	private int prixLitteraire;
	
	/**
	 * @param titre
	 * @param auteur
	 * @param nb_pages
	 * @param prixLitt
	 */
	public Roman(String titre, String auteur, int nb_pages, int prixLitt) {
		super(titre, auteur, nb_pages);
		this.prixLitteraire = prixLitt;
	}

	/**
	 * @param prixLiteraireLiteral
	 * @return int
	 */
	public static int PrixStrToInt(String prixLiteraireLiteral) {
		if(prixLiteraireLiteral.equals("Roman.GONCOURT")) {
			return GONCOURT;
		}
		if(prixLiteraireLiteral.equals("Roman.MEDICIS")) {
			return MEDICIS;
		}
		return GONCOURT;
	}

	/**
	 * @param prixLiteraireIntegral
	 * @return String
	 */
	public static String PrixIntToStr(int prixLiteraireIntegral) {
		switch(prixLiteraireIntegral) {
		case 0:		return new String("Roman.GONCOURT");
		case 1:		return new String("Roman.MEDICIS");
		default:	return new String("Roman.GONCOURT");
		}
	}
	
	/**
	 * @return prixLitteraire
	 */
	public int getPrixLitteraire() {
		return prixLitteraire;
	}

	/**
	 * @param prixLitteraire
	 */
	public void setPrixLitteraire(int prixLitteraire) {
		this.prixLitteraire = prixLitteraire;
	}

	/*
	 * @see model.Livre#toString()
	 */
	public String toString()
	{
		String toRet;
		
		toRet = super.toString();
		toRet += "Ce roman a eu " + this.prixLitteraire + " prix litteraire(s)\n";
		return toRet;
	}

    /* part of deep copy cloning
     * @see java.lang.Object#clone()
     */
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
	/**
	 * @return string
	 */
	public String toCsv() {
		String res = "";
		res += this.getTitre() + parseCSV.csvDelimiter + this.getAuteur() + parseCSV.csvDelimiter +
			String.valueOf(this.getNbPages()) + parseCSV.csvDelimiter + parseCSV.csvDelimiter + parseCSV.csvDelimiter +
			parseCSV.csvDelimiter + PrixIntToStr(prixLitteraire) + parseCSV.csvDelimiter + "Roman";
		return res;
	}
	
}
