package view;
import controller.triParAuteur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import model.Bibliotheque;
import model.Document;
import model.Livre;
import model.Manuel;
import model.Revue;
import model.Roman;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
    			new JPanel(new GridLayout(0,2)),
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
        this.buttons[2].addActionListener(new addDocument(this));
        this.buttons[3].addActionListener(new exportCSVBibliothequeAction(this));
        this.buttons[4].addActionListener(new importCSVBibliothequeAction(this));
        this.buttons[5].addActionListener(new viderBibliothequeAction(this));
        this.buttons[6].addActionListener(new trierBibliothequeAction(this));
        
		for(JButton jbt: this.buttons) {
			this.panels[0].add(jbt);
		}
        this.setContentPane(this.panels[0]);
        this.setBackground(Color.blue);
        //this.panels[0].setBackground(Color.red);
        //this.panels[1].setBackground(Color.blue);
    }
 
    //Controller pour: Afficher tout le contenu
    public class AfficheContenuAction implements ActionListener{
    	JTextArea ta;
    	public AfficheContenuAction(Affichage mainFrame) {
            ta = new JTextArea(45, 80);
            //ta.setBounds(203, 5, 869, 440);
            //ta.setBorder(border);
            ta.setLineWrap(true);
            ta.setEditable(true);
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
            	Affichage.this.AfficherContenu(ta);            	
            /*}*/
        }
    }
    public void AfficherContenu(JTextArea ta){
   		ta.setText(this.managedBibliotheque.toString());
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
   		String prix;
   		String titre;
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
            mainFrame.panels[3].add(backBtn, BorderLayout.SOUTH);
            //backBtn.setActionCommand("back");
            JButton deleteBtn = new JButton("Effacer Document");
            deleteBtn.addActionListener(new effacerDocumentAction(mainFrame, ta_titre));
            mainFrame.panels[3].add(deleteBtn, BorderLayout.CENTER);
    	}
    	public void actionPerformed(ActionEvent e) { 
    		prix = ta_prix.getText();
    		titre = ta_titre.getText();
    		String res = "Not found.";
            if(titre.isEmpty() == false) {
            	Document docRes = mainFrame.managedBibliotheque.rechercheParTitre(titre);
            	if(docRes != null) {
            		res = docRes.toString();
            		titre = docRes.getTitre();
            	}
            }
            else {
            	if(prix.isEmpty() == false) {
            		Document docRes = mainFrame.managedBibliotheque.rechercheRomanParPrixLiteraire(Roman.PrixStrToInt(prix));
            		if(docRes != null) {
            			res = docRes.toString();
            			titre = docRes.getTitre();
            		}
            	}
            }
            
    		ta_readonly.setText(res);    		
    		mainFrame.setContentPane(mainFrame.panels[3]);
            mainFrame.revalidate();
    	} 
    }   

    //effacer document par nom apres document after search go back button
    public class effacerDocumentAction implements ActionListener {
    	Affichage mainFrame;
    	JTextField titreDoc;
    	public effacerDocumentAction(Affichage aff, JTextField titreF){
    		mainFrame = aff;
    		titreDoc = titreF;
    	}
    	public void actionPerformed(ActionEvent e) {
    		Boolean res = mainFrame.managedBibliotheque.suppressionParTitre(titreDoc.getText());
    		if(res)
    			JOptionPane.showMessageDialog(mainFrame, "document " + titreDoc.getText() + " efface.", "information", JOptionPane.INFORMATION_MESSAGE);
    		else
    			JOptionPane.showMessageDialog(mainFrame, "le document " + titreDoc.getText() + " n'a pas pu etre efface.", "erreur", JOptionPane.ERROR_MESSAGE);
    		mainFrame.setContentPane(mainFrame.panels[0]);
    	} 
    }

    //Panel to add a new document
    public class addDocument implements ActionListener {
    	//declare UI elements
    	Affichage mainFrame;
    	JLabel[] addDocLabels;
    	JTextField[] addDocTextFields;
    	ButtonGroup[] addDocButtonGroups;
    	JRadioButton[] addDocRadioButtons;
    	
   		//String prix;
   		//String titre;
    	public addDocument(Affichage aff){
    		mainFrame = aff;
    		
    		//instanciate UI elements
    		addDocLabels = new JLabel[] {
    				new JLabel("Titre:", JLabel.TRAILING),
    				new JLabel("Auteur:", JLabel.TRAILING),
    				new JLabel("NbPages:", JLabel.TRAILING),
    				new JLabel("Mois:", JLabel.TRAILING),
    				new JLabel("Annee:", JLabel.TRAILING),
    				new JLabel("Niveau:", JLabel.TRAILING),
    				new JLabel("Prix literaire:", JLabel.TRAILING),
    				new JLabel("Type:", JLabel.TRAILING),};
    		addDocTextFields = new JTextField[] {
    				new JTextField("", 5),
    				new JTextField("", 5),
    				new JTextField("", 5),
    				new JTextField("", 5),
    				new JTextField("", 5),
    				new JTextField("", 5)};
        	addDocButtonGroups = new ButtonGroup[]{new ButtonGroup(), new ButtonGroup()};
        	addDocRadioButtons = new JRadioButton[] {new JRadioButton("Goncourt"),
        			new JRadioButton("Medicis"),
        			new JRadioButton("Livre"),
        			new JRadioButton("Roman"),
        			new JRadioButton("Manuel"),
        			new JRadioButton("Revue")};
        	addDocRadioButtons[0].setActionCommand("GONCOURT");
        	addDocRadioButtons[1].setActionCommand("MEDICIS");
        	addDocRadioButtons[2].setActionCommand("Livre");
        	addDocRadioButtons[3].setActionCommand("Roman");
        	addDocRadioButtons[4].setActionCommand("Manuel");
        	addDocRadioButtons[5].setActionCommand("Revue");
        	
        	//associate labels with texfields and radiobuttongroups with radiobuttons
    		for (Integer i : Arrays.asList(0,1,2,3,4,5)) {
    			addDocLabels[i].setLabelFor(addDocTextFields[i]);
    			mainFrame.panels[4].add(addDocLabels[i]);
    			mainFrame.panels[4].add(addDocTextFields[i]);
    		}
   			mainFrame.panels[4].add(addDocLabels[6]);
        	JPanel prixLitButtonsPanel = new JPanel();
   			mainFrame.panels[4].add(prixLitButtonsPanel);
        	for (Integer i : Arrays.asList(0,1)) {
            	addDocButtonGroups[0].add(addDocRadioButtons[i]);
            	mainFrame.panels[4].add(addDocRadioButtons[i]);
            }
   			mainFrame.panels[4].add(addDocLabels[7]);
        	JPanel typeButtonsPanel = new JPanel();
   			mainFrame.panels[4].add(typeButtonsPanel);
            for (Integer i : Arrays.asList(2,3,4,5)) {
            	addDocButtonGroups[1].add(addDocRadioButtons[i]);
            	mainFrame.panels[4].add(addDocRadioButtons[i]);
            }
         
        	addDocRadioButtons[0].setSelected(true);
        	addDocRadioButtons[2].setSelected(true);
        
        	//let's not forget go back button and validate button
            JButton backBtn = new JButton("Go Back");
            backBtn.addActionListener(new goBackAction(mainFrame));
            mainFrame.panels[4].add(backBtn);

            JButton okBtn = new JButton("Ajouter Document");
            okBtn.addActionListener(new addDocumentAction(mainFrame, addDocTextFields, addDocButtonGroups));
            mainFrame.panels[4].add(okBtn);
    	}
		@Override
		public void actionPerformed(ActionEvent e) {
    		mainFrame.setContentPane(mainFrame.panels[4]);
            mainFrame.revalidate();
    	}
    }
    
    //Execution class: ajouter le document effectivement (apres validation dans le formulaire effectuee dans addDocument)
    public class addDocumentAction implements ActionListener {
    	Affichage mainFrame;
    	JTextField[] detailsTfs;
    	ButtonGroup[] detailsBgs;
		public addDocumentAction (Affichage aff, JTextField[] textfs, ButtonGroup[] buttonGroups) {
    		mainFrame = aff;
    		detailsTfs = textfs;
    		detailsBgs = buttonGroups;
    	}
   		//validation du formulaire de saisie d'un document
    	public void actionPerformed(ActionEvent e) {
    		Document newDoc = null;
    		Boolean res = false;
    		String errReason = "";
    		String titre = detailsTfs[0].getText();
    		String auteur = detailsTfs[1].getText();
    		int nbPages = 0;
    		String tmp = detailsTfs[2].getText();
    		if ((!tmp.isEmpty()) && (tmp.chars().allMatch(Character::isDigit)))
    			nbPages = Integer.parseInt(tmp);
    		int mois = 0;
    		tmp = detailsTfs[3].getText();
    		if ((!tmp.isEmpty()) && (tmp.chars().allMatch(Character::isDigit)))
    			mois = Integer.parseInt(tmp);
    		int annee = 0;
    		tmp = detailsTfs[4].getText();
    		if ((!tmp.isEmpty()) && (tmp.chars().allMatch(Character::isDigit)))
    			annee = Integer.parseInt(tmp);
    		int niveau = 0;
    		tmp = detailsTfs[5].getText();
    		if ((!tmp.isEmpty()) && (tmp.chars().allMatch(Character::isDigit)))
    			niveau = Integer.parseInt(tmp);
    		
    		tmp = detailsBgs[0].getSelection().getActionCommand();
    		int prixLit = 0;
    		if(tmp.equals("GONCOURT"))
    			prixLit = 1;    			
    		String type = detailsBgs[1].getSelection().getActionCommand();
 
            if(titre.isEmpty()) {
            	errReason = "champ titre vide";
            }
            else {
            	if(mainFrame.managedBibliotheque.rechercheParTitre(titre) != null) {
                	errReason = "titre de document deja existant";            		
            	}
            	if(type == "Livre") {
            		if(auteur.isEmpty() || nbPages <= 0)
            			errReason = "auteur ou nb de pages non correctement renseigne";
            		else
            			newDoc = new Livre(titre, auteur, nbPages);
            	}
            	if(type == "Roman") {
            		if(auteur.isEmpty() || nbPages <= 0)
            			errReason = "auteur ou nb de pages non correctement renseigne";
            		else
            			newDoc = new Roman(titre, auteur, nbPages, prixLit);
            	}
            	if(type == "Manuel") {
            		if(auteur.isEmpty() || nbPages <= 0)
            			errReason = "auteur ou nb de pages non correctement renseigne";
            		else
            			newDoc = new Manuel(titre, auteur, nbPages, niveau);            		
            	}
            	if(type == "Revue") {
            		if(auteur.isEmpty() || nbPages <= 0)
            			errReason = "auteur ou nb de pages non correctement renseigne";
            		else
            			newDoc = new Revue(titre, mois, annee);            		
            	}
            	if(errReason.isEmpty() && newDoc != null && !newDoc.getTitre().isEmpty())
            		res = mainFrame.managedBibliotheque.addDocument(newDoc);
            }
    		
    		if(res)
    			JOptionPane.showMessageDialog(mainFrame, "document " + titre + " enregistre.", "information", JOptionPane.INFORMATION_MESSAGE);
    		else
    			JOptionPane.showMessageDialog(mainFrame, "le document n'a pas pu etre enregistre. " + errReason, "erreur", JOptionPane.ERROR_MESSAGE);
    		mainFrame.setContentPane(mainFrame.panels[0]);
    	} 
    }
   
    
    //Vider bibliotheque
    public class viderBibliothequeAction implements ActionListener{
    	Affichage mainFrame;
    	public viderBibliothequeAction(Affichage mainframe) {
    		mainFrame = mainframe;
    	}
        public void actionPerformed(ActionEvent e) {
            mainFrame.managedBibliotheque.eraseAll();
    		JOptionPane.showMessageDialog(mainFrame, "All documents removed from bibliotheque", "information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //trier bibliotheque
    public class trierBibliothequeAction implements ActionListener{
    	Affichage mainFrame;
    	public trierBibliothequeAction(Affichage mainframe) {
    		mainFrame = mainframe;
    	}
        public void actionPerformed(ActionEvent e) {
            mainFrame.managedBibliotheque.triLexicographique();
    		JOptionPane.showMessageDialog(mainFrame, "All documents in bibliotheque were sorted", "information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //export csv
    public class exportCSVBibliothequeAction implements ActionListener{
    	Affichage mainFrame;
    	public exportCSVBibliothequeAction(Affichage mainframe) {
    		mainFrame = mainframe;
    	}
        public void actionPerformed(ActionEvent e) {
        	String fileName = "libraryDump.csv";
        	Boolean res = mainFrame.managedBibliotheque.sauvegardeToCsv(fileName);
    		if(res)
    			JOptionPane.showMessageDialog(mainFrame, "All documents in bibliotheque were dumped to file " + fileName, "information", JOptionPane.INFORMATION_MESSAGE);
    		else
    			JOptionPane.showMessageDialog(mainFrame, "Impossible d'exporter vers le fichier " + fileName, "erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //import csv
    public class importCSVBibliothequeAction implements ActionListener{
    	Affichage mainFrame;
    	public importCSVBibliothequeAction(Affichage mainframe) {
    		mainFrame = mainframe;
    	}
        public void actionPerformed(ActionEvent e) {
        	String fileName = "listeLivresTest.csv";
            Boolean res = mainFrame.managedBibliotheque.addListFromCSV(fileName);
    		if(res)
    			JOptionPane.showMessageDialog(mainFrame, "documents from " + fileName + "were imported to library", "information", JOptionPane.INFORMATION_MESSAGE);
    		else
    			JOptionPane.showMessageDialog(mainFrame, "Impossible d'imoprter depuis le fichier " + fileName, "erreur", JOptionPane.ERROR_MESSAGE);
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
