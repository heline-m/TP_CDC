package home.projet_cdc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
class ProjetCdcApplicationTests {

    @Test
    void casNominal() {
        // ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badge = new Badge(1);

        // ET une porte ayant des accès
        List<Badge> badgesAutorise = new ArrayList<>();
        badgesAutorise.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, badgesAutorise);

        // QUAND un badge autorisé sur la porte est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);


        // ALORS la porte est déverrouillée
        assertTrue(porte.verifierOuvertureDemandee());
    }

    @Test
    void casSansInterrogation() {
        // ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);  //double de test

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(new Badge(1));

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casSansDetection(){
        //ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badge = new Badge(1);

        // ET une porte ayant des accès
        List<Badge> badgesAutorise = new ArrayList<>();
        badgesAutorise.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, badgesAutorise);

        //QUAND on interroge ce lecteur sans qu'il ait détecté un badge
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieurPortesSansAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte1 = new PorteBuilder().parDefaut();
        var porte2 = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte1, porte2);
        var badge = new Badge(1);

        // ET une porte n'ayant pas d'accès
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte1, new ArrayList<>());
        accesPorteFake.addAcces(porte2, new ArrayList<>());

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);


        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte1.verifierOuvertureDemandee());
        assertFalse(porte2.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieurPortesAvecAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte1 = new PorteBuilder().parDefaut();
        var porte2 = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte1, porte2);
        var badge = new Badge(1);

        // ET une porte ayant des accès
        List<Badge> badges = new ArrayList<>();
        badges.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte1, badges);
        accesPorteFake.addAcces(porte2, badges);

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);


        // ALORS la porte est déverrouillée
        assertTrue(porte1.verifierOuvertureDemandee());
        assertTrue(porte2.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieurPortesAvecUnSeulAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte1 = new PorteBuilder().parDefaut();
        var porte2 = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte1, porte2);
        var badge = new Badge(1);

        // ET une porte ayant des accès
        List<Badge> badges = new ArrayList<>();
        badges.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte1, badges);

        // ET une porte n'ayant pas d'accès
        accesPorteFake.addAcces(porte2, new ArrayList<>());

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);


        // ALORS la porte est déverrouillée
        assertTrue(porte1.verifierOuvertureDemandee());
        assertFalse(porte2.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieursLecteursAvecAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake1 = new LecteurFake(porte);  //double de test
        var lecteurFake2 = new LecteurFake(porte);  //double de test
        var badge = new Badge(1);

        // ET une porte ayant des acces
        List<Badge> badges = new ArrayList<>();
        badges.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, badges);

        // QUAND un badge est passe devant un des lecteur
        lecteurFake1.simulerDetectionBadge(badge);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake1, lecteurFake2);


        // ALORS la porte est déverrouillée
        assertTrue(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieursLecteursSansAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake1 = new LecteurFake(porte);  //double de test
        var lecteurFake2 = new LecteurFake(porte);  //double de test
        var badge = new Badge(1);

        // ET une porte n'ayant pas d'acces
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, new ArrayList<>());

        // QUAND un badge est passe devant un des lecteur
        lecteurFake1.simulerDetectionBadge(badge);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake1, lecteurFake2);


        // ALORS la porte est déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieursLecteursPlusieursPortesAvecAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte1 = new PorteBuilder().parDefaut();
        var porte2 = new PorteBuilder().parDefaut();
        var lecteurFake1 = new LecteurFake(porte1);  //double de test
        var lecteurFake2 = new LecteurFake(porte2);  //double de test
        var badge = new Badge(1);

        // ET une porte ayant des accès
        List<Badge> badges = new ArrayList<>();
        badges.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte1, badges);
        accesPorteFake.addAcces(porte2, badges);

        // QUAND un badge est passe devant un des lecteur
        lecteurFake1.simulerDetectionBadge(badge);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake1, lecteurFake2);


        // ALORS la porte est déverrouillée
        assertTrue(porte1.verifierOuvertureDemandee());
        assertFalse(porte2.verifierOuvertureDemandee());
    }

    @Test
    void casPlusieursLecteursPlusieursPortesSansAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte1 = new PorteBuilder().parDefaut();
        var porte2 = new PorteBuilder().parDefaut();
        var lecteurFake1 = new LecteurFake(porte1);  //double de test
        var lecteurFake2 = new LecteurFake(porte2);  //double de test
        var badge = new Badge(1);

        // ET une porte n'ayant pas d'accès
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte1, new ArrayList<>());
        accesPorteFake.addAcces(porte2, new ArrayList<>());

        // QUAND un badge est passe devant un des lecteur
        lecteurFake1.simulerDetectionBadge(badge);

        // ET que ces lecteurs sont interrogés
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake1, lecteurFake2);


        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte1.verifierOuvertureDemandee());
        assertFalse(porte2.verifierOuvertureDemandee());
    }

    @Test
    void casPorteAvecAccesNonDeverrouilleeQuandBadgeBloqueDetecte() {
        // ÉTANT DONNÉ un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badgeBloque = new Badge(1);

        // ET porte ayant des acces
        List<Badge> badges = new ArrayList<>();
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, badges);

        // QUAND un badge bloqué est détecté
        badgeBloque.bloque();
        lecteurFake.simulerDetectionBadge(badgeBloque);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteSansAccesNonDeverrouilleeQuandBadgeBloqueDetecte() {
        // ÉTANT DONNÉ un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badgeBloque = new Badge(1);

        // ET porte n'ayant pas d'acces
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, new ArrayList<>());

        // QUAND un badge bloqué est détecté
        badgeBloque.bloque();
        lecteurFake.simulerDetectionBadge(badgeBloque);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteAvecAccesDeverrouilleeQuandBadgeBloquePuisDebloqueDetecte() {
        // ÉTANT DONNÉ un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badgeBloque = new Badge(1);
        badgeBloque.bloque();
        badgeBloque.debloque();

        // ET un porte ayant des acces
        List<Badge> badges = new ArrayList<>();
        badges.add(badgeBloque);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, badges);

        // QUAND un badge bloqué puis debloqué est détecté

        lecteurFake.simulerDetectionBadge(badgeBloque);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte est déverrouillée
        assertTrue(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteSansAccesDeverrouilleeQuandBadgeBloquePuisDebloqueDetecte() {
        // ÉTANT DONNÉ un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badgeBloque = new Badge(1);
        badgeBloque.bloque();
        badgeBloque.debloque();

        // ET un porte ayant des acces
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, new ArrayList<>());

        // QUAND un badge bloqué puis debloqué est détecté

        lecteurFake.simulerDetectionBadge(badgeBloque);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte est déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteAvecAccesNonDeverouilleeQuandPorteBloqueeEtBadgeDetecte(){
        // ÉTANT DONNÉ un lecteur relié à une porte bloquee et non bloquee
        var porteBloquee = new PorteBuilder().Bloquee().Build();
        var porteNonBloquee = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porteBloquee, porteNonBloquee);
        var badge = new Badge(1);

        // ET porte ayant des acces
        List<Badge> badges = new ArrayList<>();
        badges.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porteBloquee, badges);
        accesPorteFake.addAcces(porteNonBloquee, badges);

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte bloquée n'est pas déverrouillée
        assertFalse(porteBloquee.verifierOuvertureDemandee());
        // ET la porte non bloquée est déverrouillée
        assertTrue(porteNonBloquee.verifierOuvertureDemandee());
    }

    @Test
    void casPorteSansAccesNonDeverouilleeQuandPorteBloqueeEtBadgeDetecte(){
        // ÉTANT DONNÉ un lecteur relié à une porte bloquee et non bloquee
        var porteBloquee = new PorteBuilder().Bloquee().Build();
        var porteNonBloquee = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porteBloquee, porteNonBloquee);
        var badge = new Badge(1);

        // ET porte n'ayant pas d'acces
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porteBloquee, new ArrayList<>());
        accesPorteFake.addAcces(porteNonBloquee, new ArrayList<>());

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte bloquée n'est pas déverrouillée
        assertFalse(porteBloquee.verifierOuvertureDemandee());
        // ET la porte non bloquée n'est pas déverrouillée
        assertFalse(porteNonBloquee.verifierOuvertureDemandee());
    }

    @Test
    void casPorteAvecAccesNonDeverouilleeQuandPorteBloqueePuisDebloqueeEtBadgeDetecte(){
        // ÉTANT DONNÉ un lecteur relié à une porte bloquee puis debloquee
        var porteBloqueePuisDebloque = new PorteBuilder().Bloquee().Build();
        porteBloqueePuisDebloque.debloquer();
        var lecteurFake = new LecteurFake(porteBloqueePuisDebloque);
        var badge = new Badge(1);

        // ET une porte ayant des acces
        List<Badge> badges = new ArrayList<>();
        badges.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porteBloqueePuisDebloque, badges);

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte bloquée puis débloquée est déverrouillée
        assertTrue(porteBloqueePuisDebloque.verifierOuvertureDemandee());
    }

    @Test
    void casPorteSansAccesNonDeverouilleeQuandPorteBloqueePuisDebloqueeEtBadgeDetecte(){
        // ÉTANT DONNÉ un lecteur relié à une porte bloquee puis debloquee
        var porteBloqueePuisDebloque = new PorteBuilder().Bloquee().Build();
        porteBloqueePuisDebloque.debloquer();
        var lecteurFake = new LecteurFake(porteBloqueePuisDebloque);
        var badge = new Badge(1);

        // ET une porte n'ayant pas d'acces
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porteBloqueePuisDebloque, new ArrayList<>());

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte bloquée puis débloquée n'est pas déverrouillée
        assertFalse(porteBloqueePuisDebloque.verifierOuvertureDemandee());
    }

    @Test
    void casPorteAvecAccesDeverouilleEtantDonneBadgeEtantDansLaListeDautorisationDeLaPorte(){
        // ÉTANT DONNÉ une porte liée à un lecteur
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badge = new Badge(1);

        // ET une porte ayant des acces
        List<Badge> badges = new ArrayList<>();
        badges.add(badge);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, badges);

        // QUAND le badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);


        // ALORS la porte est déverrouillée
        assertTrue(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteSansAccesDeverouilleEtantDonneBadgeEtantDansLaListeDautorisationDeLaPorte(){
        // ÉTANT DONNÉ une porte liée à un lecteur
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badge = new Badge(1);

        // ET une porte ayant des acces
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, new ArrayList<>());

        // QUAND le badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);


        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteSansAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badge = new Badge(1);

        // ET une porte n'ayant pas d'accès
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, new ArrayList<>());

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteAvecAccesDifferent() {
        // ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badge1 = new Badge(1);

        // ET une porte ayant des accès
        List<Badge> badgesAutorise = new ArrayList<>();
        var badge2 = new Badge(2);
        var badge3 = new Badge(3);
        var badge4 = new Badge(4);
        badgesAutorise.add(badge2);
        badgesAutorise.add(badge3);
        badgesAutorise.add(badge4);
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, badgesAutorise);

        // QUAND un badge non autorisé est détecté
        lecteurFake.simulerDetectionBadge(badge1);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteSansAccesPuisAvecAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badge = new Badge(1);

        // ET une porte n'ayant pas d'accès
        var accesPorteFake = new AccesPorteFake();
        accesPorteFake.addAcces(porte, new ArrayList<>());

        // Puis ayant des acces
        List<Badge> badgesAutorise = new ArrayList<>();
        badgesAutorise.add(badge);
        accesPorteFake.addAcces(porte, badgesAutorise);

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte est déverrouillée
        assertTrue(porte.verifierOuvertureDemandee());
    }

    @Test
    void casPorteAvecAccesPuisSansAcces() {
        // ETANT DONNE un lecteur relié à une porte
        var porte = new PorteBuilder().parDefaut();
        var lecteurFake = new LecteurFake(porte);
        var badge = new Badge(1);

        // ET une porte ayant des accès
        var accesPorteFake = new AccesPorteFake();
        List<Badge> badgesAutorise = new ArrayList<>();
        badgesAutorise.add(badge);
        accesPorteFake.addAcces(porte, badgesAutorise);

        // Puis n'ayant plus d'acces
        accesPorteFake.addAcces(porte, new ArrayList<>());

        // QUAND un badge est détecté
        lecteurFake.simulerDetectionBadge(badge);

        // ET que ce lecteur est interrogé
        MoteurOuverture.InterrogerLecteurs(accesPorteFake, lecteurFake);

        // ALORS la porte n'est pas déverrouillée
        assertFalse(porte.verifierOuvertureDemandee());
    }
}
