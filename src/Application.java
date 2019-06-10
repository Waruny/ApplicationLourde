/* import des packages contenant ce dont on a besoin pour cr�er une IHM (Interface Homme Machine)
la plupart des classes import�es sont issues de Swing qui est une librairy graphique pour Java
*/

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;


/*la classe Application va h�riter (extends) de la classe JFrame (= cadre de fenetre en anglais)
 *  qui est une classe qui permet de cr�er une IHM 
avec toutes ses m�thodes */

public class Application extends JFrame {

	private Application appli;

	private JPanel logo;

	private JLabel titreIdentifiant, titreMdp;

	private JTextField champUser;

	private JPasswordField champMdp;

	private JButton boutonConnexion;

	private JMenuBar barreMenu;

	private JTable tableau;

	private JTableHeader entete;
	
	private JScrollPane scrollTableau;

	private JLabel refresh;

	// RequeteBDD uneRequete;

	public Application() {

		appli = this;
		setTitle("TRUBL");
		setSize(900, 500);
		setLocationRelativeTo(null); // On centre la fen�tre sur l'�cran
		setResizable(true); // On interdit la redimensionnement de la fen�tre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit �  l'application de se fermer lors du clic sur la
														// croix
		setContentPane(buildContentPane());

	}

	private JPanel buildContentPane() {
		JPanel contenu = new JPanel(); // contenu => il va contenir nos diff�rents panneaux
		contenu.setPreferredSize(new Dimension(900, 500));
		// On d�finit le layout manager = (le gestionnaire de disposition, placement des
		// �l�ments)
		contenu.setLayout(new GridBagLayout()); // GridBagLayout c'est la disposition du contenu en grille

		ImageIcon icon = new ImageIcon("logo.png");
		JLabel img = new JLabel(icon);
		logo = new JPanel();
		logo.add(img);
		logo.setPreferredSize(new Dimension(466, 151));

		// on cr��e deux �tiquettes JLabel
		titreIdentifiant = new JLabel("Identifiant :");
		titreMdp = new JLabel("Mot de passe :");

		// JTextField est le champ de saisie de nos �tiquettes
		champUser = new JTextField();
		champMdp = new JPasswordField("Mot de passe");

		// on rajoute un bouton cliquable pour lancer la fonction de v�rification des
		// informations
		this.boutonConnexion = new JButton("Connexion");

		// on initialise �  vide
		champMdp.setText("");
		champUser.setText("");

		// on cr�ee une barre de menu

		barreMenu = new JMenuBar();

		// premier onglet
		JMenu fichier = new JMenu("Fichier");
		barreMenu.add(fichier);

		JMenuItem deconnexion = new JMenuItem(new GestionDeconnecter(this, "Se d�connecter"));
		fichier.add(deconnexion);
		JMenuItem quitter = new JMenuItem(new GestionQuitter("Quitter"));
		fichier.add(quitter);

		// deuxi�me onglet
		JMenu seance = new JMenu("Articles");
		barreMenu.add(seance);

		JMenuItem art = new JMenuItem("Liste des articles");
		seance.add(art);
		JMenuItem bestart = new JMenuItem("Meilleurs articles");
		seance.add(bestart);

		// troisi�me onglet
		JMenu stats = new JMenu("Clients");
		barreMenu.add(stats);

		JMenuItem cli = new JMenuItem("Liste des clients");
		stats.add(cli);
		JMenuItem bestcli = new JMenuItem("Meilleurs clients");
		stats.add(bestcli);

		// quatri�me onglet
		JMenu commandes = new JMenu("Commandes");
		barreMenu.add(commandes);

		JMenuItem comm = new JMenuItem("Liste des commandes");
		commandes.add(comm);

		// on alimente notre JMenuBar
		setJMenuBar(barreMenu);

		// on cr�e un tableau vide
		tableau = new JTable();
		
		// on cr�e une entete vide
		entete = tableau.getTableHeader();
		
		// on met notre tableau dans un scroll
		scrollTableau = new JScrollPane(tableau);

		GridBagConstraints grilleManager = new GridBagConstraints();

		// on commence par le logo

		grilleManager.gridx = 0; // notre grille commence �  x=0 en largeur
		grilleManager.gridy = 0; // notre grille commence �  y=0 en hauteur
		grilleManager.gridwidth = GridBagConstraints.REMAINDER; // notre logo est la seule cellule de cette ligne c'est
																// donc le dernier �l�ment
		grilleManager.anchor = GridBagConstraints.PAGE_START; // on ancre le logo au d�but de la grille
		grilleManager.insets = new Insets(0, 0, 0, 0); // on ne souhaite pas de marges
		grilleManager.weightx = 1; // on redistribue l'espace vide en largeur car notre image est moins large que
									// la fenetre
		grilleManager.weighty = 1; // on redistribue l'espace vide en hauteur
		grilleManager.fill = GridBagConstraints.BOTH; // remplissage si la cellule est plus grande que le composant

		contenu.add(logo, grilleManager);

		// on s'occupe de l'�tiquette du champ identifiant
		grilleManager.gridx = 0;
		grilleManager.gridy = 1; // on est sur une deuxi�me ligne
		grilleManager.gridwidth = 1; // etiquette devra composer une seule cellule en largeur
		grilleManager.gridheight = 1; // etiquettet devra composer une seule cellule en hauteur
		grilleManager.weightx = 0; // on r�tabli les poids � z�ro pour que l'espace vide ne soit pas redistribuer
									// sur cette ligne
		grilleManager.weighty = 0;
		grilleManager.fill = GridBagConstraints.NONE;
		grilleManager.anchor = GridBagConstraints.BASELINE_LEADING; // on aligne notre etiquette en haut �  droite de la
																	// cellule
		grilleManager.insets = new Insets(10, 200, 0, 0); // on rajoute une marge autour pour pas que �a soit coll� au
															// champs qui viendra (haut, gauche, bas, droite)

		contenu.add(titreIdentifiant, grilleManager);

		// on s'occupe du champ de saisie de l'identifiant
		grilleManager.gridx = 1; // �  la cellule qui suit l'�tiquette
		grilleManager.gridy = 1; // ligne de l'�tiquette
		grilleManager.gridwidth = GridBagConstraints.REMAINDER; // dernier composant de cette ligne
		grilleManager.gridheight = 1;
		grilleManager.fill = GridBagConstraints.HORIZONTAL; // on le laisse s'�tendre dans l'espace de la cellule
															// restant
		grilleManager.anchor = GridBagConstraints.BASELINE; // on aligne avec notre �tiquette (en haut de la cellule au
															// centre)
		grilleManager.insets = new Insets(10, 15, 0, 200); // on ajuste les marges

		contenu.add(champUser, grilleManager);

		// meme chose pour l'�tiquette mot de passe et son champ de saisie
		grilleManager.gridx = 0;
		grilleManager.gridy = 2;
		grilleManager.gridwidth = 1;
		grilleManager.gridheight = 1;
		grilleManager.fill = GridBagConstraints.NONE;
		grilleManager.anchor = GridBagConstraints.BASELINE_LEADING;
		grilleManager.insets = new Insets(10, 200, 100, 0);

		contenu.add(titreMdp, grilleManager);

		// on s'occupe du champ de saisie du mdp
		grilleManager.gridx = 1;
		grilleManager.gridy = 2;
		grilleManager.gridwidth = GridBagConstraints.REMAINDER; // dernier composant de cette ligne
		grilleManager.gridheight = 1;
		grilleManager.fill = GridBagConstraints.HORIZONTAL;
		grilleManager.anchor = GridBagConstraints.BASELINE;
		grilleManager.insets = new Insets(10, 15, 100, 200);

		contenu.add(champMdp, grilleManager);

		// on s'occupe du bouton maintenant
		grilleManager.gridx = 0;
		grilleManager.gridy = 3;
		grilleManager.gridheight = 1;
		grilleManager.gridwidth = GridBagConstraints.REMAINDER; // dernier composant de cette ligne
		grilleManager.fill = GridBagConstraints.NONE; // aucun redimensionnement
		grilleManager.anchor = GridBagConstraints.CENTER; // on aligne notre bouton dans la cellule
		grilleManager.insets = new Insets(0, 15, 100, 0); // on ajuste les marges

		contenu.add(boutonConnexion, grilleManager);

		//en t�te des tableaux
		grilleManager.gridx = 0;
		grilleManager.gridy = 4;
		grilleManager.gridheight = 1;
		grilleManager.gridwidth = GridBagConstraints.REMAINDER; // dernier composant de cette ligne
		grilleManager.anchor = GridBagConstraints.PAGE_START;// on aligne notre bouton dans la cellule
		grilleManager.insets = new Insets(0, 0, 0, 0);
		grilleManager.weightx = 1; // on redistribue l'espace vide en largeur
		contenu.add(entete, grilleManager);

		grilleManager.gridx = 0;
		grilleManager.gridy = 5;
		grilleManager.gridheight = 1;
		grilleManager.gridwidth = GridBagConstraints.REMAINDER; // dernier composant de cette ligne
		grilleManager.fill = GridBagConstraints.HORIZONTAL; // aucun redimensionnement
		grilleManager.anchor = GridBagConstraints.NORTH;// on aligne notre bouton dans la cellule
		grilleManager.insets = new Insets(0, 0, 0, 0);
		grilleManager.weightx = 1; // on redistribue l'espace vide en largeur
		grilleManager.weighty = 1;
		contenu.add(tableau, grilleManager);

		refresh = new JLabel("");
		contenu.add(refresh, grilleManager);

		// on rend visible les �l�ments qui nous int�resse � l'instanciation de
		// l'Application

		refresh(); // sert � rafra�chir l'�tat des contenus de notre panneau

		logo.setVisible(true);
		titreIdentifiant.setVisible(true);
		champUser.setVisible(true);
		titreMdp.setVisible(true);
		champMdp.setVisible(true);
		boutonConnexion.setVisible(true);
		entete.setVisible(false);
		tableau.setVisible(false);
		barreMenu.setVisible(false);

		// on ajoute sur le bouton de connexion un point d'�coute, un listener

		boutonConnexion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				RequeteBDD uneRequete = new RequeteBDD();
				boolean test = uneRequete.AuthentifierUser(champUser, champMdp);

				if (test == true) {

					refresh();
					
					logo.setVisible(false);
					titreIdentifiant.setVisible(false);
					champUser.setVisible(false);
					champMdp.setVisible(false);
					titreMdp.setVisible(false);
					boutonConnexion.setVisible(false);
					barreMenu.setVisible(true); // on rend visible la barre de menu

				}

			}
		});

		art.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (tableau != null) {
					remove(tableau); // �a veut dire qu'on a d�j� fait une requete on doit effacer la JTable correspondante au pr�alable
					remove(entete);
					remove(scrollTableau);
					refresh();
				}

				RequeteBDD uneRequete = new RequeteBDD();
				tableau = uneRequete.listerArticles();
				scrollTableau = new JScrollPane(tableau);
				entete = tableau.getTableHeader();

				contenu.add(entete, grilleManager);
				contenu.add(scrollTableau, grilleManager);

				refresh();

				entete.setVisible(true);
				tableau.setVisible(true);
				logo.setVisible(false);
				titreIdentifiant.setVisible(false);
				champUser.setVisible(false);
				champMdp.setVisible(false);
				titreMdp.setVisible(false);
				boutonConnexion.setVisible(false);
				barreMenu.setVisible(true);

			}
		});

		
		cli.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (tableau != null) {
					remove(tableau); // �a veut dire qu'on a d�j� fait une requete on doit effacer la JTable correspondante au pr�alable
					remove(entete);
					remove(scrollTableau);
					refresh();
				}

				RequeteBDD uneRequete = new RequeteBDD();
				tableau = uneRequete.listerClients();
				scrollTableau = new JScrollPane(tableau);
				entete = tableau.getTableHeader();

				contenu.add(entete, grilleManager);
				contenu.add(scrollTableau, grilleManager);

				refresh();

				entete.setVisible(true);
				tableau.setVisible(true);
				logo.setVisible(false);
				titreIdentifiant.setVisible(false);
				champUser.setVisible(false);
				champMdp.setVisible(false);
				titreMdp.setVisible(false);
				boutonConnexion.setVisible(false);
				barreMenu.setVisible(true);

			}
		});
		
		comm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (tableau != null) {
					remove(tableau); // �a veut dire qu'on a d�j� fait une requete on doit effacer la JTable correspondante au pr�alable
					remove(entete);
					remove(scrollTableau);
					refresh();
				}

				RequeteBDD uneRequete = new RequeteBDD();
				tableau = uneRequete.listerCommandes();
				scrollTableau = new JScrollPane(tableau);
				entete = tableau.getTableHeader();

				contenu.add(entete, grilleManager);
				contenu.add(scrollTableau, grilleManager);

				refresh();

				entete.setVisible(true);
				tableau.setVisible(true);
				logo.setVisible(false);
				titreIdentifiant.setVisible(false);
				champUser.setVisible(false);
				champMdp.setVisible(false);
				titreMdp.setVisible(false);
				boutonConnexion.setVisible(false);
				barreMenu.setVisible(true);

			}
		});
		
		
	
		// on retourne notre JPanel, notre panneau
		return contenu;
	}

	// on fait des get et des set pour pouvoir acc�der aux �l�ments de la fenetre de
	// l'application dans les classes de gestion
	// �a servira pour les rendre visible ou les masquer etc...

	public JPanel getLogo() {
		return this.logo;
	}

	public JLabel getIdentifiant() {
		return this.titreIdentifiant;
	}

	public JLabel getMdp() {
		return this.titreMdp;
	}

	public JTextField getUser() {
		return this.champUser;
	}

	public JPasswordField getMotdepasse() {
		return this.champMdp;
	}

	public JButton getBoutonConnexion() {
		return this.boutonConnexion;
	}

	public JMenuBar getBarreMenu() {
		return this.barreMenu;
	}

	public JTable getTableau() {
		return this.tableau;
	}
	
	public JTableHeader getTableHeader() {
		return this.entete;
	}
	
	public JScrollPane getScrollTableau() {
		return this.scrollTableau;
	}

	public void setTableau(JTable tab) {
		this.tableau = tab;

	}

	public void refresh() {
		refresh.setVisible(true);
		refresh.setVisible(false);
	}
}
