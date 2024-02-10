package home.projet_cdc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccesPorteFake implements AccesPorteInterface {

    private Map<PorteInterface, List<Badge>> acces = new HashMap<>();

    @Override
    public List<Badge> getAcces(PorteInterface porte) {
        return this.acces.get(porte);
    }

    @Override
    public void addAcces(PorteInterface porte, List<Badge> badges) {
        //this.acces.put(porte, badges);
    }
}
