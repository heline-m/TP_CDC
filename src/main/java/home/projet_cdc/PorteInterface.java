package home.projet_cdc;

public interface PorteInterface {
    void ouvrir();

    void bloquer();

    void debloquer();

    boolean isEstBloque();
}
