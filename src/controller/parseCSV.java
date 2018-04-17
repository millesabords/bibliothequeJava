/**
 * 
 */
package controller;

import java.util.ArrayList;

import model.Bibliotheque;
import model.Document;
import model.Livre;
import model.Manuel;
import model.Revue;
import model.Roman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * reading csv file containing list of books details
 */
public class parseCSV {

	public static final char csvDelimiter = ';';
	
    public static ArrayList<Document> ReadDocument(String fileName)  throws FileNotFoundException{
        ArrayList<Document> bigListe = new ArrayList<Document>();
        try
        {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useDelimiter(";");
            
           	scanner.nextLine();//skip first line
            while(scanner.hasNextLine()){
            	String[] items = scanner.nextLine().split(";");
            	if(items.length == 8) {
            		String titleDocument = items[0];
            		String typeDocument = items[7];
            		//System.out.print(title);
            
                	if(typeDocument.equals("Roman")) {
                		bigListe.add(new Roman(titleDocument, items[1], Integer.parseInt(items[2]), Roman.PrixStrToInt(items[6])));
                	}
                	if(typeDocument.equals("Manuel")) {
                		bigListe.add(new Manuel(titleDocument, items[1], Integer.parseInt(items[2]), Integer.parseInt(items[5])));
                	}
                	if(typeDocument.equals("Revue")) {
                		bigListe.add(new Revue(titleDocument, Integer.parseInt(items[3]), Integer.parseInt(items[4])));
                	}
                	if(typeDocument.equals("Livre")) {
                		bigListe.add(new Livre(titleDocument, items[1], Integer.parseInt(items[2])));
                	}
            	}
            	else {
            		System.err.println("ReadDocument constructor error: document has not 7 categories as information");
            	}
            }
           	scanner.close();
           	return bigListe;
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
            return null;
        }
    }
    
    
    /**
     * @param fileName
     * @param biblio
     * @return boolean
     */
    public static Boolean WriteDocument(String fileName, Bibliotheque biblio) {
        try
        {
        	BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
        	out.write("Titre;Auteur;Nombre pages;Mois;Annee;niveau;Prix literaire;Type");
        	out.newLine();
        	for ( Document doc : biblio.getDocuments()) {
    			out.write(doc.toCsv());//toCsv() est un bon exemple de polymorphisme
    			out.newLine();
    		}

        	out.flush();
        	out.close();
        	return true;
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
            return null;
        }
    }
    
};
