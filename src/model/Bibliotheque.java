package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import controller.triParTitre;
import controller.parseCSV;

/**
 * Contient la liste des documetns de la bibliotheque
 *
 */
public class Bibliotheque implements Cloneable{

	public final static String newline = System.getProperty("line.separator");
	
	// Liste des documents de la bibliotheque
	private List<Document> documents;

	/**
	 * Constructeur d'une bibliotheque dont la liste de documents est vide.
	 */
	public Bibliotheque() {
		this.documents = new ArrayList<Document>();
	}
	
	/**
	 * @return
	 * Renvoie la liste des documents de la bibliotheque.
	 */
	public List<Document> getDocuments() {
		return documents;
	}
	
	/**
	 * @param i
	 * @return
	 * Renvoie le i �me document de la liste des documents, s�il existe, 
	 * ou null sinon.
	 */
	public Document getDocument(int i) {
		if(i < 0 || i >= documents.size()) {
			System.err.println("Erreur numéro de page Livre");
			//throw new RuntimeException("getDocument(int) called with erronneous index: " + i);
			return null;
		}
		return documents.get(i);
	}
	
	/**
	 * @param doc
	 * @return
	 * Si doc est non null et n'appartient pas d�j� � la liste des documents,
	 * alors ajoute doc � cette liste et renvoie true ;
	 * sinon renvoie faux.
	 */
	public boolean addDocument(Document doc) {
		if(documents.contains(doc)) {
			System.err.println("Erreur: Bibliotheque.addDocument(): document deja existant");
			return false;
		}
		if(documents == null) {
			System.err.println("Erreur: Bibliotheque.addDocument(): document null");
			return false;
		}
		documents.add(doc);
		return true;
	}
	
	/**
	 * @param doc
	 * @return
	 * Si doc est dans la liste des documents, alors l'y supprime et renvoie true;
	 * sinon renvoie false.
	 */
	public boolean removeDocument(Document doc) {
		Iterator<Document> itr = documents.iterator();
		
		while (itr.hasNext()) {
		    Document iterDoc = itr.next();
		    if (doc == iterDoc) {
		       itr.remove();
		       return true;
		    }
		}
		return false;
	}

	public void eraseAll() {
		documents.clear();
	}
	
	/**
	 * @param titre
	 * @return Livre
	 */
	public Document rechercheParTitre(String titre) {
		Iterator<Document> itr = documents.iterator();
		
		while (itr.hasNext()) {
		    Document iterDoc = itr.next();
			if( iterDoc instanceof Livre) {//if it has an author
				Livre bouquin = (Livre) iterDoc;
				if(bouquin.getTitre().equals(titre))
					return bouquin;
		    }
		}
		return null;
	}
	
	/**
	 * @param titre
	 * @return Boolean
	 */
	public Boolean suppressionParTitre(String titre) {
		Document recherche = rechercheParTitre(titre);
		
		if(recherche == null) {
			System.err.println("Erreur: le document demande ne peut etre supprime car il n'existe pas: " + titre);
			return false;
		}
		else {
			return removeDocument(recherche);
		}
	}
	
	/**
	 * @param prixLiteraire
	 * @return Roman
	 */
	public Roman rechercheRomanParPrixLiteraire(int prixLiteraire) {
		Iterator<Document> itr = documents.iterator();
		
		while (itr.hasNext()) {
		    Document iterDoc = itr.next();
			if( iterDoc instanceof Roman) {
				Roman cestUnBeauRoman = (Roman) iterDoc;
				if(cestUnBeauRoman.getPrixLitteraire() == prixLiteraire)
					return cestUnBeauRoman;//...cestUneBelleHistoire
		    }
		}
		return null;
	}
	
	/**
	 * @param prixLiteraire
	 * @return Boolean
	 */
	public Boolean suppressionParPrixLiteraire(int prixLiteraire) {
		Roman recherche = rechercheRomanParPrixLiteraire(prixLiteraire);
		
		if(recherche == null) {
			System.err.println("Erreur: pas de document ayant recu ce type de prix literaire: " + prixLiteraire);
			return false;
		}
		else {
			return removeDocument(recherche);
		}
	}
	
	/**
	 * @param csvFileName
	 * @return boolean
	 */
	public Boolean addListFromCSV(String csvFileName) {
		try {
			ArrayList<Document> nouvelleListe = parseCSV.ReadDocument(csvFileName);
			documents.addAll(nouvelleListe);
			return true;
		}
		catch(Exception e){
            e.printStackTrace();
            return false;
		}
	}
	
	/**
	 * @param csvFileName
	 * @return boolean
	 */
	public Boolean sauvegardeToCsv(String csvFileName) {
		return parseCSV.WriteDocument(csvFileName, this);
	}
	
    /* necessary for deep copying
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        Bibliotheque clonedBibliotheque = null;
        try {
            clonedBibliotheque = (Bibliotheque)super.clone();
            clonedBibliotheque.deepCloneDocuments(this.documents);
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        
        return clonedBibliotheque;
    }

	/**
	 * @param newList
	 * @throws CloneNotSupportedException
	 */
	public void deepCloneDocuments(List<Document> oldList) throws CloneNotSupportedException {
		documents = new ArrayList<Document>();
		//clonedBibliotheque.eraseAll();
        for(Document doc : oldList) {
         	System.out.print("ah");
          	addDocument((Document) doc.clone());
        }
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String res = "Bibliotheque de " + documents.size() + " documents: " + newline;
		for ( Document doc : this.documents ) {
			//here we can use 'doc' in the string construction because it is implicitely interpreted
			//as being a call to 'toString' method:
			res += "- " + doc + newline;
		}
		
		return res;
	}	
	
	public void triLexicographique() {
       Collections.sort(documents, new triParTitre());
	}
}
