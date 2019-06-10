import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

public class RequeteBDD
{
    private Application appli;

    private Connection conn;

    public RequeteBDD()
    {

    }

    public Connection ConnexionBase()
    {

        Base base = new Base();
        base.connexionBDD();
        conn = base.getConnect();

        return conn;

    }

    public boolean AuthentifierUser(JTextField user, JPasswordField motdepasse)
    {
        boolean resultatRequete = false;

        Connection conn = ConnexionBase();
        Statement statement = null;
        ResultSet resultat;

        String login = user.getText();
        String password = String.valueOf(motdepasse.getPassword());

        try
        {
            // createStatement() est issue de l'import de java.sql il sert √  cr√©er un objet qui va avoir pour r√¥le
            // d'envoyer nos requetes SQL √  la BDD

            statement = conn.createStatement();
            String sql = "SELECT password FROM utilisateurs WHERE username ='" + login + "'";
            resultat = statement.executeQuery(sql);

            // la m√©thode next() renvoie un bool√©en si c'est vrai √ßa veut dire qu'on a au moins un r√©sultat de notre requete
            // et donc que celle ci fonctionne

            if (resultat.next() == true)

            {
                // on r√©cup√®re le mot de passe de la base de donn√©es
                String motDePasse = resultat.getString(1);

                // on le compare √  notre mot de passe tapp√© et selon le cas:

                if (motDePasse.equals(password))
                {
                    // on affiche un popup pour dire que c'est bon
                    JOptionPane.showMessageDialog(null, "Connexion rÈussie ! ", "Success", JOptionPane.PLAIN_MESSAGE);
                    resultatRequete = true;

                }
                else
                {
                    // on affiche un popup pour dire que c'est pas bon (ici c'est le mot de passe)
                    JOptionPane.showMessageDialog(null, "Mot de passe incorrect ! ", "Error", 1);
                }
            }
            else
            {
                // on affiche un popup pour dire que c'est pas bon (ici c'est le login)
                JOptionPane.showMessageDialog(null, "Login incorrect ! ", "Error", 1);
            }

            // on co√ªpe la connexion √  la BDD
            conn.close();
        }
        catch (SQLException e4)
        {
            System.out.println(e4.getMessage());
        }
        return resultatRequete;
    }

    public JTable listerArticles()
    {

        Connection conn = ConnexionBase();
        Statement statement = null;
        ResultSet resultat1, resultat2;
        int nombreLignes = 0;
        
        JTable tableau;

        try
        {
            // createStatement() est issue de l'import de java.sql il sert √  cr√©er un objet qui va avoir pour r√¥le
            // d'envoyer nos requetes SQL √  la BDD
        	
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            
            String sql1 = "SELECT COUNT(*) FROM produits";
            String sql2 = "SELECT * FROM produits";
            resultat1 = statement.executeQuery(sql1);
            while (resultat1.next()) {
            	nombreLignes = resultat1.getInt(1);
            }
                        
            
            resultat2 = statement.executeQuery(sql2);

            
            /*
             * on obtient un resultat sous forme de ResultSet. C'est un objet qui contient les r√©sultats d'une req√™te SQL nous
             * voulons r√©cup√©rer ces donn√©es et les mettre dans un tableau on doit donc conna√Ætre le nombre de colonnes, de lignes
             * pour cela on a des m√©tadonn√©es qu'on peut r√©cup√©rer dans le ResultSet qui nous donne toutes ces infos (les
             * m√©tadonn√©es servent √  d√©crire une structure)
             */

         // On cr√©e la m√©tadata √  partir du resultset.
           ResultSetMetaData metadataArticles = resultat2.getMetaData();
           int nombreColonnes = metadataArticles.getColumnCount();
           
         
           
           
        // On cr√©e le menu:
           String[] entetes = {"Id", "Id_Categorie", "Nom", "Descriptif", "Prix", "Poids","Date", "Image","Stock"};

        /* On cr√©e un tableau √  deux dimensions qui va contenir autant de lignes que de r√©sultats + 1 
         * (Pour l'ajout) puis autant de lignes que de cases que de colonnes -1.
         */
           
           
           
           Object[][] donnees = new Object[nombreLignes][nombreColonnes];
           
           int i = 0;
           while (resultat2.next()) {
               donnees[i][0] = resultat2.getInt("id");
               donnees[i][1] = resultat2.getInt("id_Categorie");
               donnees[i][2] = resultat2.getString("nom_produit");
               donnees[i][3] = resultat2.getString("descriptif");
               donnees[i][4] = resultat2.getDouble("prix_produit");
               donnees[i][5] = resultat2.getDouble("poids_produit");
               donnees[i][6] = resultat2.getString("image_produit");
               donnees[i][7] = resultat2.getTime("date_produit");
               donnees[i][8] = resultat2.getDouble("stock_produit");
               
               i++;
           }
        // On cr√©e le mod√®le.
         /*
           ArticlesTableModele articlesTableModele = new ArticlesTableModele(donnees, entetes);
           tableArticles = new JTable(articlesTableModele);
          */ 
           // donnees.length nous donne le nombre de lignes +1 c'est pour √ßa qu'on mets -1 dans notre boucle
         /*  
           for (int h=0; h<donnees.length-1;h++) {
               for (int j=0;j<9;j++) {
                   System.out.println(donnees[h][j]);
           }
           }
           System.out.println(donnees.length);
         */
           tableau = new JTable(donnees, entetes);
           return tableau;
        }
        catch (SQLException e4)
        {
            System.out.println(e4.getMessage());
        }
        return null;
      
        
    }
    
