/**
 * 
 */
package controller;

import java.util.Comparator;

import model.Livre;

/**
 * Permet de comparer (et donc de trier) deux livres selon leur auteur
 */
public class triParAuteur implements Comparator<Livre>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Livre a, Livre b)
    {
    	String auteurA = a.getAuteur();
    	String auteurB = b.getAuteur();
    	return auteurA.compareTo(auteurB);
    }
}