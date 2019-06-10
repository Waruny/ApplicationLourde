import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
 
public class GestionQuitter extends AbstractAction {
    public GestionQuitter(String texte){
        super(texte);
    }
     
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}