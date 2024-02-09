package home.projet_cdc;

import java.util.List;
import java.util.Map;

public class MoteurOuverture {

    public static void InterrogerLecteurs(AccesPorteInterface acces, LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs){
            if(lecteur.aDetecteBadge()){
                for(var porte : lecteur.getPortes())
                    if(!porte.isEstBloque()){
                        for(var badge : acces.getAcces(porte)) {
                            porte.ouvrir();
                        }
                    }
            }
        }
    }
}
