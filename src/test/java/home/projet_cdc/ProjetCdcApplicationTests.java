package home.projet_cdc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
class ProjetCdcApplicationTests {

    @Test
    void testPorteDeverrouilleeQuandBadgeDetecte() {
        // ETANT DONNE un lecteur relié à une porte
        Lecteur lecteur = new Lecteur();
        Porte porte = new Porte();
        lecteur.setPorte(porte);

        // QUAND un badge est détecté
        Badge badge = new Badge();
        boolean result = lecteur.detecterBadge(badge);

        // ALORS la porte est déverrouillée
        assertTrue(porte.estDeverrouillee(), "La porte devrait être déverrouillée");
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
