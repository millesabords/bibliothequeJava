/**
 * 
 */
package controller;

import java.util.Comparator;

import model.Document;

/**
 * Permet de comparer (et donc de trier) deux documents selon leur titre
 */
public class triParTitre implements Comparator<Document>
{
    // Used for sorting in ascending order of titres
    public int compare(Document a, Document b)
    {
    	String titreA = a.getTitre();
    	String titreB = b.getTitre();
    	return titreA.compareTo(titreB);
    }
}