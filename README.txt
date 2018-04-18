Before use:
- jre1.8.0_161 required (or more recent)
- JAVA_HOME properly set and sourced in /etc/environment (or ~/.bashrc or ~/.profile or /etc/profile.d.java.sh)
- listeLivresTest.csv file must be in same folder as the archive

How to use:
$java -jar archive.jar
#archive compressed with:
#jar cmvf META-INF/MANIFEST.MF taist.jar .


Technical explanations:
Explications sur la partie UI avec Swing (fichier Affichage.java).
La classe "Affichage" est la JFrame (en quelques sortes le panel racine qui contient tous les autres panel)
Les differentes UI sont sous forme de panels qui sont tous contenus dans cette classe Affichage (c'est moche et ca meriterait d'etre modularise)
Un panel par fonctionnalite (c'est ce que l'on voit sur le panneau d'acceuil lorsque l'on lance l'application)
Chaque panel est implemente dans une sous-classe de cette classe affichage.
Chaque panel herite de ActionListener, c'est ce qui permet d'avoir un systeme interactif avec des boutons:
et possede donc un constructeur qui mets en place les elements UI et est appelee au demarrage de l'application,
et une methode ActionEvent qui contient le code appele lorsque l'on veut faire apparaitre ce panel (qui existe deja donc)
Pour switcher d'un panel a l'autre, on utilise 'setContentPane()' sur le handler de la JFrame principale (du coup il faut se trimbaler ce handler un peu partout) en lui donnant en argument le numero du panel que l'on souhaite afficher (ex numero 3 = panneau de recherche de document...)

Certains panels vont utiliser une classe supplementaire parcequ'ils ont une fonctionnalite sous-jacente.
Exemple: - le panel 'recherche document' va proposer une fonction de suppression de document -> classe suppression document avec un petit dialog de confirmation
		 - toujours au niveau du panel 'recherche document', on a aussi la fonctionnalite 'go back' pour revenir en arriere qui necessite une classe aussi (la classe pour gerer le retour arriere fait juste reapparaitre le panneau principal: la JFrame). Go back est re-utilise par plusieurs panel.

FAQ anticipee (peut-etre non exhaustive):
- Pour comprendre le fonctionnement concretement, le meilleur exemple est de regarder les 2 classes 'addDocument' et 'addDocumentAction':
	- addDocument: represente la panel 'ajouter un document' au niveau UI
	- addDocumentAction: valide ou invalide le formulaire d'ajout de addDocument, est declenche par le bouton 'Ok' de addDocument
- Pourquoi 2 classes? -> une classe par evenement: faire apparaitre le panel 'addDocument' est un evenement, confirmer l'ajout de document en est un autre.
- Pourquoi les textfields sont trimballes partout au lieu du texte qu'ils contiennent? -> parceque le texte qu'ils contiennent peut changer et on a besoin d'acceder a leur valeur non pas lors du constructeur (qui se fait au demarrage de l'appli), mais dans la methode de gestion de l'event (qui se fait a l'instant t)
- Pourquoi passer le handler 'mainFrame' de la JFrame principale partout, et ne pas utiliser le mot-cle 'this'? -> parceque nous sommes la plupart du temps dans des sous-classes, et que le mot-cle this va referer a ces sous-classes (et surtout c'est tres ambigu) et non pas a la classe principale.


Suggestions d'ameliorations:
- la UI (Affichage.java) ne passe pas par le package controlleur et va directement piocher dans le package modele, ce qui est une violation du principe MVC.
- la recherche par prix literaire renvoit le premier document trouve, mais devrait plutot renvoyer une liste (il peut y avoir plusieurs roman ayant recu un meme prix dans une bibliotheque)
- l'import/export csv devrait pouvoir donner le choix pour le nom du fichier. 
