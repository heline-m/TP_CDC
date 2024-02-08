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
        lecteurFake.simulerDetectionBadge(new Badge());

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
        lecteurFake.simulerDetectionBadge(new Badge());

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
        var lecteurFake = new LecteurFake(porteSpy1, porteSpy2);

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(new Badge());

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
        lecteurFake1.simulerDetectionBadge(new Badge());

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
        lecteurFake1.simulerDetectionBadge(new Badge());

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(lecteurFake1, lecteurFake2);


        // ALORS la porte est déverrouillée
        assertTrue(porteSpy1.verifierOuvertureDemandee());
        assertFalse(porteSpy2.verifierOuvertureDemandee());
    }

    @Test
    void casPorteNonDeverrouilleeQuandBadgeBloqueDetecte() {
        // ÉTANT DONNÉ un lecteur relié à une porte
        var porteSpy = new PorteSpy();
        var lecteurFake = new LecteurFake(porteSpy);
        var badgeBloque = new Badge();

        // QUAND un badge bloqué est détecté
        badgeBloque.bloque();
        lecteurFake.simulerDetectionBadge(badgeBloque);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porteSpy.verifierOuvertureDemandee());
    }

    @Test
    void casPorteDeverrouilleeQuandBadgeBloquePuisDebloqueDetecte() {
        // ÉTANT DONNÉ un lecteur relié à une porte
        var porteSpy = new PorteSpy();
        var lecteurFake = new LecteurFake(porteSpy);
        var badgeBloque = new Badge();

        // QUAND un badge bloqué puis debloqué est détecté
        badgeBloque.bloque();
        badgeBloque.debloque();
        lecteurFake.simulerDetectionBadge(badgeBloque);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte est déverrouillée
        assertTrue(porteSpy.verifierOuvertureDemandee());
    }

    @Test
    void casPorteNonDeverouilleeQuandPorteBloqueeEtBadgeDetecte(){
        // ÉTANT DONNÉ un lecteur relié à une porte bloquee et non bloquee
        var porteBloquee = new PorteBuilder().Bloquee().Build();
        var porteNonBloquee = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porteBloquee, porteNonBloquee);
        var badge = new Badge();

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte bloquée n'est pas déverrouillée
        assertFalse(porteBloquee.verifierOuvertureDemandee());
        // ET la porte non bloquée est déverrouillée
        assertTrue(porteNonBloquee.verifierOuvertureDemandee());
    }

    @Test
    void casPorteNonDeverouilleeQuandPorteBloqueePuisDebloqueeEtBadgeDetecte(){
        // ÉTANT DONNÉ un lecteur relié à une porte bloquee puis debloquee
        var porteBloqueePuisDebloque = new PorteBuilder().Bloquee().Build();
        porteBloqueePuisDebloque.debloquer();
        var lecteurFake = new LecteurFake(porteBloqueePuisDebloque);
        var badge = new Badge();

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(lecteurFake);

        // ALORS la porte bloquée puis débloquée est déverrouillée
        assertTrue(porteBloqueePuisDebloque.verifierOuvertureDemandee());
    }
}
