package home.projet_cdc;

import java.util.List;
import java.util.Map;

public interface AccesPorteInterface {

    List<Badge> getAcces(PorteInterface porte);

    void addAcces(PorteInterface porte, List<Badge> badges);

}