    public JTable listerClients()
    {

        Connection conn = ConnexionBase();
        Statement statement = null;
        ResultSet resultat1, resultat2;
        int nombreLignes = 0;
        
        JTable tableau;

        try
        {
            // createStatement() est issue de l'import de java.sql il sert √  cr√©er un objet qui va avoir pour r√¥le
            // d'envoyer nos requetes SQL √  la BDD
        	
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            
            String sql1 = "SELECT COUNT(*) FROM client";
            String sql2 = "SELECT * FROM client";
            resultat1 = statement.executeQuery(sql1);
            while (resultat1.next()) {
            	nombreLignes = resultat1.getInt(1);
            }
                        
            
            resultat2 = statement.executeQuery(sql2);

            
            /*
             * on obtient un resultat sous forme de ResultSet. C'est un objet qui contient les r√©sultats d'une req√™te SQL nous
             * voulons r√©cup√©rer ces donn√©es et les mettre dans un tableau on doit donc conna√Ætre le nombre de colonnes, de lignes
             * pour cela on a des m√©tadonn√©es qu'on peut r√©cup√©rer dans le ResultSet qui nous donne toutes ces infos (les
             * m√©tadonn√©es servent √  d√©crire une structure)
             */

         // On cr√©e la m√©tadata √  partir du resultset.
           ResultSetMetaData metadataArticles = resultat2.getMetaData();
           int nombreColonnes = metadataArticles.getColumnCount();
           
         
           
           
        // On cr√©e le menu:
           String[] entetes = {"Id", "email_client", "mdp_client", "Nom", "PrÈnom", "Ville","Code Postal", "Date d'inscription","TÈlÈphone","adresse","complÈment adresse"};

        /* On cr√©e un tableau √  deux dimensions qui va contenir autant de lignes que de r√©sultats + 1 
         * (Pour l'ajout) puis autant de lignes que de cases que de colonnes -1.
         */
           
           
           
           Object[][] donnees = new Object[nombreLignes][nombreColonnes];
           
           int i = 0;
           while (resultat2.next()) {
               donnees[i][0] = resultat2.getInt("id");
               donnees[i][1] = resultat2.getString("email_client");
               donnees[i][2] = resultat2.getString("mdp_client");
               donnees[i][3] = resultat2.getString("nom_client");
               donnees[i][4] = resultat2.getString("prenom_client");
               donnees[i][5] = resultat2.getString("ville_client");
               donnees[i][6] = resultat2.getString("codePostal_client");
               donnees[i][7] = resultat2.getTime("dateInscription_client");
               donnees[i][8] = resultat2.getString("tel_client");
               donnees[i][9] = resultat2.getString("adresse_client");
               donnees[i][10] = resultat2.getString("adresse2_client");
               
               i++;
           }
        
           tableau = new JTable(donnees, entetes);
           return tableau;
        }
        catch (SQLException e4)
        {
            System.out.println(e4.getMessage());
        }
        return null;
      
        
    }
    
