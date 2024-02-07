package home.projet_cdc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
class ProjetCdcApplicationTests {

    @Test
    void casNominal() {
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy(); //double de test
        var lecteurFake = new LecteurFake(porteSpy);


        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge();

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);


        // ALORS la porte est déverrouillée
        assertTrue(porteSpy.verifierOuvertureDemandee());
    }

    @Test
    void casSansInterrogation() {
        // ETANT DONNE un lecteur relié à une porte
        var lecteurFake = new LecteurFake();  //double de test
        var porteSpy = new PorteSpy();

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge();

        // ALORS la porte est déverrouillée
        assertFalse(porteSpy.verifierOuvertureDemandee());
    }

    @Test
    void casSansDetection(){
        //ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy();
        var lecteurFake = new LecteurFake(porteSpy);

        //QUAND on interroge ce lecteur sans qu'il ait détecté un badge
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        assertFalse(porteSpy.verifierOuvertureDemandee());

    }

    @Test
    void casPlusieurPortes() {
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy1 = new PorteSpy();
        var porteSpy2 = new PorteSpy();
        var lecteurFake = new LecteurFake(porteSpy1, porteSpy2);  //double de test

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge();

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);


        // ALORS la porte est déverrouillée
        assertTrue(porteSpy1.verifierOuvertureDemandee());
        assertTrue(porteSpy2.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieursLecteurs() {
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy = new PorteSpy();
        var lecteurFake1 = new LecteurFake(porteSpy);  //double de test
        var lecteurFake2 = new LecteurFake(porteSpy);  //double de test

        // QUAND un badge est passe devant un des lecteur
        lecteurFake1.simulerDetectionBadge();

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);


        // ALORS la porte est déverrouillée
        assertTrue(porteSpy.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieursLecteursPlusieursPortes() {
        // ETANT DONNE un lecteur relié à une porte
        var porteSpy1 = new PorteSpy();
        var porteSpy2 = new PorteSpy();
        var lecteurFake1 = new LecteurFake(porteSpy1);  //double de test
        var lecteurFake2 = new LecteurFake(porteSpy2);  //double de test


        // QUAND un badge est passe devant un des lecteur
        lecteurFake1.simulerDetectionBadge();

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);


        // ALORS la porte est déverrouillée
        assertTrue(porteSpy1.verifierOuvertureDemandee());
        assertFalse(porteSpy2.verifierOuvertureDemandee());
    }



    @Test
    void testPorteNonDeverrouilleeQuandBadgeInvalideDetecte() {
        // ETANT DONNE un lecteur relié à une porte
        Lecteur lecteur = new Lecteur();
        Porte porte = new Porte();
        lecteur.setPorte(porte);

        // QUAND un badge invalide ou inconnu est détecté
        Badge badgeInvalide = new Badge("badgeInvalide");
        boolean result = lecteur.detecterBadge(badgeInvalide);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.estDeverrouillee(), "La porte ne devrait pas être déverrouillée pour un badge invalide");
    }



    @Test
    void testPorteNonDeverrouilleeQuandBadgeValideBloqueDetecte() {
        // ÉTANT DONNÉ un lecteur relié à une porte
        Lecteur lecteur = new Lecteur();
        Porte porte = new Porte();
        lecteur.setPorte(porte);

        // LORSQUE un badge valide mais bloqué est détecté
        Badge badgeBloque = new Badge("badgeValideBloque");
        badgeBloque.bloquer();
        boolean result = lecteur.detecterBadge(badgeBloque);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.estDeverrouillee(), "La porte ne devrait pas être déverrouillée pour un badge valide mais bloqué");
    }

    @Test
    void testPorteNonDeverrouilleeQuandRecuperationImpossible() {
        // ÉTANT DONNÉ un lecteur relié à une porte
        Lecteur lecteur = new Lecteur();
        Porte porte = new Porte();
        lecteur.setPorte(porte);

        // ET qu'il est impossible de récupérer les informations
        lecteur.setRecuperationImpossible(true);

        // QUAND un badge est détecté
        Badge badge = new Badge("badgeTest");
        boolean result = lecteur.detecterBadge(badge);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.estDeverrouillee(), "La porte ne devrait pas être déverrouillée lorsque la récupération d'informations est impossible");
    }



}
