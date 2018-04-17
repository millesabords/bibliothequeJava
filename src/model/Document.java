package model;


public abstract class Document implements Cloneable{
	
	private int numEnreg;
	private String titre;

	private static int next_num_enregistrement = 1;

	/**
	 * @param titre
	 * constructeur
	 */
	public Document(String titre)
	{
		
		this.setTitre(titre);
		this.numEnreg = Document.next_num_enregistrement;
		Document.next_num_enregistrement++;
	}
	
	/**
	 * @return numero d'enregistrment du document
	 */
	public int getNumEnreg() {
		return numEnreg;
	}

	/**
	 * @param numEnreg
	 */
	public void setNumEnreg(int numEnreg) {
		this.numEnreg = numEnreg;
	}

	/**
	 * @return titre du document
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @param titre
	 */
	public void setTitre(String titre) {
		if (titre == "")
		{
			System.err.println("Erreur titre document");
			this.titre = "Document sans nom";
		}
		else
			this.titre = titre;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toRet;
		
		toRet = "Ce livre s'appelle " + this.titre + ". Il a pour numéro d'enregistrement " + this.numEnreg + "\n";
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
	public abstract String toCsv();

}
