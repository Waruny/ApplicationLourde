import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.* ;
import java.io.* ;

 
public class Base {
     
    public Connection conn;
     
    public void connexionBDD(){
         
         
        /*  Chargement de la Base de données  */
            try{
                 
                Class.forName("com.mysql.jdbc.Driver");
                 
            } catch (ClassNotFoundException e ) {
                 
                System.out.println(" Erreur de chargement de la Base de données");
                e.getMessage();
                System.exit(0);
                 
            }
         
         
            /*    Connexion de la Base de données  */
            try {
                 
                String url = "jdbc:mysql://localhost:3306/trubl_bdd";
                String user = "root";
                String passwd ="root";
                conn = DriverManager.getConnection(url, user, passwd);
                 
            } catch (Exception e) {
                System.out.println(" Erreur de Connexion à la Base de données ");
            }  
             
        }
     
            public Connection getConnect(){
                 
                return conn;
            }
     
             
            /*   Deconnexion  */
            public void Deconnexion(){
             
                try {
                    conn.close();
                     
                } catch (Exception e) {
                    System.out.println(" Déconnexion Impossible ");
                }
            }
         
             
             
             
}