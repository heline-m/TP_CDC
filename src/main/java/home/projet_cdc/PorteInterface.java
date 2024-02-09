package home.projet_cdc;

import java.util.List;

public interface PorteInterface {

    void ouvrir();

    void bloquer();

    void debloquer();

    boolean isEstBloque();
}
