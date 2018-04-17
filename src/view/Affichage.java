package view;
import controller.triParAuteur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import model.Bibliotheque;
import model.Document;
import model.Livre;
import model.Roman;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//import javax.swing.SpringLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Dimension;


/**
 * Utilitaires d'affichage
 */
public class Affichage extends JFrame {
	
    private static final long serialVersionUID = 4954725314228221853L;
    
    Bibliotheque managedBibliotheque;
	JButton[] buttons = {new JButton("Afficher tout le contenu"),
				new JButton("Rechercher un document"),//include suppresion document
				new JButton("Ajouter un nouveau document"),
				new JButton("dump contenu vers fichier CSV"),
				new JButton("import liste livres depuis CSV"),
				new JButton("vider la bibliotheque"),
				new JButton("tri lexicographique")};
    JPanel[] panels = {new JPanel(new GridLayout(0,2)),
    			new JPanel(new BorderLayout()),
    			new JPanel(new GridLayout(0,2)),
    			new JPanel(new BorderLayout()),
    			new JPanel(),
    			new JPanel(),
    			new JPanel()};
    
    public Affichage(Bibliotheque biblio){
        super("Afficheur Bibliotheque");
        this.managedBibliotheque = biblio;
        this.setSize(1024, 768);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        this.buttons[0].addActionListener(new AfficheContenuAction(this));
        this.buttons[1].addActionListener(new RechercherDocumentAction(this));
		for(JButton jbt: this.buttons) {
			this.panels[0].add(jbt);
		}
        this.setContentPane(this.panels[0]);
        this.panels[0].setBackground(Color.red);
        this.panels[1].setBackground(Color.blue);
    }
 
    //Controller pour: Afficher tout le contenu
    public class AfficheContenuAction implements ActionListener{
    	public AfficheContenuAction(Affichage mainFrame) {
            JTextArea ta = new JTextArea(45, 80);
            //ta.setBounds(203, 5, 869, 440);
            ta.setText(mainFrame.managedBibliotheque.toString());
            //ta.setBorder(border);
            ta.setLineWrap(true);
            ta.setEditable(false);
            JScrollPane sp = new JScrollPane(ta);
            sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            //sp.setBounds(50, 30, 300, 50);
            //mainFrame.panels[1].setPreferredSize(new Dimension(500, 400));
            mainFrame.panels[1].add(sp, BorderLayout.NORTH);

            JButton backBtn = new JButton("Go Back");
            backBtn.addActionListener(new goBackAction(mainFrame));
            mainFrame.panels[1].add(backBtn, BorderLayout.CENTER);
            backBtn.setActionCommand("back");
    	}
        public void actionPerformed(ActionEvent e) {
        	/*String cmd = e.getActionCommand();
            if (cmd.equals("back")) {
                dispose();
            }
            else {*/
            	Affichage.this.AfficherContenu();            	
            /*}*/
        }
    }
    public void AfficherContenu(){
        this.setContentPane(this.panels[1]);
        //this.pack();
        this.revalidate();
    }
    
    //Controller pour: rechercher un document
    public class RechercherDocumentAction implements ActionListener{
    	public RechercherDocumentAction(Affichage mainFrame) {
        	JLabel titreLbl = new JLabel("Recherche par Titre:", JLabel.TRAILING);
        	JTextField titreTf = new JTextField("", 5);

        	JLabel prixLitLbl = new JLabel("Recherche par Prix Literaire:", JLabel.TRAILING);
        	JTextField prixLitTf = new JTextField("", 5);
        	
            mainFrame.panels[2].add(titreLbl);
            titreLbl.setLabelFor(titreTf);
            mainFrame.panels[2].add(titreTf);
            mainFrame.panels[2].add(prixLitLbl);
            prixLitLbl.setLabelFor(prixLitTf);
            mainFrame.panels[2].add(prixLitTf);

            JButton backBtn = new JButton("Go Back");
            JButton okBtn = new JButton("Ok");
            backBtn.addActionListener(new goBackAction(mainFrame));
            okBtn.addActionListener(new searchDocument(mainFrame, titreTf, prixLitTf));
            mainFrame.panels[2].add(backBtn);
            mainFrame.panels[2].add(okBtn);
            //backBtn.setActionCommand("back");
    	}
        public void actionPerformed(ActionEvent e) {
            	Affichage.this.chercheDoc();            	
        }
    }
    public void chercheDoc(){
        this.setContentPane(this.panels[2]);
        this.revalidate();
    }
    
    //go back button
    public class goBackAction implements ActionListener {
    	Affichage mainFrame;
    	public goBackAction(Affichage aff){
    		//super(texte);
    		mainFrame = aff;
    	}
    	public void actionPerformed(ActionEvent e) { 
    		mainFrame.setContentPane(mainFrame.panels[0]);
    	} 
    }
    
    //confirm searching for document with title or award
    public class searchDocument implements ActionListener {
    	Affichage mainFrame;
    	JTextField ta_titre;
    	JTextField ta_prix;
    	JTextArea ta_readonly;
    	public searchDocument (Affichage aff, JTextField ta1, JTextField ta2){
    		mainFrame = aff;
            ta_titre = ta1;
            ta_prix = ta2;
            ta_readonly = new JTextArea(45, 80);
            ta_readonly.setLineWrap(true);
            ta_readonly.setEditable(true);
            JScrollPane sp = new JScrollPane(ta_readonly);
            sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            mainFrame.panels[3].add(sp, BorderLayout.NORTH);
            JButton backBtn = new JButton("Go Back");
            backBtn.addActionListener(new goBackAction(mainFrame));
            mainFrame.panels[3].add(backBtn, BorderLayout.CENTER);
            backBtn.setActionCommand("back");
    	}
    	public void actionPerformed(ActionEvent e) { 
    		String prix = ta_prix.getText();
    		String titre = ta_titre.getText();
    		String res = "Not found.";
            if(titre.isEmpty() == false) {
            	Document docRes = mainFrame.managedBibliotheque.rechercheParTitre(titre);
            	if(docRes != null)
            		res = docRes.toString();
            }
            else {
            	if(prix.isEmpty() == false) {
            		Document docRes = mainFrame.managedBibliotheque.rechercheRomanParPrixLiteraire(Roman.PrixStrToInt(prix));
            		if(docRes != null)
            			res = docRes.toString();	
            	}
            }
            
    		ta_readonly.setText(res);    		
            String debug3 = ta_readonly.getText();
    		mainFrame.setContentPane(mainFrame.panels[3]);
            mainFrame.revalidate();
    	} 
    }   

    ///////////////////////////////////////*****************************************/////////////////////
    ///////////////////////////////////////*****************************************/////////////////////
    ///////////////////////////////////////*****************************************/////////////////////  
    
	 /**
	 * @param docs documetns a afficher
	 * cette methode est une mauvaise idee de conception: elle ajoute de la redondance de code:
	 * la fonctionnalite fait doublon avec Bibliotheque.toString () 
	 */
	public static void afficherDocuments(Collection<Document> docs) {
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
	public static void afficherAuteurs(Collection<Document> docs) {
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
