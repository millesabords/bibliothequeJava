package model;

import controller.parseCSV;

public class Revue extends Document{

	private int mois;
	private int annee;
	
	/**
	 * @param titre
	 * @param mois
	 * @param annee
	 */
	public Revue(String titre, int mois, int annee) {
		super(titre);
		this.setMois(mois);
		this.setAnnee(annee);
		//this.annee = annee;
	}

	/**
	 * @return mois
	 */
	public int getMois() {
		return mois;
	}

	/**
	 * @param mois
	 */
	public void setMois(int mois) {
		if ((mois < 1) || (mois > 12))
		{	
			System.err.println("Erreur Mois Revue");
			this.mois = 1;
		}
		else
			this.mois = mois;
	}

	/**
	 * @return annee
	 */
	public int getAnnee() {
		return annee;
	}

	/**
	 * @param annee
	 */
	public void setAnnee(int annee) {
		if ((annee < 1960) || (annee > 2020))
		{	
			System.err.println("Erreur année Revue");
			this.annee = 2000;
		}
		else
			this.annee = annee;
	}

	/*
	 * @see model.Document#toString()
	 */
	public String toString() {
		String toRet;
		
		toRet = super.toString();
		toRet += "Cette revue date de " + this.mois + "/" + this.annee + "\n";
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
		res += this.getTitre() + parseCSV.csvDelimiter + parseCSV.csvDelimiter + parseCSV.csvDelimiter +
				String.valueOf(this.getMois()) + parseCSV.csvDelimiter + String.valueOf(this.getAnnee()) +
				parseCSV.csvDelimiter + parseCSV.csvDelimiter + parseCSV.csvDelimiter + "Revue";
		return res;
	}
	
}
