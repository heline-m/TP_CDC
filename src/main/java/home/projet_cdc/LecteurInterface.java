package home.projet_cdc;

public interface LecteurInterface {
    boolean aDetecteBadge();

    Integer aDetecteBadgeId();

    PorteInterface[] getPortes();
}
