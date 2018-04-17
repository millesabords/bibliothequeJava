package view;
import controller.triParAuteur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import model.Bibliotheque;
import model.Document;
import model.Livre;

/**
 * Utilitaires d'affichage
 */
public class Affichage {

	 /**
	 * @param docs documetns a afficher
	 * cette methode est une mauvaise idee de conception: elle ajoute de la redondance de code:
	 * la fonctionnalite fait doublon avec Bibliotheque.toString () 
	 */
	public void afficherDocuments(Collection<Document> docs) {
		String res = "Affichage.afficherDocuments: " + Bibliotheque.newline;
		for ( Document doc : docs ) {
			//here we can use 'doc' in the string construction because it is implicitely interpreted
			//as being a call to 'toString' method:
			res += "- " + doc + Bibliotheque.newline;
		}
		System.out.println(res);
	 }
	
	 /**
	 * @param docs documents dont on souhaite afficher les auteurs par ordre alphabetique
	 */
	public void afficherAuteurs(Collection<Document> docs) {
		String res = "Affichage.afficherAuteurs: " + Bibliotheque.newline;
		
		ArrayList<Livre> listeLivres = new ArrayList<Livre>();
		for ( Document doc : docs ) {
			if( doc instanceof Livre) {//does the document has an author?
				listeLivres.add((Livre) doc);
			}
		}
		
        Collections.sort(listeLivres, new triParAuteur());
		
		for ( Livre lv : listeLivres ) {
			res += "- " + lv.getAuteur() + Bibliotheque.newline;
		}
		
		System.out.println(res);
	}
	
}
