package controller;

import model.Bibliotheque;
import model.Document;
import model.Livre;
import model.Manuel;
import model.Revue;
import model.Roman;
import view.Affichage;

/*
 * todo ameliorations (suggestions non realisees car il faut rester dans les consignes du projet):
 * - tester la classe Bibliotheque dans une classe separee nommee 'TestBibliotheque'
 * - utiliser JUnit qui est plus elegant pour obtenir une couverture de test plus 'standard'
 */


public class TestDocuments {

	/*
	 * Programme de test.
	 * @param args
	 */
	public static void main(String[] args) {

		//test de la classe documents
		Document[] documents = {
			new Livre("L'archipel du Goulag", "Soljenitsyne", 250),
			new Roman("Rouge Bresil", "Rufin", 120, Roman.GONCOURT),
			new Revue("Le point", 03, 2014),
			new Roman("Le mendiant", "Wiesel", 150, Roman.MEDICIS),
			new Livre("La condition humaine", "Malraux", 130),
			new Manuel("Manuel qualite ISO 9001", "AFNOR", -1, 3)
		};
	
		System.out.println("test de la classe document avec 6 documents:");
		for ( Document doc : documents ) {
			System.out.println(doc);
		}
		
		//test de la classe Bibliotheque
		Bibliotheque jaimeLire = new Bibliotheque();
		Document doc1 = new Livre("L'archipel du Goulag, tome2", "Soljenitsyne", 251);
		Document doc2 = new Roman("Rouge Bresil, tome 2", "Rufin", 121, Roman.GONCOURT);
		Document doc3 = new Revue("Le point, tome 2", 05, 2015);
		Document doc4 = new Roman("Le mendiant, tome 2", "Wiesel", 151, Roman.MEDICIS);
		Document doc5 = new Livre("La condition humaine, tome 2", "Malraux", 131);
		Document doc6 = new Manuel("Manuel qualite ISO 9001, tome 2", "AFNOR", -2, 4);
		jaimeLire.addDocument(doc1);
		jaimeLire.addDocument(doc2);
		jaimeLire.addDocument(doc3);
		jaimeLire.addDocument(doc4);
		jaimeLire.addDocument(doc5);
		jaimeLire.addDocument(doc6);
		
		System.out.println("test de la classe Bibliotheque qui contient les tomes 2 des precedents documents:");
		System.out.println(jaimeLire);
		
		jaimeLire.removeDocument(doc5);
		
		System.out.println("Etat final de la Bibliotheque apres avoir supprime 2 livres:");
		System.out.println(jaimeLire);
		
		Affichage affichageManager = new Affichage();
		affichageManager.afficherDocuments(jaimeLire.getDocuments());
		affichageManager.afficherAuteurs(jaimeLire.getDocuments());
		
		System.out.println("test fonction de recherche par titre: ('Serge Karamasov' puis 'Le mendiant, tome 2')");
		Livre recherche1 = jaimeLire.rechercheParTitre("Serge Karamasov");
		Livre recherche2 = jaimeLire.rechercheParTitre("Le mendiant, tome 2");
		if(recherche1 == null)
			System.out.println("voici le resultat de la recherche1: null");
		else
			System.out.println("voici le resultat de la recherche1: " + recherche1.getTitre());
		if(recherche2 == null)
			System.out.println("voici le resultat de la recherche2: null");
		else
			System.out.println("voici le resultat de la recherche2: " + recherche2.getTitre());
		
		System.out.println("test fonction de suppression par titre: (sur 'Le mendiant, tome 2')");
		Boolean seekAndDestroy1 = jaimeLire.suppressionParTitre("Le mendiant, tome 2");
		System.out.println("resultat: " + seekAndDestroy1.toString());
		
		
		System.out.println("test fonction de recherche roman par prix literaire: ('Roman.GONCOUR')");
		Roman rechercheRoman = jaimeLire.rechercheRomanParPrixLiteraire(Roman.GONCOURT);
		if(rechercheRoman == null)
			System.out.println("voici le resultat de la recherche de prix goncourt: null");
		else
			System.out.println("voici le resultat de la recherche de prix goncourt: " + rechercheRoman.getTitre());
		
		System.out.println("test fonction de suppression par prix literaire: (toujours sur 'Le mendiant, tome 2' avec prix goncourt)");
		Boolean seekAndDestroy2 = jaimeLire.suppressionParPrixLiteraire(Roman.GONCOURT);
		System.out.println("resultat: " + seekAndDestroy2.toString());
		
		jaimeLire.addListFromCSV("listeLivresTest.csv");
		//System.out.println(jaimeLire.toString());
		
		jaimeLire.sauvegardeToCsv("dumpTest.csv");
	}
}