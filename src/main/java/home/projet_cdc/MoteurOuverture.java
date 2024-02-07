package home.projet_cdc;

public class MoteurOuverture {

    public static void InterrogerLecteurs(LecteurInterface... lecteurs) {
        for (var lecteur : lecteurs){
            if(lecteur.aDetecteBadge()){
                for(var porte : lecteur.getPortes())
                porte.ouvrir();
            }
        }
    }
}