    public JTable listerCommandes()
    {

        Connection conn = ConnexionBase();
        Statement statement = null;
        ResultSet resultat1, resultat2;
        int nombreLignes = 0;
        
        JTable tableau;

        try
        {
            // createStatement() est issue de l'import de java.sql il sert a crÈer un objet qui va avoir pour r√¥le
            // d'envoyer nos requetes SQL a la BDD
        	
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            
            String sql1 = "SELECT COUNT(*) FROM commandes";
            String sql2 = "SELECT * FROM commandes";
            resultat1 = statement.executeQuery(sql1);
            while (resultat1.next()) {
            	nombreLignes = resultat1.getInt(1);
            }
                        
            
            resultat2 = statement.executeQuery(sql2);

            
            /*
             * on obtient un resultat sous forme de ResultSet. C'est un objet qui contient les rÈsultats d'une requete SQL nous
             * voulons rÈcupÈrer ces donnÈes et les mettre dans un tableau on doit donc connaitre le nombre de colonnes, de lignes
             * pour cela on a des mÈtadonnÈes qu'on peut r√©cup√©rer dans le ResultSet qui nous donne toutes ces infos (les
             * mÈtadonnÈes servent a  dÈcrire une structure)
             */

         // On crÈe la mÈtadata a  partir du resultset.
           ResultSetMetaData metadataCommandes = resultat2.getMetaData();
           int nombreColonnes = metadataCommandes.getColumnCount();
           
         
           
           
        // On crÈe le menu:
           String[] entetes = {"Id", "montant_commande", "Nom", "Adresse", "adresse 2", "Ville_commande","Code Postal", "tel", "Livraison_commande", "Email"};

        /* On crÈe un tableau a  deux dimensions qui va contenir autant de lignes que de rÈsultats + 1 
         * (Pour l'ajout) puis autant de lignes que de cases que de colonnes -1.
         */
           
           
           
           Object[][] donnees = new Object[nombreLignes][nombreColonnes];
           
           int i = 0;
           while (resultat2.next()) {
               donnees[i][0] = resultat2.getInt("id");
               donnees[i][1] = resultat2.getInt("montant_commande");
               donnees[i][2] = resultat2.getString("nomClient_commande");
               donnees[i][3] = resultat2.getString("adresse_Commande");
               donnees[i][4] = resultat2.getString("adresse2_commande");
               donnees[i][5] = resultat2.getString("ville_commande");
               donnees[i][6] = resultat2.getInt("codePostal_commande");
               donnees[i][7] = resultat2.getInt("tel_commande");
               //donnees[i][8] = resultat2.getDate("livraison_commande");
	           donnees[i][9] = resultat2.getString("email_commande");
               
               i++;
           }
        // On crÈe le modËle.
         /*
           ArticlesTableModele articlesTableModele = new ArticlesTableModele(donnees, entetes);
           tableCommandes = new JTable(articlesTableModele);
          */ 
           // donnees.length nous donne le nombre de lignes +1 c'est pour √ßa qu'on mets -1 dans notre boucle
         /*  
           for (int h=0; h<donnees.length-1;h++) {
               for (int j=0;j<9;j++) {
                   System.out.println(donnees[h][j]);
           }
           }
           System.out.println(donnees.length);
         */
           tableau = new JTable(donnees, entetes);
           return tableau;
        }
        catch (SQLException e4)
        {
            System.out.println(e4.getMessage());
        }
        return null;
      
        
    }
    
   
    
}
