import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
 
public class GestionDeconnecter extends AbstractAction {
    private Application application;
    public GestionDeconnecter(Application appli, String texte){
        super(texte);
        this.application = appli;
    }
     
    public void actionPerformed(ActionEvent e) {
        

        application.getTableau().removeAll();
        application.getTableHeader().removeAll();
        application.getScrollTableau().removeAll();
    	
    	application.refresh();
        
        application.getLogo().setVisible(true);
        application.getIdentifiant().setVisible(true);
        application.getUser().setVisible(true);
        application.getMdp().setVisible(true);
        application.getMotdepasse().setVisible(true);
        application.getBoutonConnexion().setVisible(true);
        application.getBarreMenu().setVisible(false);
        application.getTableau().setVisible(false);
        application.getTableHeader().setVisible(false);
        application.getScrollTableau().setVisible(false);
        
        
        application.getMotdepasse().setText("");
        application.getUser().setText("");
        
    }
}
